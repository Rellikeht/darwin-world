package world.model;

import world.SimulationSettings;
import world.util.MapVisualizer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

public abstract class AbstractWorldMap {

    // Elementy
    protected final Set<Vector2d> grass;
    protected final ConcurrentMap<Vector2d, List<Animal>> animals;
    protected final Vector2d upperRight, lowerLeft;
    protected final int jungleStart;
    protected final int jungleEnd;

    // Numeracja
    protected static int curId = 0;
    protected final int id;
    protected int day = 0;

    // Staty
    protected final int size;
    protected int deadAnimalsAmount = 0;
    protected int deadAnimalsLifespanSum = 0;
    protected int animalsAmount = 0;
    protected final Map<Genome, Integer> mostPopularGenomes = new HashMap<>();
    protected int childrenAmountSum = 0;
    // Prawdopodobnie za trudne
    //protected int animalEnergySum = 0;

    // Reszta
    protected static final Random random = new Random();
    protected final RandomPositionGenerator overEquator, underEquator, equator;
    protected final SimulationSettings settings;
    protected final Set<MapChangeListener> listeners;
    protected final MapVisualizer visualizer = new MapVisualizer(this);

    public AbstractWorldMap(SimulationSettings settings) {
        this.settings = settings;

        grass = new HashSet<>(settings.get("InitialGrassAmount"));
        animals = new ConcurrentHashMap<>(settings.get("InitialAnimalAmount"));
        listeners = new LinkedHashSet<>();
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(settings.get("MapWidth") - 1, settings.get("MapHeight") - 1);

        jungleStart = (upperRight.getY() - lowerLeft.getY() - settings.get("JungleSize")) / 2;
        jungleEnd = jungleStart + settings.get("JungleSize");
        underEquator = new RandomPositionGenerator(
                lowerLeft,
                new Vector2d(upperRight.getX(), jungleStart)
        );
        equator = new RandomPositionGenerator(
                new Vector2d(lowerLeft.getX(), jungleStart),
                new Vector2d(upperRight.getX(), jungleEnd + 1)
        );
        overEquator = new RandomPositionGenerator(
                new Vector2d(lowerLeft.getX(), jungleEnd + 1),
                upperRight
        );

        this.id = curId;
        curId += 1;
        size = settings.get("MapWidth") * settings.get("MapHeight");
        grassPlace(settings.get("InitialGrassAmount"));
    }

    public void nextDay() {
        allAnimals().toList().forEach(animal -> {
            if (animal.getEnergy() <= 0) {
                animals.get(animal.getPosition()).remove(animal);
                animal.die(day);

                deadAnimalsAmount += 1;
                deadAnimalsLifespanSum += animal.getDayOfDeath() - animal.getDayOfBirth();
                animalsAmount -= 1;

                Genome genome = animal.getGenes();
                int count = mostPopularGenomes.get(genome);
                mostPopularGenomes.put(genome, count - 1);
                //animalEnergySum -= animal.getEnergy();
            }
        });
        day += 1;
    }

    protected void grassPlace(int N) {
        // It's definitely a kind of magic
        List<RandomPositionGenerator> generators = new ArrayList<>(10);

        // Po to bozia dała oczy...
        if (underEquator.hasNext(N/2)) { generators.add(underEquator); }
        if (overEquator.hasNext(N/2)) { generators.add(overEquator); }
        if (equator.hasNext(N/2)) {
            for (int i = 0; i < 8; i++) { generators.add(equator); }
        }

        if (generators.isEmpty()) { return; }
        for (int i = N; i > 0; i--) {
            int grassProbability = random.nextInt(generators.size());
            RandomPositionGenerator generator = generators.get(grassProbability);

            // ... żeby robić na oko
            if (generator.hasNext(N/3)) {
                Vector2d grassPosition = generator.next();
                grass.add(grassPosition);
            } else { generators.removeIf(g -> g == generator); }
        }

        mapChanged("We have some new grass");
    }
    public void grassPlace() { grassPlace(settings.get("DailyGrassAmount")); }

    public void place(Animal animal) {
        List<Animal> animalsAt = animals.getOrDefault(animal.getPosition(), new ArrayList<>());
        animalsAt.add(animal);
        animals.put(animal.getPosition(), animalsAt);
        animalsAmount += 1;
        Genome genome = animal.getGenes();
        Integer count = mostPopularGenomes.getOrDefault(genome, 0);
        mostPopularGenomes.put(genome, count + 1);
    }

    public Stream<Animal> allAnimals() {
        // Przyjemne, trzeba przyznać
        return animals.values().stream().flatMap(List::stream);
    }

    public String toString() { return visualizer.draw(lowerLeft, upperRight); }
    public void addListener(MapChangeListener listener) {
        listeners.add(listener);
    }
    public void removeListener(MapChangeListener listener) {
        listeners.remove(listener);
    }
    protected void mapChanged(String message) {
        for (MapChangeListener listener : listeners) {
            listener.mapChanged(this, message);
        }
    }

    public boolean isGrassAt(Vector2d position) { return grass.contains(position); }
    public Animal getAnimalAt(Vector2d position) {
        List<Animal> animalList = animals.get(position);
        if (animalList != null && !animalList.isEmpty()) { return animalList.get(0); }
        return null;
    }

    public String getStringAt(Vector2d position) {
        List<Animal> animalsAt = animals.get(position);
        if (animalsAt != null) {
            if (animalsAt.size() == 1) {
                return animalsAt.get(0).toString();
            } else if (animalsAt.size() > 1 && animalsAt.size() < 10) {
                return Integer.toString(animalsAt.size());
            } else if (animalsAt.size() >= 10) {
                return "#";
            }
        }

        if (grass.contains(position)) {
            return "*";
        }
        return "";
    }

    public void moveAnimals() {
        allAnimals().toList().forEach(animal -> {
            animal.activateGene();
            Direction direction = animal.getDirection();
            Vector2d dirVector = direction.toUnitVector();
            Vector2d beforePosition = animal.getPosition();
            List<Animal> animalsAtBefore = animals.getOrDefault(beforePosition, new ArrayList<>());
            animals.remove(beforePosition);
            animalsAtBefore.remove(animal);
            specialAnimalMovement(animal, dirVector);
            Vector2d afterPosition = animal.getPosition();
            List<Animal> animalsAtAfter = animals.getOrDefault(afterPosition, new ArrayList<>());
            animalsAtAfter.add(animal);
            animals.put(beforePosition, animalsAtBefore);
            animals.put(afterPosition, animalsAtAfter);
        });

        mapChanged("Animals moved");
    }

    protected void specialAnimalMovement(Animal animal, Vector2d vector) { }

    protected static Queue<Animal> getFittest(List<Animal> animals) {
        PriorityQueue<Animal> queue = new PriorityQueue<>(animals.size());
        queue.addAll(animals);
        return queue;
    }

    protected static Animal getSingleFittest(List<Animal> animals) {
        Animal fittest = animals.get(0);
        for (ListIterator<Animal> it = animals.listIterator(1); it.hasNext(); ) {
            Animal animal = it.next();
            if (animal.compareTo(fittest) < 0) fittest = animal;
        }
        return fittest;
    }

    public void doEating() {
        animals.forEach((position, list) -> {
            if (!list.isEmpty() && grass.contains(position)) {
                getSingleFittest(list).eatGrass(settings.get("GrassEnergy"));
                grass.remove(position);

                // Bo mi się nie chce ifów robić, a to i tak zadziała
                underEquator.free(position);
                equator.free(position);
                overEquator.free(position);
            }
        });
        mapChanged("Eating done");
    }

    public void doReproduction() {
        animals.forEach((ignored, list) -> {
            Animal a1, a2;
            if (list.size() < 2) return;
            else if (list.size() == 2) {
                a1 = list.get(0);
                a2 = list.get(1);
                if (a1.getEnergy() > a2.getEnergy()) {
                    Animal tmp = a1;
                    a1 = a2;
                    a2 = tmp;
                }
            } else {
                Queue<Animal> fittest = getFittest(list);
                a1 = fittest.poll();
                a2 = fittest.poll();
            }
            if (a1.getEnergy() >= settings.get("EnergyNeededForProcreation") &&
                    a2.getEnergy() >= settings.get("EnergyNeededForProcreation")) {
                place(reproducing(a1, a2));
            }
        });

        mapChanged("Reproduction done");
    }

    protected Animal reproducing(Animal animal1, Animal animal2) {
        childrenAmountSum += 2;
        Vector2d position = animal1.getPosition();
        Genome genome = new Genome(
                animal1.getGenes(), animal2.getGenes(),
                animal1.getEnergy(), animal2.getEnergy(),
                settings.get("MinAmountOfMutations"),
                settings.get("MaxAmountOfMutations"),
                settings.get("isMutationRandom")
        );
        animal1.loseEnergy(settings.get("EnergyTakenByProcreation"));
        animal2.loseEnergy(settings.get("EnergyTakenByProcreation"));
        return new Animal(
                position, 2 * settings.get("EnergyTakenByProcreation"),
                genome, day, animal1, animal2
        );
    }

    public int getId() { return this.id; }
    public int getDay() { return day; }
    public Vector2d getLowerLeft() {return lowerLeft;}
    public Vector2d getUpperRight() {return upperRight;}

    int getGrassAmount() { return grass.size(); }
    int getFreeSquares() {
        Set<Vector2d> occupied = new HashSet<>(grass);
        animals.forEach((pos, list) -> {
            if (!list.isEmpty()) occupied.add(pos);
        });
        return size - (occupied.size());
    }

    int getDeadAnimalsAmount() { return deadAnimalsAmount; }
    int getDeadAnimalsLifespanSum() { return deadAnimalsLifespanSum; }
    int getAnimalsAmount() { return animalsAmount; }
    int getChildrenAmountSum() { return childrenAmountSum; }
    Map<Genome, Integer> getMostPopularGenomes() { return mostPopularGenomes; }

}
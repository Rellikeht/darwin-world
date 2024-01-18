package world.model;

import world.SimulationSettings;
import world.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap {

    // Elementy
    protected final Set<Vector2d> grass;
    protected final Map<Vector2d, List<Animal>> animals;
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
    // TODO
    //protected int animalEnergySum = 0;
    //protected int childrenAmountSum = 0;

    // Reszta
    protected final Random random = new Random();
    protected final RandomPositionGenerator overEquator, underEquator, equator;
    protected final SimulationSettings settings;
    // TODO i tak będzie tylko 1 listener, więc trzeba to jakoś zmienić
    protected final Set<MapChangeListener> listeners;
    protected final MapVisualizer visualizer = new MapVisualizer(this);

    public AbstractWorldMap(SimulationSettings settings) {
        this.settings = settings;

        grass = new HashSet<>(settings.getInitialGrassAmount());
        animals = new HashMap<>(settings.getInitialAnimalAmount());
        listeners = new LinkedHashSet<>();
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(settings.getMapWidth()-1, settings.getMapHeight()-1);

        jungleStart = (upperRight.getY() - lowerLeft.getY() - settings.getJungleSize()) / 2;
        jungleEnd = jungleStart + settings.getJungleSize();
        underEquator = new RandomPositionGenerator(
                lowerLeft,
                new Vector2d(upperRight.getX(), jungleStart)
        );
        equator = new RandomPositionGenerator(
                new Vector2d(lowerLeft.getX(), jungleStart),
                new Vector2d(upperRight.getX(), jungleEnd+1)
        );
        overEquator = new RandomPositionGenerator(
                new Vector2d(lowerLeft.getX(), jungleEnd+1),
                upperRight
        );

        this.id = curId;
        curId += 1;
        size = settings.getMapWidth()*settings.getMapHeight();
        grassPlace(settings.getInitialGrassAmount());
    }

    public void nextDay() {
        for(Animal animal: allAnimals()) {
            if(animal.getEnergy()<=0){
                animals.get(animal.getPosition()).remove(animal);
                animal.die(day);

                deadAnimalsAmount += 1;
                deadAnimalsLifespanSum += animal.getDayOfDeath() - animal.getDayOfBirth();
                animalsAmount -= 1;

                Genome genome = animal.getGenes();
                int count = mostPopularGenomes.get(genome);
                mostPopularGenomes.put(genome, count-1);
                //animalEnergySum -= animal.getEnergy();
            }
        }
        day += 1;
    }

    protected void grassPlace(int N) {
        // It's definitely a kind of magic
        List<RandomPositionGenerator> generators = new ArrayList<>(10);
        if (underEquator.hasNext()) { generators.add(underEquator); }
        if (overEquator.hasNext()) { generators.add(overEquator); }
        if (equator.hasNext()) {
            for (int i = 0; i < 8; i++) { generators.add(equator); }
        }

        if (generators.isEmpty()) { return; }
        for(int i=0;i<N;i++) {
            int grassProbability = random.nextInt(generators.size());
            RandomPositionGenerator generator = generators.get(grassProbability);

            // TODO Tu trzeba przysiąść i to lepiej zrobić, ale to jak będzie za dużo czasu
            if (generator.hasNext()) {
                Vector2d grassPosition = generator.next();
                grass.add(grassPosition);
            }
        }

        mapChanged("We have some new grass");
    }

    public void grassPlace() {
        grassPlace(settings.getDailyGrassAmount());
    }

    public void place(Animal animal)  {
        List<Animal> animalsAt = animals.getOrDefault(animal.getPosition(), new ArrayList<>());
        animalsAt.add(animal);
        animals.put(animal.getPosition(), animalsAt);
        animalsAmount += 1;

        Genome genome = animal.getGenes();
        Integer count = mostPopularGenomes.getOrDefault(genome, 0);
        mostPopularGenomes.put(genome, count+1);
        //animalEnergySum += animal.getEnergy();
    }

    protected Collection<Animal> allAnimals() {
        // Przyjemne, trzeba przyznać
        return animals.values().stream()
                .flatMap(List::stream)
                .toList();
    }

    public String toString() { return visualizer.draw(lowerLeft, upperRight); }

    public void addListener(MapChangeListener listener) { listeners.add(listener); }
    public void removeListener(MapChangeListener listener) { listeners.remove(listener); }
    protected void mapChanged(String message) {
        for (MapChangeListener listener : listeners) {
            listener.mapChanged(this, message);
        }
    }

    public String getAt(Vector2d position) {
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

        if (grass.contains(position)) { return "*"; }
        return "";
    }

    //protected boolean canMove(Animal animal, Vector2d dirVector){
    //    return animal.getPosition().add(dirVector).follows(lowerLeft)
    //        && animal.getPosition().add(dirVector).precedes(upperRight);
    //}

    public void moveAnimals() {
        for(Animal animal: allAnimals()) {
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
        }

        mapChanged("Animals moved");
    }

    protected void specialAnimalMovement(Animal animal, Vector2d vector) {
        // TODO powyciągać dalej, implementacje nie są za ładne
    }

    protected Queue<Animal> getFittestAt(Vector2d position) {
        List<Animal> animals = this.animals.get(position);
        PriorityQueue<Animal> queue = new PriorityQueue<>(animals.size());
        queue.addAll(animals);
        return queue;
    }

    public void doEating() {
        for (Vector2d position: animals.keySet()) {
            if (!animals.get(position).isEmpty()) {
                Queue<Animal> fittest = getFittestAt(position);
                Animal animal = fittest.peek();
                animal.eatGrass(settings.getGrassEnergy());
                grass.remove(position);

                // Bo mi się nie chce ifów robić, a to i tak zadziała xd
                underEquator.free(position);
                equator.free(position);
                overEquator.free(position);
            }
        }

        mapChanged("Eating done");
    }

    public void doReproduction() {
        for (Vector2d position: animals.keySet()) {
            if (animals.get(position).size() > 1) {
                Queue<Animal> fittest = getFittestAt(position);
                Animal a1 = fittest.peek();
                Animal a2 = fittest.peek();
                if (a1.getEnergy() >= settings.getEnergyNeededForProcreation() &&
                    a2.getEnergy() >= settings.getEnergyNeededForProcreation()) {
                    place(reproducing(a1, a2));
                }
            }
        }

        mapChanged("Reproduction done");
    }

    protected Animal reproducing(Animal animal1, Animal animal2) {
        animal1.loseEnergy(settings.getEnergyTakenByProcreation());
        animal2.loseEnergy(settings.getEnergyTakenByProcreation());

        Vector2d position = animal1.getPosition();
        Genome genome = new Genome(
                animal1.getGenes(), animal2.getGenes(),
                animal1.getEnergy(), animal2.getEnergy(),
                settings.getMinAmountOfMutations(),
                settings.getMaxAmountOfMutations(),
                settings.isMutationRandom()
        );

        return new Animal(
                position, Direction.randomDirection(),
                2*settings.getEnergyTakenByProcreation(),
                genome, day, animal1, animal2
        );
    }

    public int getGrassAmount() { return grass.size(); }
    public int getFreeSquares() {
        // TODO usunąć w razie czego, nie wiem, jak interpretować wolne pola
        return size - getGrassAmount();
    }

    public int getId() { return this.id; }
    public Vector2d getLowerLeft() { return lowerLeft; }
    public Vector2d getUpperRight() { return upperRight; }

    public int getAvgLifespan() {
        return deadAnimalsLifespanSum/deadAnimalsAmount;
    }
    public int getAnimalsAmount() { return animalsAmount; }
    public Map<Genome, Integer> getMostPopularGenomes() {
        return mostPopularGenomes;
    }

    public int getAvgChildrenAmount() {
        int childrenAmount = 0;
        for (Animal animal: allAnimals()) {
            childrenAmount += animal.getChildrenAmount();
        }
        return childrenAmount/animalsAmount;
    }
    public int getAvgAnimalEnergy() {
        int animalEnergySum = 0;
        for (Animal animal: allAnimals()) {
            animalEnergySum += animal.getEnergy();
        }
        return animalEnergySum/animalsAmount;
    }

}

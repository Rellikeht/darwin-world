package world.model;

import world.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    // Elementy
    protected final Set<Vector2d> grass;
    protected final Map<Vector2d, List<Animal>> animals;
    protected final Vector2d upperRight, lowerLeft;
    protected final Set<MapChangeListener> listeners;
    protected final MapVisualizer visualizer = new MapVisualizer(this);
    //protected final int jungleSize;
    protected final int jungleStart;
    protected final int jungleEnd;

    // Numeracja
    protected static int curId = 0;
    protected final int id;
    protected int day = 0;

    // Generacja
    protected final Random random = new Random();
    protected final RandomPositionGenerator overEquator, underEquator, equator;
    //protected final Iterator<Vector2d>[] generators;

    @Override
    public int getId() { return this.id; }
    @Override
    public Vector2d getUpperRight() { return upperRight; }

    public AbstractWorldMap(int width, int height, int initialGrassAmount, int jungleSize) {
        grass = new HashSet<>(initialGrassAmount);
        animals = new HashMap<>(initialGrassAmount);
        listeners = new LinkedHashSet<>();
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width-1, height-1);

        // TODO te obliczenia nie są bardzo dokładne,
        // i tamten test nie przechodzi
        //this.jungleSize = jungleSize;
        jungleStart = (upperRight.getY() - lowerLeft.getY() - jungleSize) / 2;
        jungleEnd = jungleStart + jungleSize;
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

        // It's a kind of magic
        //generators = new RandomPositionGenerator[]{
        //        overEquator, underEquator, equator, equator, equator,
        //        equator, equator, equator, equator, equator
        //};

        this.id = curId;
        curId += 1;

        // Początkowa Trawa
        grassPlace(initialGrassAmount);
    }

    @Override
    public void grassPlace(int N) {
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

            // W tym miejscu i tak się to wywali
            // TODO Tu trzeba przysiąść i to lepiej zrobić
            if (generator.hasNext()) {
                Vector2d grassPosition = generator.next();
                grass.add(grassPosition);
            }
        }
    }

    @Override
    public void place(Animal animal)  {
        List<Animal> animalsAt = animals.getOrDefault(animal.getPosition(), new ArrayList<>());
        animalsAt.add(animal);
        animals.put(animal.getPosition(), animalsAt);
    }

    //@Override
    protected Collection<Animal> allAnimals() {
        // Przyjemne, trzeba przyznać
        return animals.values().stream()
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public String toString() {
        return visualizer.draw(lowerLeft, upperRight);
    }
    public Set<Vector2d> grassPositions() { return grass; }

    @Override
    public void addListener(MapChangeListener listener) { listeners.add(listener); }
    @Override
    public void removeListener(MapChangeListener listener) { listeners.remove(listener); }
    protected void mapChanged(String message) {
        for (MapChangeListener listener : listeners) {
            listener.mapChanged(this, message);
        }
    }

    @Override
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

    //protected Set<Vector2d> grassPositions() { return grass; }

    private boolean canMove(Animal animal, Vector2d dirVector){
        return animal.getPosition().add(dirVector).follows(lowerLeft)
            && animal.getPosition().add(dirVector).precedes(upperRight);
    }
    @Override
    public void moveAnimals(int energyTaken) {
        // TODO Coś z listenerem, trzeba przerzucić do simulation jak dla mnie
        // ale to później
        mapChanged("");

        for(Animal animal: allAnimals()) {
            animal.rotateAnimals(animal.getCurrentGene()%(animal.getGenes().getLength()+1));
            Direction direction = animal.getOrientation();
            Vector2d dirVector = direction.toUnitVector();
            Vector2d beforePosition = animal.getPosition();

            List<Animal> animalsAtBefore = animals.getOrDefault(beforePosition, new ArrayList<>());
            animals.remove(beforePosition);
            animalsAtBefore.remove(animal);
            if (canMove(animal, dirVector)){
                animal.move();
                animal.loseEnergy(energyTaken);
            }

            Vector2d afterPosition=animal.getPosition();
            List<Animal> animalsAtAfter = animals.getOrDefault(afterPosition, new ArrayList<>());
            animalsAtAfter.add(animal);
            animals.put(beforePosition, animalsAtBefore);
            animals.put(afterPosition, animalsAtAfter);
        }
    }

    protected Queue<Animal> getFittestAt(Vector2d position) {
        List<Animal> animals = this.animals.get(position);
        PriorityQueue<Animal> queue = new PriorityQueue<>(
                animals.size()
                //new FittestComparator()
        );
        queue.addAll(animals);
        return queue;
    }

    public Vector2d getUpperBound(){
        return upperRight;
    }
    public Vector2d getLowerBound(){
        return lowerLeft;
    }

    @Override
    public void doEating(int grassEnergy) {
        for (Vector2d position: animals.keySet()) {
            if (!animals.get(position).isEmpty()) {
                Queue<Animal> fittest = getFittestAt(position);
                Animal animal = fittest.peek();
                animal.eatGrass(grassEnergy);
                grass.remove(position);

                // Bo mi się nie chce ifów robić, a to i tak zadziała xd
                underEquator.free(position);
                equator.free(position);
                overEquator.free(position);
            }
        }

    }

    protected Animal reproducing(Animal animal1, Animal animal2, int energyTaken) {
        int side = random.nextInt(2);
        int energy1 = animal1.getEnergy();
        int energy2 = animal2.getEnergy();
        Genome genome1 = animal1.getGenes();
        Genome genome2 = animal2.getGenes();
        Genome newGenome = new Genome(0);
        int GENOME_LENGTH = genome1.getLength();
        int proportions = GENOME_LENGTH*(energy2/energy1);

        if(side==0) {
            for (int i = 0; i < GENOME_LENGTH-proportions; i++) {
                newGenome.setGene(i, genome2.getGene(i));
            }
            for (int i = GENOME_LENGTH-proportions; i < GENOME_LENGTH; i++) {
                newGenome.setGene(i, genome1.getGene(i));
            }
        }

        else{
            for (int i = 0; i < proportions; i++) {
                newGenome.setGene(i, genome1.getGene(i));
            }
            for (int i = proportions; i < GENOME_LENGTH; i++) {
                newGenome.setGene(i, genome2.getGene(i));
            }
        }

        animal1.loseEnergy(energyTaken);
        animal2.loseEnergy(energyTaken);
        Vector2d position = animal1.getPosition();
        return new Animal(position, Direction.randomDirection(), 2*energyTaken, newGenome, day);
    }

    @Override
    public void doReproduction(int energyNeeded, int energyTaken) {
        for (Vector2d position: animals.keySet()) {
            if (animals.get(position).size() > 1) {
                Queue<Animal> fittest = getFittestAt(position);
                Animal a1 = fittest.peek();
                Animal a2 = fittest.peek();
                if (a1.getEnergy() >= energyNeeded && a2.getEnergy() >= energyNeeded) {
                    place(reproducing(a1, a2, energyTaken));
                }
            }
        }
    }

    @Override
    public void nextDay() {
        for(Animal animal: allAnimals()) {
            if(animal.getEnergy()<=0){
                animals.get(animal.getPosition()).remove(animal);
                animal.die(day);
            }
            //else{
            //    animal.nextDayOfLife(); // ??
            //}
        }
        day += 1;
    }

}

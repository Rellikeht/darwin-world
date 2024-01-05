package world.model;

import world.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    // Elementy
    //protected final Map<Vector2d, Grass> grass;
    protected final Set<Vector2d> grass;
    protected final Map<Vector2d, List<Animal>> animals;
    protected final Vector2d upperRight, lowerLeft;
    protected final Set<MapChangeListener> listeners;
    protected final MapVisualizer visualizer = new MapVisualizer(this);
    protected final int jungleSize;

    // Numeracja
    protected static int curId = 0;
    protected final int id;
    protected int day = 0;

    protected final RandomPositionGenerator overEquator, underEquator, equator;
    protected final Iterator<Vector2d>[] generators;

    @Override
    public int getId() { return this.id; }
    @Override
    public Vector2d getUpperRight() { return upperRight; }

    public AbstractWorldMap(int width, int height, int initialGrassAmount, int jungleSize) {
        //grass = new HashMap<>(initialGrassAmount);
        grass = new HashSet<>(initialGrassAmount);
        animals = new HashMap<>(initialGrassAmount);
        listeners = new LinkedHashSet<>();
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width-1, height-1);
        int jungleStart = (upperRight.getY() - lowerLeft.getY() - jungleSize) / 2;
        int jungleEnd = jungleStart + jungleSize;

        this.jungleSize = jungleSize;
        this.id = curId;
        curId += 1;

        // Początkowa Trawa
        Random random = new Random();
        overEquator = new RandomPositionGenerator(
                new Vector2d(lowerLeft.getX(), jungleEnd),
                upperRight
        );
        underEquator = new RandomPositionGenerator(
                lowerLeft,
                new Vector2d(upperRight.getX(), jungleStart)
        );
        equator = new RandomPositionGenerator(
                new Vector2d(lowerLeft.getX(), jungleStart),
                new Vector2d(upperRight.getX(), jungleEnd)
        );

        // It's a kind of magic
        generators = new RandomPositionGenerator[]{
                overEquator, underEquator, equator, equator, equator,
                equator, equator, equator, equator, equator
        };
        for(int i=0;i<initialGrassAmount;i++) {
            int grassProbability = random.nextInt(generators.length);
            Vector2d grassPosition = generators[grassProbability].next();

            //grass.put(grassPosition, new Grass(grassPosition));
            grass.add(grassPosition);
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

        //Grass grassAt = grass.get(position);
        //if (grassAt != null) { return grassAt.toString(); }
        if (grass.contains(position)) { return "*"; }
        return "";
    }

    //public List<Animal> animalsAt(Vector2d position) {
    //    return animals.get(position);
    //}

    //public Set<Vector2d> grassPositions() { return grass.keySet(); }
    protected Set<Vector2d> grassPositions() { return grass; }

    @Override
    public void moveAnimals() {
        // TODO nie wiem, co z tym listenerem
        mapChanged("");
        //mapChanged("Animal at %s is moving".formatted(animal.getPosition().toString()));

        for(Animal animal: allAnimals()) {

            // TODO ruch tutaj
            //Vector2d beforePosition = animal.getPosition();
            //List<Animal> animalsAtBefore=animals.getOrDefault(beforePosition, new ArrayList<>());
            //animals.remove(beforePosition);
            //animalsAtBefore.remove(animal);
            ////animal.move(direction, this);
            //Vector2d afterPosition=animal.getPosition();

            //List<Animal> animalsAtAfter=animals.getOrDefault(afterPosition, new ArrayList<>());
            //animalsAtAfter.add(animal);
            //animals.put(beforePosition, animalsAtBefore);
            //animals.put(afterPosition, animalsAtAfter);

        }
    }

    protected Queue<Animal> getFittestAt(Vector2d position) {
        List<Animal> animals = this.animals.get(position);
        PriorityQueue<Animal> queue = new PriorityQueue<>(
                animals.size(),
                Comparator.comparingInt(Animal::getEnergy)
        );
        queue.addAll(animals);
        return queue;
    }

    @Override
    public void doEating(int grassEnergy) {

        //Map<Vector2d, Animal> eatingAnimals= new HashMap<>(animals.size());
        //for(Animal animal:animals) {
        //    Vector2d position = animal.getPosition();
        //    if (grassPositions.contains(position)) {
        //        if (eatingAnimals.containsKey(position)) {
        //            Animal oldAnimal = eatingAnimals.get(position);
        //            if (animal.getEnergy() > oldAnimal.getEnergy()) {
        //                eatingAnimals.replace(position,animal);
        //            }
        //            if (animal.getEnergy() == oldAnimal.getEnergy()) {
        //                if(animal.getDayOfBirth()>oldAnimal.getDayOfBirth()){
        //                    eatingAnimals.replace(position,animal);
        //                }
        //                if(animal.getDayOfBirth()==oldAnimal.getDayOfBirth()){
        //                    if(animal.getChildrenAmount()>oldAnimal.getChildrenAmount()){
        //                        eatingAnimals.replace(position,animal);
        //                    }
        //                    if(animal.getChildrenAmount()==oldAnimal.getChildrenAmount()){
        //                        Random random = new Random();
        //                        int chosenAnimal = random.nextInt(2);
        //                        if(chosenAnimal==0){
        //                            eatingAnimals.replace(position,animal);
        //                        }
        //                    }
        //                }

        //            }
        //        }
        //        else {
        //            eatingAnimals.put(animal.getPosition(), animal);
        //        }
        //    }
        //}

        //for(Animal eater: eatingAnimals.values()){
        //    grassPositions.remove(eater.getPosition());
        //    // ?????
        //    //Animal animal = animals.get(animals.indexOf(eater));
        //    //animal.addEnergy(GRASS_ENERGY);
        //}

        for (Vector2d position: animals.keySet()) {
            if (!animals.get(position).isEmpty()) {
                Queue<Animal> fittest = getFittestAt(position);
                Animal animal = fittest.peek();
                animal.addEnergy(grassEnergy);
            }
        }

    }

    protected Animal reproducing(Animal animal1, Animal animal2) {
        Random random = new Random();
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

        Vector2d position = animal1.getPosition();
        return new Animal(position, Direction.randomDirection(), 10, newGenome, day);
    }

    public void doReproduction() {
        for (Vector2d position: animals.keySet()) {
            if (animals.get(position).size() > 1) {
                Queue<Animal> fittest = getFittestAt(position);
                // Walony warning, nie ma jak mu powiedzieć, że to jest dobrze
                // A jest, bo ten if tutaj
                place(reproducing(fittest.peek(), fittest.peek()));
            }
        }
    }

    @Override
    public void nextDay() {
        for(Animal animal: allAnimals()) {
            if(animal.getEnergy()<=0){
                animals.get(animal.getPosition()).remove(animal);
                animal.setDayOfDeath(day);
            }
        }
        day += 1;
    }

}

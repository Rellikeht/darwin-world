package world;

import javafx.scene.paint.RadialGradient;
import world.model.*;

import java.util.*;

public class Simulation implements Runnable {

    // Stałe
    private static final int MAP_HEIGHT = 30;
    private static final int MAP_WIDTH = 100;
    private static final int INITIAL_GRASS_AMOUNT = 300;
    private static final int GRASS_ENERGY = 20;
    private static final int DAILY_GRASS_AMOUNT = 50;
    private static final int INITIAL_ANIMAL_AMOUNT = 20;
    private static final int INITIAL_ANIMAL_ENERGY = 120;
    private static final int ENERGY_NEEDED_FOR_PROCREATION = 50;
    private static final int ENERGY_TAKEN_BY_PROCREATION = 30;
    private static final int MIN_AMOUNT_OF_MUTATIONS = 0;
    private static final int MAX_AMOUNT_OF_MUTATIONS = 100;
    private static final int GENOME_LENGTH = 10;
    private static final int INITIAL_JUNGLE_SIZE = 5;

    // Dane i elementy
    private final boolean behaviorVariant = false;
    private final boolean mapVariant = false;
    private final boolean growthVariant = false;
    private final List<Animal> animals;
    private final WorldMap map;
    //private final SimulationStats stats;

    // To raczej jest zbędne
    private int frameNum = 0;

    public Simulation(int initialAnimalAmount) {
        animals = new ArrayList<Animal>(initialAnimalAmount);
        map = new EarthMap(MAP_WIDTH, MAP_HEIGHT, INITIAL_GRASS_AMOUNT, INITIAL_JUNGLE_SIZE);
        //stats = new SimulationStats();

        // To już może potem jak się wyklaruje co i jak
//        if (mapVariant) {
//            map = new EarthMap(MAP_WIDTH, MAP_HEIGHT, INITIAL_GRASS_AMOUNT, INITIAL_JUNGLE_SIZE);
//        } else {
//            map = new HellMap(MAP_WIDTH, MAP_HEIGHT, INITIAL_GRASS_AMOUNT, INITIAL_JUNGLE_SIZE);
//        }

        Vector2d maxPos = map.getUpperRight();
        for (int i = 0; i < initialAnimalAmount; i++) {
            Random random = new Random();
            Genome genome = new Genome(GENOME_LENGTH);

            Animal animal = new Animal(
                    new Vector2d(
                            random.nextInt(maxPos.getX()),
                            random.nextInt(maxPos.getY())
                    ),
                    Direction.values()[random.nextInt(Direction.values().length)],
                    INITIAL_ANIMAL_ENERGY,
                    genome
            );

            map.place(animal);
            animals.add(animal);
        }

        // TODO staty
    }

    public Simulation() {
        this(INITIAL_ANIMAL_AMOUNT);
    }

//    private void prepare() {
//        this.frameNum = 0;
//    }
    // animal1 musi być silnieszy niż animal2
    private void reproducing(Animal animal1, Animal animal2){
        Random random = new Random();
        int side = random.nextInt(2);
        int energy1 = animal1.getEnergy();
        int energy2 = animal2.getEnergy();
        Genome genome1=animal1.getGenes();
        Genome genome2=animal2.getGenes();
        Genome newGenome = new Genome(0);
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
        map.place(new Animal(position,Direction.D0,10,newGenome,frameNum));
    }
    private void frame() {
        // To jest właśnie ten dylemat, czy robić to wszystko tu, czy nie
        // Imo nie ma co brać tej listy co frame bo ona sie będzie zmieniać tutaj i będziemy odpalać kolejny ruch na tej już zmienionej a tak to
        // pobierając co frame będziemy działać cały czas na tej samej a to bez sensu chyba
        List<Animal> animals = map.getAnimals();
        Set<Vector2d> grassPositions = map.grassPositions() ;
        // TODO RUCH
        for (Animal a : animals) {
//            map.move(a, Direction.D0);
            map.move(a);
        }

        // TODO JEDZENIE
        // Powinno działać ale nie dokończone. Jest szansa ze sie wyjebie na lokalnej zmianie wartościa tutaj a nie w całym programie
        // Ale to długie
        Map<Vector2d, Animal> eatingAnimals= new HashMap<>(animals.size());
        for(Animal animal:animals) {
            Vector2d position = animal.getPosition();
            if (grassPositions.contains(position)) {
                if (eatingAnimals.containsKey(position)) {
                    Animal oldAnimal = eatingAnimals.get(position);
                    if (animal.getEnergy() > oldAnimal.getEnergy()) {
                        eatingAnimals.replace(position,animal);
                    }
                    if (animal.getEnergy() == oldAnimal.getEnergy()) {
                        if(animal.getDayOfBirth()>oldAnimal.getDayOfBirth()){
                            eatingAnimals.replace(position,animal);
                        }
                        if(animal.getDayOfBirth()==oldAnimal.getDayOfBirth()){
                            if(animal.getChildrenAmount()>oldAnimal.getChildrenAmount()){
                                eatingAnimals.replace(position,animal);
                            }
                            if(animal.getChildrenAmount()==oldAnimal.getChildrenAmount()){
                                Random random = new Random();
                                int chosenAnimal = random.nextInt(2);
                                if(chosenAnimal==0){
                                    eatingAnimals.replace(position,animal);
                                }
                            }
                        }


                    }
                }
                else {
                        eatingAnimals.put(animal.getPosition(), animal);
                }
            }
        }
        for(Animal eater: eatingAnimals.values()){
            grassPositions.remove(eater.getPosition());
            Animal animal = animals.get(animals.indexOf(eater));
            animal.addEnergy(GRASS_ENERGY);
        }
        //Ale to jest chore gówno
        // TODO ROZMNAŻANIE
        Map<Vector2d, PriorityQueue<Animal>> reproducingAnimals= new HashMap<>(animals.size());
        for (Animal animal:animals){
            Vector2d position = animal.getPosition();
            if(reproducingAnimals.containsKey(position)){
                PriorityQueue<Animal> oldQueue = reproducingAnimals.get(position);
                oldQueue.add(animal);
                reproducingAnimals.replace(position,oldQueue);
            }
            else{
                PriorityQueue<Animal> newQueue = new PriorityQueue<>(animals.size(),Comparator.comparingInt(Animal::getEnergy));
                newQueue.add(animal);
                reproducingAnimals.put(animal.getPosition(),newQueue);
            }
        }
        for(PriorityQueue<Animal> queue:reproducingAnimals.values()) {
            if (queue.size() >= 2) {
                Animal animal1 = queue.peek();
                reproducing(animal1, queue.peek());
            }
        }

        // TODO UMIERANIE
        for(Animal animal:animals){
            if(animal.getEnergy()<=0){
                animals.remove(animal);
                animal.setDayOfDeath(frameNum);
            }
        }

        frameNum++;
    }

    public void run() {
        while (true) {
            frame();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addListener(MapChangeListener listener) {
        map.addListener(listener);
    }

    public void removeListener(MapChangeListener listener) {
        map.removeListener(listener);
    }

}
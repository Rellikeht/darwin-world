package world;

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
                        // TUTAJ TRZEBA ZROBIĆ TE KOLEJNE WARUNKI W KONFLIKCIE
                    }
                }
                else {
                        eatingAnimals.put(animal.getPosition(), animal);
                }
            }
        }
        for(Vector2d eaters: eatingAnimals.keySet()){
            grassPositions.remove(eaters);
            Animal animal = animals.get(animals.indexOf(eaters));
            animal.addEnergy(GRASS_ENERGY);
        }

        // TODO ROZMNAŻANIE

        // TODO UMIERANIE
        for(Animal animal:animals){
            if(animal.getEnergy()<=0){
                animals.remove(animal);
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
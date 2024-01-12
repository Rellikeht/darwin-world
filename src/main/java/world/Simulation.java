package world;

//import javafx.scene.paint.RadialGradient;

import world.model.*;

import java.util.Random;

public class Simulation implements Runnable {

    // Stałe
    private static final int MAP_HEIGHT = 20;
    private static final int MAP_WIDTH = 20;
    private static final int INITIAL_GRASS_AMOUNT = 3;
    private static final int GRASS_ENERGY = 20;
    private static final int DAILY_GRASS_AMOUNT = 20;
    private static final int INITIAL_ANIMAL_AMOUNT = 15;
    private static final int INITIAL_ANIMAL_ENERGY = 120;
    private static final int ENERGY_NEEDED_FOR_PROCREATION = 50;
    private static final int ENERGY_TAKEN_BY_PROCREATION = 30;
    private static final int ENERGY_TAKEN_BY_MOVEMENT = 5;
    private static final int MIN_AMOUNT_OF_MUTATIONS = 0;
    private static final int MAX_AMOUNT_OF_MUTATIONS = 100;
    private static final int GENOME_LENGTH = 10;
    private static final int INITIAL_JUNGLE_SIZE = 5;
    private static final int NEW_DAY_GRASS = 10;
    private static final int TYPE_OF_MUTATION = 0;


    // Dane i elementy
    private final boolean behaviorVariant = false;
    private final boolean mapVariant = false;
    private final boolean growthVariant = false;
    private final WorldMap map;
    //private final SimulationStats stats;

    public Simulation(int initialAnimalAmount) {
        map = new EarthMap(MAP_WIDTH, MAP_HEIGHT, INITIAL_GRASS_AMOUNT, INITIAL_JUNGLE_SIZE,TYPE_OF_MUTATION,MIN_AMOUNT_OF_MUTATIONS,MAX_AMOUNT_OF_MUTATIONS);
        //stats = new SimulationStats();

        // To już może potem jak się wyklaruje co i jak
//        if (mapVariant) {
//            map = new EarthMap(MAP_WIDTH, MAP_HEIGHT, INITIAL_GRASS_AMOUNT, INITIAL_JUNGLE_SIZE);
//        } else {
//            map = new HellMap(MAP_WIDTH, MAP_HEIGHT, INITIAL_GRASS_AMOUNT, INITIAL_JUNGLE_SIZE,ENERGY_NEEDED_FOR_PROCREATION);
//        }

        Vector2d maxPos = map.getUpperRight();
        for (int i = 0; i < initialAnimalAmount; i++) {
            Random random = new Random();
            Genome genome = new Genome(GENOME_LENGTH);

            Animal animal = new Animal(
                    new Vector2d(
                            random.nextInt(maxPos.getX()+1),
                            random.nextInt(maxPos.getY()+1)
                    ),
                    Direction.randomDirection(),
                    INITIAL_ANIMAL_ENERGY,
                    genome
            );

            map.place(animal);
        }

        // TODO staty
    }

    public Simulation() {
        this(INITIAL_ANIMAL_AMOUNT);
    }
    private void wait(int miliseconds) {
        try { Thread.sleep(500); }
        // Nie wiem, czy by tego jakoś lepiej nie trzeba
        catch (InterruptedException e) { throw new RuntimeException(e); }
    }

    private void frame() {

        // Zamysł jest taki: co można zrobić względnie sensownie w mapie niech będzie w mapie
        // Nie będzie to może najczystsze rozwiązanie, ale jakkolwiek tego nie zrobimy,
        // coś będzie źle, albo któraś klasa będzie niepotrzebna, albo se naduplikujemy
        // parametrów, albo będzie masa getterów, więc spróbuję wykombinować coś pośredniego
        // ruch będzie w mapie, symulacja nie ma tu wkładu
        // jedzenie chyba tu, bo energia, chociaż w mapie by uszło
        // rozmnażanie raczej tu, ale w sumie w mapie też by dało radę
        // usuwanie umarłych w mapie, bo to łatwe (ten dzień taki sobie, ale trudno)

        map.nextDay();
        map.moveAnimals(ENERGY_TAKEN_BY_MOVEMENT);
        wait(300);
        map.doEating(GRASS_ENERGY);
        wait(300);
        map.doReproduction(ENERGY_NEEDED_FOR_PROCREATION, ENERGY_TAKEN_BY_PROCREATION);
        wait(300);
        map.grassPlace(NEW_DAY_GRASS);
        wait(300);
    }

    public void run() {
        int i=0;
        while (i < 5) {
            frame();
            i++;
        }
    }

    public void addListener(MapChangeListener listener) {
        map.addListener(listener);
    }

    public void removeListener(MapChangeListener listener) {
        map.removeListener(listener);
    }
    public WorldMap getMap() { return map; }

}

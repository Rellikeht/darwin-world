package world;

import world.model.*;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private static final int MAP_HEIGHT = 30;
    private static final int MAP_WIDTH = 100;
    private final boolean mapVariant = false;
    private static final int INITIAL_GRASS_AMOUNT = 300;
    private static final int GRASS_ENERGY = 20;
    private static final int DAILY_GRASS_AMOUNT = 50;
    private final boolean growthVariant = false;
    private static final int INITIAL_ANIMAL_AMOUNT = 20;
    private static final int INITIAL_ANIMAL_ENERGY = 120;
    private static final int ENERGY_NEEDED_FOR_PROCREATION = 50;
    private static final int ENERGY_TAKEN_BY_PROCREATION = 30;
    private static final int MIN_AMOUNT_OF_MUTATIONS = 0;
    private static final int MAX_AMOUNT_OF_MUTATIONS = 100;
    private static final int GENOME_LENGTH = 10;
    private final boolean behaviorVariant = false;
    private static final int INITIAL_JUNGLE_SIZE = 5;
    private final List<Animal> animals;
    private int animalAmount = INITIAL_ANIMAL_AMOUNT;
    private final WorldMap map;
    private int frameNum;

    public Simulation(WorldMap map) {
        animals = new ArrayList<Animal>();
        this.map = map;
        for (int i = 0; i < INITIAL_ANIMAL_AMOUNT; i++) {
            Genome genome=new Genome(GENOME_LENGTH);
            //Animal animal = new Animal(startingPositions.get(i), 10,genome);
//            try {
//                map.place(animal);
//                animals.add(animal);
//            } catch (PositionAlreadyOccupiedException ignored) {}
        }
    }

    private void prepare() {
        this.frameNum = 0;
    }

    private void frame() {
//        map.move(curAnimal, moves.get(frameNum));
        frameNum++;
    }

    public void run() {
//        for (int i = 0; i < moves.size(); i++) {
        while (true) {
            frame();
            try { Thread.sleep(250); }
            catch (InterruptedException e) { throw new RuntimeException(e); }
        }
    }
}
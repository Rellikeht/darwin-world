package world;

import world.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Simulation implements Runnable {

    private final AbstractWorldMap map;
    private final SimulationSettings settings;
    //private final SimulationStats stats;

    public Simulation(SimulationSettings settings) {
        this.settings = settings;
        if (settings.isMapBasic()) {
            map = new EarthMap(settings);
        } else {
            map = new HellMap(settings);
        }

        //stats = new SimulationStats();

        Vector2d maxPos = map.getUpperRight();
        for (int i = 0; i < settings.getInitialAnimalAmount(); i++) {
            Random random = new Random();
            Genome genome = new Genome(settings.getGenomeLength());

            Animal animal = new Animal(
                    new Vector2d(
                            random.nextInt(maxPos.getX()+1),
                            random.nextInt(maxPos.getY()+1)
                    ),
                    Direction.randomDirection(),
                    settings.getInitialAnimalEnergy(),
                    genome
            );

            map.place(animal);
        }

    }

    // Wszystko domyślne
    public Simulation() { this(new SimulationSettings()); }
    private void wait(int milliseconds) {
        try { Thread.sleep(milliseconds); }
        // Nie wiem, czy by tego jakoś lepiej nie trzeba
        catch (InterruptedException e) { throw new RuntimeException(e); }
    }

    private void frame() {

        map.nextDay();
        map.moveAnimals();
        wait(settings.getTickTime());
        map.doEating();
        wait(settings.getTickTime());
        map.doReproduction();
        wait(settings.getTickTime());
        map.grassPlace();
        wait(settings.getTickTime());
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
    public AbstractWorldMap getMap() { return map; }

    // 6. Program ma pozwalać na śledzenie następujących statystyk dla aktualnej sytuacji w symulacji:
    //    * liczby wszystkich zwierzaków,
    public int getAnimalAmount() { return map.getAnimalsAmount(); }
    //    * liczby wszystkich roślin,
    public int getGrassAmount() { return map.getGrassAmount(); }
    //    * liczby wolnych pól,
    public int getFreeSquares() { return map.getFreeSquares(); }
    //    * najpopularniejszych genotypów,
    public Map<Genome, Integer> getMostPopularGenomes() { return map.getMostPopularGenomes(); }
    //    * średniego poziomu energii dla żyjących zwierzaków,
    public int getAvgAnimalEnergy() { return map.getAvgAnimalEnergy(); }
    //    * średniej długości życia zwierzaków dla martwych zwierzaków (wartość uwzględnia wszystkie nieżyjące zwierzaki - od początku symulacji),
    public int getAvgLifespan() { return map.getAvgLifespan(); }
    //    * średniej liczby dzieci dla żyjących zwierzaków (wartość uwzględnia wszystkie powstałe zwierzaki, a nie tylko zwierzaki powstałe w danej epoce).
    public int getAvgChildrenAmount() { return map.getAvgChildrenAmount(); }

}

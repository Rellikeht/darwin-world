package world;

import world.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Simulation implements Runnable {

    private final WorldMap map;
    private final SimulationSettings settings;

    // TODO staty
    //private final SimulationStats stats;
    private int animalsAmount;
    private final Map<Genome, Integer> genomes;

    public Simulation(SimulationSettings settings) {
        this.settings = settings;
        if (settings.isMapBasic()) {
            map = new EarthMap(settings);
        } else {
            map = new HellMap(settings);
        }

        // TODO staty
        //stats = new SimulationStats();
        this.genomes = new HashMap<>(settings.getInitialAnimalAmount());

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
            Integer count = genomes.getOrDefault(genome, 0);
            genomes.put(genome, count+1);
            animalsAmount = settings.getInitialAnimalAmount();
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
    public WorldMap getMap() { return map; }

    // 6. Program ma pozwalać na śledzenie następujących statystyk dla aktualnej sytuacji w symulacji:
    //    * liczby wszystkich zwierzaków,
    public int getAnimalAmount() { return animalsAmount; }

    //    * liczby wszystkich roślin,
    public int getGrassAmount() { return map.getGrassAmount(); }

    //    * liczby wolnych pól,
    public int getFreeSquares() { return map.getFreeSquares(); }

    //    * najpopularniejszych genotypów,
    // TODO

    //    * średniego poziomu energii dla żyjących zwierzaków,
    public int getAvgAnimalEnergy() { return map.getAvgAnimalEnergy(); }

    //    * średniej długości życia zwierzaków dla martwych zwierzaków (wartość uwzględnia wszystkie nieżyjące zwierzaki - od początku symulacji),
    private int getAvgLifespan() { return map.getAvgLifespan(); }

    //    * średniej liczby dzieci dla żyjących zwierzaków (wartość uwzględnia wszystkie powstałe zwierzaki, a nie tylko zwierzaki powstałe w danej epoce).
    // TODO

}

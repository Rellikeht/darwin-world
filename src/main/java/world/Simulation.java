package world;

import world.model.*;

import java.util.Random;

public class Simulation implements Runnable {

    private final AbstractWorldMap map;
    private final SimulationSettings settings;
    private final SimulationStats stats;
    private final Random random = new Random();

    public Simulation(SimulationSettings settings) {
        this.settings = settings;
        if (settings.get("isMapBasic") == 1) {
            map = new EarthMap(settings);
        } else {
            map = new HellMap(settings);
        }

        Vector2d maxPos = map.getUpperRight();
        for (int i = 0; i < settings.get("InitialAnimalAmount"); i++) {
            Genome genome = new Genome(settings.get("GenomeLength"));

            Animal animal = new Animal(
                    new Vector2d(
                            random.nextInt(maxPos.getX()+1),
                            random.nextInt(maxPos.getY()+1)
                    ),
                    Direction.randomDirection(),
                    settings.get("InitialAnimalEnergy"),
                    genome
            );

            map.place(animal);
        }

        stats = new SimulationStats(map);
    }

    // Wszystko domyślne
    public Simulation() { this(new SimulationSettings()); }
    private void wait(int milliseconds) {
        try { Thread.sleep(milliseconds); }
        catch (InterruptedException e) { throw new RuntimeException(e); }
    }

    private void frame() {

        map.nextDay();
        map.moveAnimals();
        //wait(settings.get("TickTime"));
        map.doEating();
        //wait(settings.get("TickTime"));
        map.doReproduction();
        //wait(settings.get("TickTime"));
        map.grassPlace();
        wait(settings.get("TickTime"));
    }

    public void run() {
        int i=0;
        while (i < 10) {
            System.out.println(i);
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

    public SimulationStats getStats() { return stats; }
    public AbstractWorldMap getMap() { return map; }
    public SimulationSettings getSettings() { return settings; }

}

package world;

import world.model.*;

import java.util.Random;

public class Simulation implements Runnable {

    private final WorldMap map;
    private final SimulationSettings settings;
    private final SimulationStats stats;

    public Simulation(SimulationSettings settings) {
        this.settings = settings;
        if (settings.isMapBasic()) {
            map = new EarthMap(settings);
        } else {
            map = new HellMap(settings);
        }

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

        // TODO staty
        stats = new SimulationStats();
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
        map.grassPlace(settings.getDailyGrassAmount());
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

}

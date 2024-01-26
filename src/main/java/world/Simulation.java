package world;

import world.model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Simulation implements Runnable {

    private static final String CSV_FILE = "Statistics.csv";
    private final AbstractWorldMap map;
    private final SimulationSettings settings;
    private final SimulationStats stats;
    private boolean running = true;

    private void writeToCSV(String filePath, String content) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error saving statistics to CSV: " + e.getMessage());
        }
    }

    private void writeToCSV(String content) {
        writeToCSV(CSV_FILE, content);
    }

    private void appendToCSV(String filePath, String content) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error saving statistics to CSV: " + e.getMessage());
        }
    }

    private void appendToCSV(String content) {
        appendToCSV(CSV_FILE, content);
    }

    private void saveStatisticsToCSV() {  // czy to zadanie dla symulacji?
        appendToCSV(String.format("%s,%s,%s,%s,%s,%s,%s\n",
                stats.getAnimalsAmount(),
                stats.getGrassAmount(),
                stats.getFreeSquares(),
                stats.getMostPopularGenome(),
                stats.getAvgAnimalEnergy(),
                stats.getAvgLifespan(),
                stats.getAvgChildrenAmount()));
    }

    public Simulation(SimulationSettings settings) { // dobrze jest zaczynać od konstruktora
        this.settings = settings;
        if (settings.get("isMapBasic") == 1) { // 1?
            map = new EarthMap(settings);
        } else {
            map = new HellMap(settings);
        }

        Vector2d maxPos = map.getUpperRight();
        for (int i = 0; i < settings.get("InitialAnimalAmount"); i++) {
            Genome genome = new Genome(settings.get("GenomeLength"));

            Random random = new Random(); // co wywołanie?
            Animal animal = new Animal(
                    new Vector2d(
                            random.nextInt(maxPos.getX() + 1),
                            random.nextInt(maxPos.getY() + 1)
                    ),
                    settings.get("InitialAnimalEnergy"), genome
            );

            map.place(animal);
        }

        stats = new SimulationStats(map);
        writeToCSV("AnimalsNumber,PlantsNumber,FreeZones,PopularGenome,AvgEnergy,AvgLife,AvgChildren\n");
    }

    public Simulation() {
        this(new SimulationSettings());
    }

    private void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        running = false;
    }

    public void start() {
        running = true;
    }

    private void frame() { // nazwa
        int tickTime = settings.get("TickTime")
        map.nextDay();
        map.moveAnimals();
        wait(tickTime);
        map.doEating();
        wait(tickTime);
        map.doReproduction();
        wait(tickTime);
        map.grassPlace();
        wait(tickTime);
        saveStatisticsToCSV();
    }

    public void run() {
        while (stats.getAnimalsAmount() > 0) {
            if (running) {
                frame();
            } else {
                System.out.println();
                wait(settings.get("TickTime"));
            }
        }
    }

    public int getDay() {
        return map.getDay();
    }

    public void addListener(MapChangeListener listener) {
        map.addListener(listener);
    }

    public void removeListener(MapChangeListener listener) {
        map.removeListener(listener);
    }

    public SimulationStats getStats() {
        return stats;
    }

    public AbstractWorldMap getMap() {
        return map;
    }

    public SimulationSettings getSettings() {
        return settings;
    }

}
package agh.ics.oop.model;

public class ConsoleMapDisplay implements MapChangeListener {

    private int messages = 0;
    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        synchronized (this) {
            messages++;
            System.out.println(message);
            System.out.printf("Map ID: %d%n", worldMap.getId());
            System.out.println(worldMap.toString());
            System.out.printf("Amount of messages: %d%n", messages);
        }
    }
}

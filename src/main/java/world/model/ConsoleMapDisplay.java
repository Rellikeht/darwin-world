package world.model;

public class ConsoleMapDisplay implements MapChangeListener {

    private int messages = 0;
    @Override
    public void mapChanged(AbstractWorldMap worldMap, String message) {
        synchronized (this) {
            messages++;
            System.out.println(message);
            System.out.printf("Map ID: %d%n", worldMap.getId());
            System.out.println(worldMap);
            System.out.printf("Amount of messages: %d%n", messages);
        }
    }
}

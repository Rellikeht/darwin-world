package world;

import world.model.ConsoleMapDisplay;

public class Main {

    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        ConsoleMapDisplay display = new ConsoleMapDisplay();
        simulation.addListener(display);
        simulation.run();
    }
}

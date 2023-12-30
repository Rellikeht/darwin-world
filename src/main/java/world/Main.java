package world;

import world.model.ConsoleMapDisplay;
import world.model.EarthMap;
import world.model.WorldMap;

public class Main {

    public static void main(String[] args) {
        WorldMap map = new EarthMap(20, 20, 10, 20);
        Simulation simulation = new Simulation(map);
        ConsoleMapDisplay display = new ConsoleMapDisplay();
        map.addListener(display);
        simulation.run();
    }
}

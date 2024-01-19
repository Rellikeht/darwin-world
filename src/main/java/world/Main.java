package world;

import world.model.ConsoleMapDisplay;

public class Main {

    public static void main(String[] args) {
//        Simulation simulation = new Simulation();
//        ConsoleMapDisplay display = new ConsoleMapDisplay();
//        simulation.addListener(display);
//        simulation.run();
        //Application.launch(SimulationApp.class, args);
        SimulationApp app = new SimulationApp();
        try {
            app.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package world;

public class WorldGUI {
    public static void main(String[] args) {
        //Application.launch(SimulationApp.class, args);
        SimulationApp app = new SimulationApp();
        try {
            app.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
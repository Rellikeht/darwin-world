package world;

public class Main {
    public static void main(String[] args) {
        SimulationApp app = new SimulationApp();
        try {
            app.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

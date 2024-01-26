package world;

public class Main {
    public static void main(String[] args) {
        SimulationApp app = new SimulationApp();
        try {
            app.start(); // na pewno tak się uruchamia aplikację w JavaFX?
        } catch (Exception e) { // ?
            throw new RuntimeException(e);
        }
    }
}

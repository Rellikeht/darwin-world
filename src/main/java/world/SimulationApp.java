package world;

import javafx.application.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationApp {

    private static final int DEFAULT_THREADS_AMOUNT = 6;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(DEFAULT_THREADS_AMOUNT);

    // Skopiowane na przyszłość
    private void launchSimulation(Simulation simulation) {threadPool.submit(simulation);}
    private void awaitSimulationsEnd() throws InterruptedException {
        threadPool.shutdown();
        if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
            threadPool.shutdownNow();
        }
    }

    public void start() throws Exception {
        Application.launch(SimulationPresenter.class);
    }

}

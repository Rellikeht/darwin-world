package agh.ics.oop;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {

    private final List<Simulation> simulations;
    private final Thread[] threads;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(4);

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
        this.threads = new Thread[simulations.size()];
    }

    public void runSync() {
        simulations.forEach(Simulation::run);
    }

    public void runAsync() {
        for (int i = 0; i < simulations.size(); i++) {
            threads[i] = new Thread(simulations.get(i));
            threads[i].start();
        }
    }

    public void runAsyncInThreadPool() {
        simulations.forEach(threadPool::submit);
    }

    public void awaitSimulationsEnd() throws InterruptedException {
        threadPool.shutdown();
        if (!threadPool.awaitTermination(10, TimeUnit.SECONDS)) {
            threadPool.shutdownNow();
        }

        for (Thread thread : threads) {
            if (thread != null) {
                thread.join();
            }
        }
    }
}

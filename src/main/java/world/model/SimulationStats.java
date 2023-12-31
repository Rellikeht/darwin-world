package world.model;

import java.util.ArrayList;
import java.util.List;

public class SimulationStats {
    // Można by przynajmniej część liczyć leniwie, ale to już na kiedy indziej chyba
    private int deadAnimalAmount = 0, aliveAnimalAmount = 0;
    private int freeSquares = 0, grassAmount = 0;
    private double averageEnergy = 0, averageLifespan = 0, averageChildrenAmount = 0;
    private final List<Animal> animals;
    // TODO Genotypy

    public SimulationStats(
            int aliveAnimalAmount,
            int deadAnimalAmount,
            int freeSquares,
            int grassAmount,
            List<Animal> animals
            ) {

        this.aliveAnimalAmount = aliveAnimalAmount;
        this.deadAnimalAmount = deadAnimalAmount;
        this.freeSquares = freeSquares;
        this.grassAmount = grassAmount;
        this.animals = new ArrayList<>(animals);
    }

    public SimulationStats() {
        this.animals = new ArrayList<>();
    }

    private void calcAvgEnergy() {}
    private void calcAvgLifespan() {}
    private void calcAvgChildrenAmount() {}
}

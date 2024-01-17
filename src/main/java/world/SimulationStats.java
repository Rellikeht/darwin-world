package world;

import world.model.Animal;
import world.model.Genome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationStats {
    // Można by przynajmniej część liczyć leniwie, ale to już na kiedy indziej chyba
    //private int deadAnimalAmount = 0, aliveAnimalAmount = 0;
    private int freeSquares = 0, grassAmount = 0;
    private double averageEnergy = 0, averageLifespan = 0, averageChildrenAmount = 0;
    private final List<Animal> animals;
    private final Map<Genome, Integer> genomes;

    public SimulationStats(
            //int aliveAnimalAmount,
            //int deadAnimalAmount,
            int freeSquares,
            int grassAmount,
            List<Animal> animals
            ) {

        //this.aliveAnimalAmount = aliveAnimalAmount;
        //this.deadAnimalAmount = deadAnimalAmount;
        this.freeSquares = freeSquares;
        this.grassAmount = grassAmount;
        this.animals = new ArrayList<>(animals);
        this.genomes = new HashMap<>(animals.size());
        for (Animal animal: animals) {
            Genome genome = animal.getGenes();
            Integer count = genomes.getOrDefault(genome, 0);
            genomes.put(genome, count+1);
        }
    }

    public SimulationStats() {
        this.animals = new ArrayList<>();
        this.genomes = new HashMap<>();
    }

    private void calcAvgEnergy() {}
    private void calcAvgLifespan() {}
    private void calcAvgChildrenAmount() {}
}

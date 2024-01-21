package world.model;

import java.util.concurrent.atomic.AtomicReference;

public class SimulationStats {
    private final AbstractWorldMap map;

    public SimulationStats(AbstractWorldMap map) {
        this.map = map;
    }

    // 6. Program ma pozwalać na śledzenie następujących statystyk dla aktualnej sytuacji w symulacji:
    //    * liczby wszystkich zwierzaków,
    public int getAnimalsAmount() { return map.getAnimalsAmount(); }

    //    * liczby wszystkich roślin,
    public int getGrassAmount() { return map.getGrassAmount(); }

    //    * liczby wolnych pól,
    public int getFreeSquares() { return map.getFreeSquares(); }

    //    * najpopularniejszych genotypów,
    //SortedMap<Integer, Genome> getMostPopularGenomes() {
    //    Map<Genome, Integer> mapGenomes = map.getMostPopularGenomes();
    //    SortedMap<Integer, Genome> genomes = new TreeMap<>();
    //    mapGenomes.forEach((genome, count) -> genomes.put(count, genome));
    //    return genomes;
    //}

    public Genome getMostPopularGenome() {
        // Concurrent Modification Exception ???
        //SortedMap<Integer, Genome> genomes = getMostPopularGenomes();
        //return genomes.get(genomes.firstKey());

        AtomicReference<Genome> genome = new AtomicReference<>();
        int count = 0;
        map.getMostPopularGenomes().forEach((curGenome, curCount) -> {
            if (curCount >= count) genome.set(curGenome);
        });
        return genome.get();
    }

    //    * średniej długości życia zwierzaków dla martwych zwierzaków (wartość uwzględnia wszystkie nieżyjące zwierzaki - od początku symulacji),
    public int getAvgLifespan() {
        if (map.getDeadAnimalsAmount() > 0) {
            return map.getDeadAnimalsLifespanSum() / map.getDeadAnimalsAmount();
        }
        return 0;
    }

    //    * średniej liczby dzieci dla żyjących zwierzaków (wartość uwzględnia wszystkie powstałe zwierzaki, a nie tylko zwierzaki powstałe w danej epoce).
    public int getAvgChildrenAmount() {
        if (map.getAnimalsAmount() == 0) return 0;
        return map.allAnimals().mapToInt(Animal::getChildrenAmount).sum() / map.getAnimalsAmount();
    }

    //    * średniego poziomu energii dla żyjących zwierzaków,
    public int getAvgAnimalEnergy() {
        if (map.getAnimalsAmount() == 0) return 0;
        return map.allAnimals().mapToInt(Animal::getEnergy).sum()/ map.getAnimalsAmount();
    }

}
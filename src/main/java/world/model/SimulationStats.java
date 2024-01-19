package world.model;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class SimulationStats {
    private final AbstractWorldMap map;

    public SimulationStats(AbstractWorldMap map) {
        this.map = map;
    }

    // 6. Program ma pozwalać na śledzenie następujących statystyk dla aktualnej sytuacji w symulacji:
    //    * liczby wszystkich zwierzaków,
    public int getAnimalAmount() { return map.getAnimalsAmount(); }

    //    * liczby wszystkich roślin,
    public int getGrassAmount() { return map.getGrassAmount(); }

    //    * liczby wolnych pól,
    public int getFreeSquares() { return map.getFreeSquares(); }

    //    * najpopularniejszych genotypów,
    public SortedMap<Integer, Genome> getMostPopularGenomes() {
        Map<Genome, Integer> mapGenomes = map.getMostPopularGenomes();
        SortedMap<Integer, Genome> genomes = new TreeMap<>();
        mapGenomes.forEach((genome, count) -> genomes.put(count, genome));
        return genomes;
    }

    //    * średniego poziomu energii dla żyjących zwierzaków,
    public int getAvgAnimalEnergy() { return map.getAvgAnimalEnergy(); }

    //    * średniej długości życia zwierzaków dla martwych zwierzaków (wartość uwzględnia wszystkie nieżyjące zwierzaki - od początku symulacji),
    public int getAvgLifespan() { return map.getAvgLifespan(); }

    //    * średniej liczby dzieci dla żyjących zwierzaków (wartość uwzględnia wszystkie powstałe zwierzaki, a nie tylko zwierzaki powstałe w danej epoce).
    public int getAvgChildrenAmount() { return map.getAvgChildrenAmount(); }

}

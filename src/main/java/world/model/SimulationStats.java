package world.model;

import world.model.AbstractWorldMap;
import world.model.Animal;
import world.model.Genome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationStats {

    //private int deadAnimalAmount = 0, aliveAnimalAmount = 0;
    //private int freeSquares = 0, grassAmount = 0;
    //private double averageEnergy = 0, averageLifespan = 0, averageChildrenAmount = 0;
    //private final List<Animal> animals;
    //private final Map<Genome, Integer> genomes;

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
    public Map<Genome, Integer> getMostPopularGenomes() { return map.getMostPopularGenomes(); }
    //    * średniego poziomu energii dla żyjących zwierzaków,
    public int getAvgAnimalEnergy() { return map.getAvgAnimalEnergy(); }
    //    * średniej długości życia zwierzaków dla martwych zwierzaków (wartość uwzględnia wszystkie nieżyjące zwierzaki - od początku symulacji),
    public int getAvgLifespan() { return map.getAvgLifespan(); }
    //    * średniej liczby dzieci dla żyjących zwierzaków (wartość uwzględnia wszystkie powstałe zwierzaki, a nie tylko zwierzaki powstałe w danej epoce).
    public int getAvgChildrenAmount() { return map.getAvgChildrenAmount(); }

}

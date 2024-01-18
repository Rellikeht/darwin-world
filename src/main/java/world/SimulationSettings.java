package world;

public class SimulationSettings {

    // Wielkości mapy
    private static final int DEFAULT_MAP_HEIGHT = 15;
    private int mapHeight = DEFAULT_MAP_HEIGHT;
    public int getMapHeight() {return mapHeight;}
    void setMapHeight(int mapHeight) {this.mapHeight = mapHeight;}

    private static final int DEFAULT_MAP_WIDTH = 15;
    private int mapWidth = DEFAULT_MAP_WIDTH;
    public int getMapWidth() {return mapWidth;}
    void setMapWidth(int mapWidth) {this.mapWidth = mapWidth;}

    private static final int DEFAULT_INITIAL_GRASS_AMOUNT = 3;
    private int initialGrassAmount = DEFAULT_INITIAL_GRASS_AMOUNT;
    public int getInitialGrassAmount() {return initialGrassAmount;}
    void setInitialGrassAmount(int initialGrassAmount) {this.initialGrassAmount = initialGrassAmount;}

    private static final int DEFAULT_DAILY_GRASS_AMOUNT = 20;
    private int dailyGrassAmount = DEFAULT_DAILY_GRASS_AMOUNT;
    public int getDailyGrassAmount() {return dailyGrassAmount;}
    void setDailyGrassAmount(int dailyGrassAmount) {this.dailyGrassAmount = dailyGrassAmount;}

    private static final int DEFAULT_JUNGLE_SIZE = 5;
    private int jungleSize = DEFAULT_JUNGLE_SIZE;
    public int getJungleSize() {return jungleSize;}
    void setJungleSize(int jungleSize) {this.jungleSize = jungleSize;}

    private static final int DEFAULT_INITIAL_ANIMAL_AMOUNT = 15;
    private int initialAnimalAmount = DEFAULT_INITIAL_ANIMAL_AMOUNT;
    public int getInitialAnimalAmount() {return initialAnimalAmount;}
    void setInitialAnimalAmount(int initialAnimalAmount) {this.initialAnimalAmount = initialAnimalAmount;}

    // Energia
    private static final int DEFAULT_INITIAL_ANIMAL_ENERGY = 120;
    private int initialAnimalEnergy = DEFAULT_INITIAL_ANIMAL_ENERGY;
    public int getInitialAnimalEnergy() {return initialAnimalEnergy;}
    void setInitialAnimalEnergy(int initialAnimalEnergy) {this.initialAnimalEnergy = initialAnimalEnergy;}

    private static final int DEFAULT_GRASS_ENERGY = 20;
    private int grassEnergy = DEFAULT_GRASS_ENERGY;
    public int getGrassEnergy() {return grassEnergy;}
    void setGrassEnergy(int grassEnergy) {this.grassEnergy = grassEnergy;}

    private static final int DEFAULT_ENERGY_TAKEN_BY_PROCREATION = 30;
    private int energyTakenByProcreation = DEFAULT_ENERGY_TAKEN_BY_PROCREATION;
    public int getEnergyTakenByProcreation() {return energyTakenByProcreation;}
    void setEnergyTakenByProcreation(int energyTakenByProcreation) {this.energyTakenByProcreation = energyTakenByProcreation;}


    private static final int DEFAULT_ENERGY_NEEDED_FOR_PROCREATION = 50;
    private int energyNeededForProcreation = DEFAULT_ENERGY_NEEDED_FOR_PROCREATION;
    public int getEnergyNeededForProcreation() {return energyNeededForProcreation;}
    void setEnergyNeededForProcreation(int energyNeededForProcreation) {this.energyNeededForProcreation = energyNeededForProcreation;}

    private static final int DEFAULT_ENERGY_TAKEN_BY_MOVEMENT = 5;
    private int energyTakenByMovement = DEFAULT_ENERGY_TAKEN_BY_MOVEMENT;
    public int getEnergyTakenByMovement() {return energyTakenByMovement;}
    void setEnergyTakenByMovement(int energyTakenByMovement) {this.energyTakenByMovement = energyTakenByMovement;}

    // Genom
    private static final int DEFAULT_MIN_AMOUNT_OF_MUTATIONS = 0;
    private int minAmountOfMutations = DEFAULT_MIN_AMOUNT_OF_MUTATIONS;
    public int getMinAmountOfMutations() {return minAmountOfMutations;}
    void setMinAmountOfMutations(int minAmountOfMutations) {this.minAmountOfMutations = minAmountOfMutations;}

    private static final int DEFAULT_MAX_AMOUNT_OF_MUTATIONS = 100;
    private int maxAmountOfMutations = DEFAULT_MAX_AMOUNT_OF_MUTATIONS;
    public int getMaxAmountOfMutations() {return maxAmountOfMutations;}
    void setMaxAmountOfMutations(int maxAmountOfMutations) {this.maxAmountOfMutations = maxAmountOfMutations;}

    private static final int DEFAULT_GENOME_LENGTH = 10;
    private int genomeLength = DEFAULT_GENOME_LENGTH;
    public int getGenomeLength() {return genomeLength;}
    void setGenomeLength(int genomeLength) {this.genomeLength = genomeLength;}

    // Flagi
    private static final boolean DEFAULT_IS_MUTATION_RANDOM = false;
    private boolean isMutationRandom = DEFAULT_IS_MUTATION_RANDOM;
    public boolean isMutationRandom() {return isMutationRandom;}
    void setMutationRandom(boolean mutationRandom) {isMutationRandom = mutationRandom;}

    private static final boolean DEFAULT_IS_MAP_BASIC = true;
    private boolean isMapBasic = DEFAULT_IS_MAP_BASIC;
    public boolean isMapBasic() {return isMapBasic;}
    void setMapBasic(boolean mapBasic) {isMapBasic = mapBasic;}

    // Inne
    private static final int DEFAULT_TICK_TIME = 300;
    private int tickTime = DEFAULT_TICK_TIME;
    public int getTickTime() { return tickTime; }
    void setTickTime(int tickTime) { this.tickTime = tickTime; }

    private static final int DEFAULT_PORTAL_ENERGY = 10;
    private int portalEnergy = DEFAULT_PORTAL_ENERGY;
    public int getPortalEnergy() { return portalEnergy; }
    void setPortalEnergy(int portalEnergy) { this.portalEnergy = portalEnergy; }

    public int getMapSize() { return mapWidth*mapHeight; }
    public SimulationSettings() {
        // Wszystko domyślne
    }

    // To pod testy, nie wiem, czy potrzebujemy
    public SimulationSettings(
            int mapWidth, int mapHeight,
            int initialGrassAmount, int jungleSize
    ) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.initialGrassAmount = initialGrassAmount;
        this.jungleSize = jungleSize;
    }

}

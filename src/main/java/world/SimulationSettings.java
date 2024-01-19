package world;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SimulationSettings {

    //private static String transformName(String name) {
    //    return name.toLowerCase();
    //}

    private static final Map<String, Integer> defaultSettings = new HashMap<>();
    private static void addSetting(String name, int defaultValue) {
        defaultSettings.put(name, defaultValue);
    }

    static {

        addSetting("mapHeight", 15);
        addSetting("mapWidth", 15);
        addSetting("initialGrassAmount", 3);
        addSetting("dailyGrassAmount", 20);
        addSetting("jungleSize", 5);
        addSetting("initialAnimalAmount", 15);
        addSetting("initialAnimalEnergy", 120);
        addSetting("grassEnergy", 20);
        addSetting("energyTakenByProcreation", 30);
        addSetting("energyNeededForProcreation", 50);
        addSetting("energyTakenByMovement", 5);
        addSetting("minAmountOfMutations", 0);
        addSetting("maxAmountOfMutations", 100);
        addSetting("genomeLength", 10);
        addSetting("isMutationRandom", 0);
        addSetting("isMapBasic", 1);
        addSetting("tickTime", 300);
        addSetting("portalEnergy", 10);

        // TODO readolny calculated
        //public int getMapSize() { return mapWidth*mapHeight; }
    }

    private final Map<String, Integer> settings;

    // TODO błedy ???
    public int get(String name) {
        return settings.get(name);
    }
    void set(String name, int value) {
        if (settings.containsKey(name)) {
            settings.put(name, value);
        }
    }

    public SimulationSettings() {
        settings = new HashMap<>(defaultSettings);
    }

    // To pod testy, nie wiem, czy potrzebujemy
    public SimulationSettings(
            int mapWidth, int mapHeight,
            int initialGrassAmount, int jungleSize
    ) {

        this();
        set("mapWidth", mapWidth);
        set("mapHeight", mapHeight);
        set("initialGrassAmount", initialGrassAmount);
        set("jungleSize", jungleSize);
    }

}

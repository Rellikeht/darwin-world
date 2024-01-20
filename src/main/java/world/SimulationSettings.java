package world;

import java.util.HashMap;
import java.util.Map;

public class SimulationSettings {

    private static String inputName(String name) {
        return name.toLowerCase();
    }
    private static String outputName(String name) {
        return name; // TODO camel case if this will be used
    }

    private static final Map<String, Integer> defaultSettings = new HashMap<>();
    private final Map<String, Integer> settings;
    private static void addSetting(String name, int defaultValue) {
        defaultSettings.put(inputName(name), defaultValue);
    }

    static {

        addSetting("mapHeight", 10); // 10
        addSetting("mapWidth", 10); // 10
        addSetting("initialGrassAmount", 1); // 3
        addSetting("dailyGrassAmount", 2); // 20
        addSetting("jungleSize", 6); // 5
        addSetting("initialAnimalAmount", 1); // 15
        addSetting("initialAnimalEnergy", 40); // 5
        addSetting("grassEnergy", 30); // 20
        addSetting("energyTakenByProcreation", 30); // 30
        addSetting("energyNeededForProcreation", 50); // 500
        addSetting("energyTakenByMovement", 5); // 5
        addSetting("genomeLength", 15); // 10
        addSetting("minAmountOfMutations", 0); // 0
        addSetting("maxAmountOfMutations", 12); // 100
        addSetting("isMutationRandom", 0); // 0
        addSetting("isMapBasic", 1); // 1
        addSetting("portalEnergy", 10); // 10

        addSetting("tickTime", 400); // 300
        addSetting("energyColorStep", 50);
    }

    // TODO błedy ???
    public int get(String name) {
        return settings.get(inputName(name));
    }

    void set(String name, int value) {
        String transformedName = inputName(name);
        if (settings.containsKey(transformedName)) {
            settings.put(transformedName, value);
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

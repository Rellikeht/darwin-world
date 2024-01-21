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

        addSetting("mapHeight", 30); // 10
        addSetting("mapWidth", 30); // 10
        addSetting("initialGrassAmount", 40); // 3
        addSetting("dailyGrassAmount", 20);
        addSetting("jungleSize", 9); // 5
        addSetting("initialAnimalAmount", 20); // 15
        addSetting("initialAnimalEnergy", 120); // 5
        addSetting("grassEnergy", 40); // 20
        addSetting("energyTakenByProcreation", 40); // 30
        addSetting("energyNeededForProcreation", 60); // 500
        addSetting("energyTakenByMovement", 5);
        addSetting("genomeLength", 20); // 10
        addSetting("minAmountOfMutations", 0);
        addSetting("maxAmountOfMutations", 16); // 100
        addSetting("isMutationRandom", 0);
        addSetting("isMapBasic", 1);
        addSetting("portalEnergy", 10);

        addSetting("tickTime", 300);
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

    public SimulationSettings(SimulationSettings settings) {
        this.settings = new HashMap<>(settings.settings);
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

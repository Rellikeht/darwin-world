package world.model;

public interface WorldMap {

    Vector2d getUpperRight();
    void place(Animal animal);
    int getId();

    void addListener(MapChangeListener listener);
    void removeListener(MapChangeListener listener);

    void moveAnimals();
    void doEating(int grassEnergy);
    void doReproduction(int energyNeeded, int energyTaken);
    void nextDay();
    void grassPlace(int N);
    String getAt(Vector2d currentPosition);
}
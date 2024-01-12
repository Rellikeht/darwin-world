package world.model;

public interface WorldMap {

    Vector2d getUpperRight();
    Vector2d getLowerLeft();
    void place(Animal animal);
    int getId();

    void addListener(MapChangeListener listener);
    void removeListener(MapChangeListener listener);

    void moveAnimals();
    void doEating();
    void doReproduction();
    void nextDay();
    void grassPlace(int N);
    String getAt(Vector2d currentPosition);
}
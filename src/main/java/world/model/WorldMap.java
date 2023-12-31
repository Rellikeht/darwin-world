package world.model;

import java.util.List;

public interface WorldMap {

    Vector2d getUpperRight();
    void place(Animal animal); // throws PositionAlreadyOccupiedException;
    void move(Animal animal);
    int getId();
    List<Animal> getAnimals();
    List<Animal> animalsAt(Vector2d position);

    void addListener(MapChangeListener listener);
    void removeListener(MapChangeListener listener);

    String getAt(Vector2d currentPosition);
    //WorldElement objectAt(Vector2d position);

    // ??
//    boolean isOccupied(Vector2d position);
    //List<WorldElement> getElements();
    //Boundary getCurrentBounds();

}
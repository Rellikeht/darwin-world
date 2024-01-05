package world.model;

import java.util.List;
import java.util.Set;

public interface WorldMap {

    Vector2d getUpperRight();
    void place(Animal animal); // throws PositionAlreadyOccupiedException;
    //void move(Animal animal);
    int getId();

    void addListener(MapChangeListener listener);
    void removeListener(MapChangeListener listener);

    // TODO Tu trzeba wykombinować typ
    // Albo czy to w ogóle potrzebne
    //List<Animal> getAnimals();
    //List<Animal> animalsAt(Vector2d position);
    //Set<Vector2d> grassPositions();

    void moveAnimals();
    void doEating(int grassEnergy);
    void doReproduction();
    void nextDay();

    String getAt(Vector2d currentPosition);
    //WorldElement objectAt(Vector2d position);

}
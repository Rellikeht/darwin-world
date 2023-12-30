package world.model;

import world.model.MoveDirection;
import world.model.Vector2d;

import java.util.List;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends MoveValidator {

    //void place(Animal animal) throws PositionAlreadyOccupiedException;

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    //void move(Animal animal, MoveDirection direction);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */

    //List<WorldElement> getElements();

    //Boundary getCurrentBounds();
    Vector2d getUpperRight();

    //void addListener(MapChangeListener listener);

    //void removeListener(MapChangeListener listener);

    int getId();

    // Tutaj generalnie można by zrobić logikę, żeby wywalać obiekt lub ilość na danym polu,
    // albo tylko ilość, jak będziemy leniwi
    //WorldElement objectAt(Vector2d position);
    int amountAt(Vector2d currentPosition);
}

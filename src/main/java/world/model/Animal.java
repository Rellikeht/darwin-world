package world.model;

import static world.model.MapDirection.*;

public class Animal implements WorldElement {

    private MapDirection orientation = NORTH;
    private Vector2d position = new Vector2d(2, 2);

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    private int energy;

    public Animal() {
        // simply default constructor
    }
    public Animal(Vector2d position,int energy) {

        this.position = position;
        this.energy=energy;
    }

    @Override
    public String toString() {
        return switch (this.orientation) {
            case NORTH -> "↑";
            case EAST -> "→";
            case SOUTH -> "↓";
            case WEST -> "←";
        };

        //return "Pozycja: %s\nOrientacja: %s"
        //        .formatted(this.position.toString(),
        //                this.orientation.toString());
    }

    public boolean isAt(Vector2d position) { return this.position.equals(position); }

    // Czy to jest dobrze i dlaczego jest źle
    public Vector2d getPosition() { return this.position; }
    public MapDirection getOrientation() {return this.orientation;}

    private static Vector2d newPos(Vector2d position, MapDirection orientation, MoveDirection direction) {
        return switch (direction) {
            case FORWARD -> position.add(orientation.toUnitVector());
            case BACKWARD -> position.subtract(orientation.toUnitVector());
            default -> position;
        };
    }

    public void move(MoveDirection direction, MoveValidator validator) {
        Vector2d newPos = newPos(position, orientation, direction);
        switch (direction) {
            case FORWARD, BACKWARD -> { if (validator.canMoveTo(newPos)) { this.position = newPos; }; }
            case LEFT -> this.orientation = this.orientation.previous();
            case RIGHT -> this.orientation = this.orientation.next();
        }
    }
}
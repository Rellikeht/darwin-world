package agh.ics.oop.model;

public class PositionAlreadyOccupiedException extends Exception {

    private final String message;

    public PositionAlreadyOccupiedException(Vector2d position) {
        message = "Position (%d, %d) is already occupied".formatted(
                position.getX(), position.getY()
        );
    }
}

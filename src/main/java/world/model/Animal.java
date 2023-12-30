package world.model;

public class Animal implements WorldElement {

    private Direction direction;
    private Vector2d position;
    private int energy;
    private final Genome genes;

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public Animal(Vector2d position, Direction direction, int energy, Genome genes) {
        this.position = position;
        this.direction = direction;
        this.genes = genes;
        this.energy = energy;
    }

//    @Override
//    public String toString() {
//        return switch (this.orientation) {
//            case NORTH -> "u";
//            case EAST -> "r";
//            case SOUTH -> "d";
//            case WEST -> "l";
//        };
//    }

    public boolean isAt(Vector2d position) { return this.position.equals(position); }

    public Vector2d getPosition() { return this.position; }
    public Direction getOrientation() { return this.direction; }

//    private static Vector2d newPos(Vector2d position, Orientation orientation, MoveDirection direction) {
//        return switch (direction) {
//            case FORWARD -> position.add(orientation.toUnitVector());
//            case BACKWARD -> position.subtract(orientation.toUnitVector());
//            default -> position;
//        };
//    }

//    public void move(MoveDirection direction, MoveValidator validator) {
//        Vector2d newPos = newPos(position, orientation, direction);
//        switch (direction) {
//            case FORWARD, BACKWARD -> {
////                if (validator.canMoveTo(newPos)) {
//                    this.position = newPos;
////                };
//            }
//            case LEFT -> this.orientation = this.orientation.previous();
//            case RIGHT -> this.orientation = this.orientation.next();
//        }
//    }
//
}
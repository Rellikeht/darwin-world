package world.model;

public class Animal implements WorldElement {

    // Atrybuty
    private Direction direction;
    private Vector2d position;
    private final Genome genes;
    private int energy;

    // Staty
    private int grassEaten = 0;

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

        // TODO staty
    }

    @Override
    public String toString() {
        return switch (this.direction) {
            case D0 -> "↑";
            case D45 -> "↗";
            case D90 -> "→";
            case D135 -> "↘";
            case D180 -> "↓";
            case D225 -> "↙";
            case D270 -> "←";
            case D315 -> "↖";
        };
    }

    //public boolean isAt(Vector2d position) { return this.position.equals(position); }

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
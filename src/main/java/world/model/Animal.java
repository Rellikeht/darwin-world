package world.model;

public class Animal implements WorldElement {

    // Atrybuty
    private Direction direction;
    private Vector2d position;
    private final Genome genes;
    private int energy;

    // Staty
    private int grassEaten = 0;
    private int dayOfBirth;
    private int dayOfDeath = -1; // To by było fajnie final mieć, ale chyba się nie da
    private int childrenAmount = 0;

    // TODO ilość potomków, tutaj trzeba będzie albo mieć referencje do dzieci,
    // Albo referencje do rodziców i dodawać potomków rekurencyjnie (brzmi bardzo źle xd)

    public Animal(Vector2d position, Direction direction, int energy, Genome genes, int dayOfBirth) {
        this.position = position;
        this.direction = direction;
        this.genes = genes;
        this.energy = energy;

        this.dayOfBirth = dayOfBirth;
        // TODO staty
    }

    public Animal(Vector2d position, Direction direction, int energy, Genome genes) {
        this(position, direction, energy, genes, 0);
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

    public int getEnergy() {
        return energy;
    }

    //    public void setEnergy(int energy) { this.energy = energy; }
    // Tak mi się wydaje, że będzie lepiej, bo przecież im nigdy nie będziemy
    // bezpośrednio ustawiali energii
    public void getEnergy(int energy) { this.energy += energy; }

    // A tu od razu można umieranie zrobić
    public void loseEnergy(int energy) { this.energy -= energy; }
    public void addChildren() { this.childrenAmount += 1; }

    // Może to wyjdzie w ten sposób
    public void die(int day) {
        this.dayOfDeath = day;
    }

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

}
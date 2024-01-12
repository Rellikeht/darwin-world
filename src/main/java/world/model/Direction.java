package world.model;

import java.util.Random;

public enum Direction {

    D0(0),
    D45(1),
    D90(2),
    D135(3),
    D180(4),
    D225(5),
    D270(6),
    D315(7);

    private final Vector2d vector;
    private final int number;
    private static final int amount = Direction.values().length;
    private static final Random random = new Random();

    private static final String[] arrows = {
            "↑", "↗", "→", "↘", "↓", "↙", "←", "↖",
    };
    private static final String[] letters = {
            "u", "ur", "r", "dr", "d", "dl", "l", "ul",
    };

    public String toString() { return arrows[this.number]; }
    public String toLetter() { return letters[this.number]; }

    Direction(int number) {
        this.number = number;
        this.vector = new Vector2d(
                (int) Math.round(Math.sin(number*Math.PI/4)),
                (int) Math.round(Math.cos(number*Math.PI/4))
        );
    }

    public Vector2d toUnitVector() { return vector; }
    public int getNumber() { return number; }

    public static Direction getDirection(int i) { return Direction.values()[i]; }
    public static Direction randomDirection() { return getDirection(random.nextInt(amount)); }
    public Direction rotate(Direction change) {
        return Direction.values()[(this.number + change.number) % Direction.amount];
    }
    public Direction rotate(int change) { return rotate(getDirection(change)); }

}

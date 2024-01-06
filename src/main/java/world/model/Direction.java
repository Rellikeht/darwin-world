package world.model;

import java.util.Random;

public enum Direction {

    // To z numeracją pozwoli nieco łatwiej je dodawać
    // Nazwy nie sugerujące kierunków świata będą mniej kuły w oczy,
    // jak użyjemy ich też do zmiany kierunku
    D0(0),
    D45(1),
    D90(2),
    D135(3),
    D180(4),
    D225(5),
    D270(6),
    D315(7);

    private final Vector2d vector;

    // Czysto na wszelki wypadek
    private final int number;
    private static final int amount = Direction.values().length;
    private static final Random random = new Random();

    Direction(int number) {
        this.number = number;
        this.vector = new Vector2d(
                (int) Math.round(Math.sin(number*Math.PI/4)),
                (int) Math.round(Math.cos(number*Math.PI/4))
        );
    }

    public Vector2d toUnitVector() { return vector; }
    public int getNumber() { return number; }

    public static Direction getDirection(int i) {
        return Direction.values()[i];
    }
    public static Direction randomDirection() {
        return getDirection(random.nextInt(amount));
    }

    public Direction rotate(Direction change) {
        return Direction.values()[(this.number + change.number) % Direction.amount];
    }

    // Jakby się miało przydać to lepiej to wsadzić tam u góry
//    public String toString() {
//        return switch (this) {
//            case N -> "Północ";
//            case NE -> "Północny wschód";
//            case E -> "Wschód";
//            case SE -> "Południowy wschód";
//            case S -> "Południe";
//            case SW -> "Południowy zachód";
//            case W -> "Zachód";
//            case NW -> "Północny zachód";
//        };
//    }

}

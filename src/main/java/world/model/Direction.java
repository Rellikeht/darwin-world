package world.model;

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

    Direction(int number) {
        this.number = number;
        this.vector = new Vector2d(
                (int) Math.round(Math.sin(number*Math.PI/4)),
                (int) Math.round(Math.cos(number*Math.PI/4))
        );
    }

    public Vector2d toUnitVector() { return vector; }
    public int getNumber() { return number; }

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
//
//    public MapDirection next() {
//        return switch (this) {
//            case NORTH -> EAST;
//            case SOUTH -> WEST;
//            case EAST -> SOUTH;
//            case WEST -> NORTH;
//        };
//    }
//
//    public MapDirection previous() {
//        return switch (this) {
//            case NORTH -> WEST;
//            case SOUTH -> EAST;
//            case EAST -> NORTH;
//            case WEST -> SOUTH;
//        };
//    }

}

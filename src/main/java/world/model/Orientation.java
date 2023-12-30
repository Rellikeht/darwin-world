package world.model;

public enum Orientation {

    // To z numeracją pozwoli nieco łatwiej je dodawać
    N(0),
    NE(1),
    E(2),
    SE(3),
    S(4),
    SW(5),
    W(6),
    NW(7);

    private final Vector2d vector;

    Orientation(int num) {
        this.vector = new Vector2d( // Działa jakby co
                (int) Math.round(Math.sin(num*Math.PI/4)),
                (int) Math.round(Math.cos(num*Math.PI/4))
        );
    }

    public Vector2d toUnitVector() {
        return vector;
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

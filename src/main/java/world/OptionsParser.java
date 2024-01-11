package world;

import world.model.Direction;
import java.util.ArrayList;
import java.util.List;

public class OptionsParser {

    public static List<Direction> parse(String[] args) throws IllegalArgumentException {
        var result = new ArrayList<Direction>();
        for (String a: args) {
            switch (a) {
                // TODO nie wiem, czy to potrzebne, ale chuj xd
                //case "f" -> result.add(Direction.FORWARD);
                //case "b" -> result.add(Direction.BACKWARD);
                //case "l" -> result.add(Direction.LEFT);
                //case "r" -> result.add(Direction.RIGHT);
                //default -> throw new IllegalArgumentException(a + " is not a legal move specification.");
                default -> result.add(Direction.D0);
            }
        }
        return result;
    }
}

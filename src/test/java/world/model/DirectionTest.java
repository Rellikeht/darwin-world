package world.model;

import org.junit.jupiter.api.Test;

class DirectionTest {

    @Test
    void toUnitVector() {
        for (int i = 0; i < Direction.values().length; i++) {
            System.out.println(Direction.values()[i].toUnitVector());
        }
    }

    @Test
    void values() {
        for (int i = 0; i < Direction.values().length; i++) {
            System.out.println(Direction.values()[i]);
        }
    }
}
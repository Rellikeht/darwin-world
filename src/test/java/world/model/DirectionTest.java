package world.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

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

    @Test
    void rotate() {
        Direction[] values = Direction.values();
        System.out.println(Arrays.toString(values));
        System.out.println();

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length; j++) {
                System.out.printf("%s + %s = %s\n", values[i], values[j], values[i].rotate(values[j]));
            }
        }
    }
}
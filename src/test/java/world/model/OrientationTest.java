package world.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrientationTest {

    @Test
    void toUnitVector() {
        for (int i = 0; i < Orientation.values().length; i++) {
            System.out.println(Orientation.values()[i].toUnitVector());
        }
    }

    @Test
    void values() {
        for (int i = 0; i < Orientation.values().length; i++) {
            System.out.println(Orientation.values()[i]);
        }
    }
}
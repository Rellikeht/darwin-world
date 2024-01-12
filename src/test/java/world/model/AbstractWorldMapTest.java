package world.model;

import org.junit.jupiter.api.Test;
import world.SimulationSettings;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AbstractWorldMapTest {
    @Test
    void mapGenTest(){
        int tests = 30;
        Random random = new Random();
        for (int i = 0; i < tests; i++) {
            // 8 to max, żeby starczyło na strefę pod i nad równikiem
            // przy wielkości 10
            int jungleSize = random.nextInt(8)+1;

            System.out.printf("%d: %d\n", i, jungleSize);
            SimulationSettings settings = new SimulationSettings(
                    50, 50, 10, jungleSize
            );

            EarthMap map = new EarthMap(settings);
            map.place(new Animal(
                    new Vector2d(
                            1,1
                    ),
                    Direction.randomDirection(),
                    10,
                    new Genome(7)
            ));
            map.moveAnimals();
        }
    }


}

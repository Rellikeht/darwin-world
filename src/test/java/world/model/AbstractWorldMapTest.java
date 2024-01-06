package world.model;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AbstractWorldMapTest {
    @Test
    void mapGenTest(){
        int tests = 30;
        Random random = new Random();
        for (int i = 0; i < tests; i++) {
            // 8 to max, żeby starczyło na strefę pod i nad równikiem
            int jungleSize = random.nextInt(8)+1;

            System.out.printf("%d: %d\n", i, jungleSize);
            EarthMap map = new EarthMap(10, 10, 10, jungleSize);
        }
    }


}
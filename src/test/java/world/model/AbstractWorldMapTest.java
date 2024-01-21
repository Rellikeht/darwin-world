package world.model;

import org.junit.jupiter.api.Test;
import world.SimulationSettings;

import java.util.*;

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
                    10, new Genome(7)
            ));
            map.moveAnimals();
        }
    }


    @Test
    void getSingleFittest() {
        Vector2d pos = new Vector2d(1,1);
        Animal[] animals = new Animal[] {
                new Animal(pos, 5, new Genome(7)),
                new Animal(pos, 10, new Genome(7)),
                new Animal(pos, 10, new Genome(7)),
                new Animal(pos, 10, new Genome(7)),
                new Animal(pos, 10, new Genome(7), 2),
        };
        animals[1].childrenAmount = 1;
        animals[2].childrenAmount = 3;

        List<Animal> alist = new ArrayList<>(Arrays.stream(animals).toList());
        Animal fittest;
        while (!alist.isEmpty()) {
            fittest = AbstractWorldMap.getSingleFittest(alist);
            assert fittest != null;
            System.out.println(fittest.getInfo());
            alist.remove(fittest);
        }
    }
}

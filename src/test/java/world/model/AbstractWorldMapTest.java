package world.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractWorldMapTest {
    @Test
    void mapGenTest(){

        EarthMap map=new EarthMap(100,100,10,10);
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
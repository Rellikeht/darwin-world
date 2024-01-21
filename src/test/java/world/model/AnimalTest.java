package world.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Vector;

class AnimalTest {

    @Test
    void compareTo() {
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

        PriorityQueue<Animal> queue = new PriorityQueue<>(20);
        queue.addAll(Arrays.asList(animals));
        while (!queue.isEmpty()) System.out.println(queue.poll().getInfo());

        //System.out.println();
        // To nie leci w kolejności :(
        //queue.forEach(animal -> System.out.println(animal.getInfo()));

    }
}
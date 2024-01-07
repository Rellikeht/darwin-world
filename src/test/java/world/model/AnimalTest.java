package world.model;

import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void compareTo() {
        Animal animal1 = new Animal(
                new Vector2d(1,1),
                Direction.randomDirection(),
                10,
                new Genome(7)
        );

        Animal animal2 = new Animal(
                new Vector2d(1,1),
                Direction.randomDirection(),
                10,
                new Genome(7)
        );

        //PriorityQueue<Animal> queue = new PriorityQueue<>(10,new FittestComparator());
        PriorityQueue<Animal> queue = new PriorityQueue<>(10);
        queue.add(animal2);
        queue.add(animal1);

        System.out.println(animal1.getOrientation());
        System.out.println(animal2.getOrientation());
        System.out.println(queue);
        System.out.println(queue.peek());

        queue.remove(queue.peek());
        System.out.println(queue.peek());
    }
}
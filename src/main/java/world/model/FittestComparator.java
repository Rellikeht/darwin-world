package world.model;

import java.util.Comparator;
import java.util.Random;

public class FittestComparator implements Comparator<Animal> {
@Override
public int compare(Animal animal1, Animal animal2) {
        // Porównujemy po parametrze 1
        int result = Integer.compare(animal2.getEnergy(), animal1.getEnergy());
        if (result == 0) {
        result = Integer.compare(animal2.getDaysOfLife(),animal1.getDaysOfLife());
            if (result == 0){
                result = Integer.compare(animal2.getChildrenAmount(), animal1.getChildrenAmount());
                if (result==0){
                    Random random = new Random();
                    result = random.nextInt(2);
                }
            }
        }

        return result;
        }
}

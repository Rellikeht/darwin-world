package world.model;

import java.util.Comparator;
import java.util.Random;

public class FittestComparator implements Comparator<Animal> {
@Override
public int compare(Animal animal1, Animal animal2) {
        // Porównujemy po parametrze 1
        int result = Integer.compare(animal1.getEnergy(), animal2.getEnergy());
        if (result == 0) {
        result = Integer.compare(animal1.getDayOfDeath()- animal1.getDayOfBirth(),animal2.getDayOfDeath()- animal1.getDayOfBirth());
            if (result == 0){
                result = Integer.compare(animal1.getChildrenAmount(), animal2.getChildrenAmount());
                if (result==0){
                    Random random = new Random();
                    result = random.nextInt(2);
                }
            }
        }

        return result;
        }
}

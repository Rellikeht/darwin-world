package world.model;

import world.SimulationSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HellMap extends AbstractWorldMap {
    public HellMap(SimulationSettings settings) {
        super(settings);
    }

    public void moveAnimals() {
        for(Animal animal: allAnimals()) {
            animal.activateGene();
            Direction direction = animal.getDirection();
            Vector2d dirVector = direction.toUnitVector();
            Vector2d beforePosition = animal.getPosition();
            List<Animal> animalsAtBefore = animals.getOrDefault(beforePosition, new ArrayList<>());
            animals.remove(beforePosition);
            animalsAtBefore.remove(animal);
            int typeOfMove=typeOfMove(animal,dirVector);
            if (typeOfMove==1){
                Random random = new Random();
                int y = random.nextInt(upperRight.getY());
                int x = random.nextInt(upperRight.getX());
                animal.loseEnergy(settings.getPortalEnergy());
                animal.setPosition(new Vector2d(x,y));
            }
            else if(typeOfMove==2){
                animal.move();
                animal.loseEnergy(settings.getEnergyTakenByMovement());
            }
            Vector2d afterPosition=animal.getPosition();
            List<Animal> animalsAtAfter = animals.getOrDefault(afterPosition, new ArrayList<>());
            animalsAtAfter.add(animal);
            animals.put(beforePosition, animalsAtBefore);
            animals.put(afterPosition, animalsAtAfter);
        }

        super.moveAnimals();
    }

    private int typeOfMove(Animal animal, Vector2d vector){
        if (animal.getPosition().add(vector).getX()>upperRight.getX()||animal.getPosition().add(vector).getX()<lowerLeft.getX()){return 1;}
        if (animal.getPosition().add(vector).getY()>upperRight.getY() ||     animal.getPosition().add(vector).getY()<lowerLeft.getY()){return 3;}
        else{return 2;}

    }
}
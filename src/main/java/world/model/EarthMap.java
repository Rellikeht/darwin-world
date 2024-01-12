package world.model;

import java.util.ArrayList;
import java.util.List;

public class EarthMap extends AbstractWorldMap {
    public EarthMap(int width, int height, int initialGrassAmount, int jungleSize,int typeOfMutation,int minNumberOfMutation,int maxNumberOfMutation) {
        super(width, height, initialGrassAmount, jungleSize,typeOfMutation,minNumberOfMutation,maxNumberOfMutation);
    }
    public void moveAnimals(int energyTaken) {
        for(Animal animal: allAnimals()) {
            animal.rotateAnimals(animal.getCurrentGene()%(animal.getGenes().getLength()+1));
            Direction direction = animal.getDirection();
            Vector2d dirVector = direction.toUnitVector();
            Vector2d beforePosition = animal.getPosition();
            List<Animal> animalsAtBefore = animals.getOrDefault(beforePosition, new ArrayList<>());
            animals.remove(beforePosition);
            animalsAtBefore.remove(animal);
            int typeOfMove=typeOfMove(animal,dirVector);
            if (typeOfMove==1){
                animal.move();
                animal.loseEnergy(energyTaken);
                animal.setPosition(new Vector2d(1,animal.getPosition().getY()));
            }
            else if(typeOfMove==2){
                animal.move();
                animal.loseEnergy(energyTaken);
                animal.setPosition(new Vector2d(upperRight.getX(),animal.getPosition().getY()));
            }
            else if(typeOfMove==3){
                animal.move();
                animal.loseEnergy(energyTaken);
            }
            Vector2d afterPosition=animal.getPosition();
            List<Animal> animalsAtAfter = animals.getOrDefault(afterPosition, new ArrayList<>());
            animalsAtAfter.add(animal);
            animals.put(beforePosition, animalsAtBefore);
            animals.put(afterPosition, animalsAtAfter);
        }

        // TODO Coś z listenerem, w simulation mogłoby być lepiej
        // ale to później
        mapChanged("Animals moved");
    }
    protected int typeOfMove(Animal animal, Vector2d vector){
        if (animal.getPosition().add(vector).getX()>upperRight.getX()){return 1;}
        if (animal.getPosition().add(vector).getX()<lowerLeft.getX()){return 2;}
        if (animal.getPosition().add(vector).getY()>upperRight.getY() ||     animal.getPosition().add(vector).getY()<lowerLeft.getY()){return 4;}
        else{return 3;}

    }
}

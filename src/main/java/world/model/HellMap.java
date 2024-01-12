package world.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HellMap extends AbstractWorldMap {
    private final int energyPortal;
    public HellMap(int width, int height, int animal_amount, int grass_amount,int energyPortal,int typeOfMutation,int minNumberOfMutation,int maxNumberOfMutation) {
        super(width, height, animal_amount, grass_amount, typeOfMutation, minNumberOfMutation,maxNumberOfMutation);
        this.energyPortal=energyPortal;
    }

    public boolean canMoveTo(Vector2d position) {
        return false;
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
                Random random = new Random();
                int y = random.nextInt(upperRight.getY());
                int x = random.nextInt(upperRight.getX());
                animal.loseEnergy(energyPortal);
                animal.setPosition(new Vector2d(x,y));
            }
            else if(typeOfMove==2){
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
        if (animal.getPosition().add(vector).getX()>upperRight.getX()||animal.getPosition().add(vector).getX()<lowerLeft.getX()){return 1;}
        if (animal.getPosition().add(vector).getY()>upperRight.getY() ||     animal.getPosition().add(vector).getY()<lowerLeft.getY()){return 3;}
        else{return 2;}

    }
}
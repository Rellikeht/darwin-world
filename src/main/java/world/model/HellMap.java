package world.model;

import world.SimulationSettings;

public class HellMap extends AbstractWorldMap {
    public HellMap(SimulationSettings settings) { super(settings); }

    protected void specialAnimalMovement(Animal animal, Vector2d vector){
        if (animal.getPosition().add(vector).getX()>upperRight.getX() ||
            animal.getPosition().add(vector).getX()<lowerLeft.getX()) {
            int y = random.nextInt(upperRight.getY());
            int x = random.nextInt(upperRight.getX());
            animal.loseEnergy(settings.getPortalEnergy());
            animal.setPosition(new Vector2d(x,y));
        }
        else if (animal.getPosition().add(vector).getY() <= upperRight.getY() &&
                animal.getPosition().add(vector).getY() >= lowerLeft.getY()) {
            animal.move();
            animal.loseEnergy(settings.getEnergyTakenByMovement());
        }
    }
}
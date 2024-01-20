package world.model;

import world.SimulationSettings;

public class EarthMap extends AbstractWorldMap {
    public EarthMap(SimulationSettings settings) { super(settings); }

    protected void specialAnimalMovement(Animal animal, Vector2d vector){
        if (animal.getPosition().add(vector).getX()>upperRight.getX()) {
            animal.move();
            animal.loseEnergy(settings.get("EnergyTakenByMovement"));
            animal.setPosition(new Vector2d(0,animal.getPosition().getY()));
        }
        else if (animal.getPosition().add(vector).getX()<lowerLeft.getX()) {
            animal.move();
            animal.loseEnergy(settings.get("EnergyTakenByMovement"));
            animal.setPosition(new Vector2d(upperRight.getX(),animal.getPosition().getY()));
        }
        else if (animal.getPosition().add(vector).getY() <= upperRight.getY() &&
                animal.getPosition().add(vector).getY() >= lowerLeft.getY()) {
            animal.move();
            animal.loseEnergy(settings.get("EnergyTakenByMovement"));
        }
    }
}

package world.model;

public class HellMap extends AbstractWorldMap {

    public HellMap(int width, int height, int animal_amount, int grass_amount, int grassEnergy) {
        super(width, height, animal_amount, grass_amount, grassEnergy);
    }

    public boolean canMoveTo(Vector2d position) {
        return false;
    }

}
package world.model;

public class EarthMap extends AbstractWorldMap {
    public EarthMap(int width, int height, int animalAmount, int grassAmount, int grassEnergy) {
        super(width, height, animalAmount, grassAmount, grassEnergy);
    }

//    @Override
//    public boolean canMoveTo(Vector2d position) {
//        return position.follows(this.lowerLeft) &&
//                position.precedes(this.upperRight) &&
//                super.canMoveTo(position);
//    }
}

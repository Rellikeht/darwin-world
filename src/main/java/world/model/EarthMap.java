package world.model;

public class EarthMap extends AbstractWorldMap {
    public EarthMap(int width, int height, int animalAmount, int grassAmount) {
        super(width, height, animalAmount, grassAmount);
    }

//    @Override
//    public boolean canMoveTo(Vector2d position) {
//        return position.follows(this.lowerLeft) &&
//                position.precedes(this.upperRight) &&
//                super.canMoveTo(position);
//    }

}

package world.model;

public class EarthMap extends AbstractWorldMap {
    public EarthMap(int width, int height, int initialGrassAmount, int jungleSize) {
        super(width, height, initialGrassAmount, jungleSize);
    }

//    @Override
//    public boolean canMoveTo(Vector2d position) {
//        return position.follows(this.lowerLeft) &&
//                position.precedes(this.upperRight) &&
//                super.canMoveTo(position);
//    }

}

package world.model;

public class EarthMap extends AbstractWorldMap {
    public EarthMap(int width, int height, int animal_amount, int grass_amount) {
        super(width, height, animal_amount, grass_amount);
    }

//    @Override
//    public boolean canMoveTo(Vector2d position) {
//        return position.follows(this.lowerLeft) &&
//                position.precedes(this.upperRight) &&
//                super.canMoveTo(position);
//    }
}

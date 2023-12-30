package world.model;

public class EarthMap extends AbstractWorldMap {

    public EarthMap(int width, int height) {
        super(width, height);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(this.lowerLeft) &&
                position.precedes(this.upperRight) &&
                super.canMoveTo(position);
    }
}

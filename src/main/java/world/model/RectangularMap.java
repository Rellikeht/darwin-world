package world.model;

import world.model.util.MapVisualizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RectangularMap extends AbstractWorldMap {
    private final Vector2d upperRight, lowerLeft;
    private final Boundary boundary;

    public RectangularMap(int width, int height) {
        super(width+height);
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width-1, height-1);
        boundary = new Boundary(lowerLeft, upperRight);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft) &&
                position.precedes(upperRight) &&
                super.canMoveTo(position);
    }

    @Override
    public Boundary getCurrentBounds() {return boundary;}
}

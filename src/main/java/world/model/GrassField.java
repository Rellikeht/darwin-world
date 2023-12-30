package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrassField extends AbstractWorldMap {

    private final Map<Vector2d, Grass> grass;
    private Vector2d lowerLeft, upperRight;

    public GrassField(int n) {
        super(n);
        grass = new HashMap<>(n);

        int maxDim = (int) Math.sqrt(10*n);
        upperRight = new Vector2d(0, 0);
        lowerLeft = new Vector2d(maxDim, maxDim);

        RandomPositionGenerator gen = new RandomPositionGenerator(maxDim, maxDim, n);
        for (Vector2d grassPosition : gen) {
            grass.put(grassPosition, new Grass(grassPosition));
            upperRight = upperRight.upperRight(grassPosition);
            lowerLeft = lowerLeft.lowerLeft(grassPosition);
        }

    };

    @Override
    public void place(Animal animal) throws PositionAlreadyOccupiedException {
        super.place(animal);
        upperRight = upperRight.upperRight(animal.getPosition());
        lowerLeft = lowerLeft.lowerLeft(animal.getPosition());
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position) || grass.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement fromSuper = super.objectAt(position);
        if (fromSuper != null) return fromSuper;
        return grass.get(position);
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> lst = super.getElements();
        for (Grass g : grass.values()) lst.add(g);
        return lst;
    }

    @Override
    public Boundary getCurrentBounds() {
        for (WorldElement e : getElements()) {
            lowerLeft = lowerLeft.lowerLeft(e.getPosition());
            upperRight = upperRight.upperRight(e.getPosition());
        }
        return new Boundary(lowerLeft, upperRight);
    }

}
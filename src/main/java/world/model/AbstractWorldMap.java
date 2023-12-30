package world.model;

import world.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected final int mapInitialSize = 100;
    protected final Map<Vector2d, Animal> animals = new HashMap<>(mapInitialSize);
    protected final Map<Vector2d, Grass> grass = new HashMap<>(mapInitialSize);

    protected final Vector2d upperRight, lowerLeft;
    protected final Boundary boundary;

    //protected final Set<MapChangeListener> listeners;
    //protected final MapVisualizer visualizer = new MapVisualizer(this);

    protected static int curId = 0;
    protected final int id;

    //public AbstractWorldMap(int initialSize) {
    public AbstractWorldMap(int width, int height) {
        //listeners = new LinkedHashSet<>();

        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width-1, height-1);
        this.id = curId;
        curId += 1;

        boundary = new Boundary(lowerLeft, upperRight);
    }

    @Override
    public void place(Animal animal) throws PositionAlreadyOccupiedException {
        if (animals.containsKey(animal.getPosition())) {
            throw new PositionAlreadyOccupiedException(animal.getPosition());
        }
        animals.put(animal.getPosition(), animal);
        //mapChanged("Animal placed at %s".formatted(
        //        animal.getPosition().toString())
        //);
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        //mapChanged("Animal at %s is moving".formatted(
        //        animal.getPosition().toString())
        //);
        animals.remove(animal.getPosition());
        animal.move(direction, this);
        animals.put(animal.getPosition(), animal);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !animals.containsKey(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> lst = new ArrayList<>(animals.size());
        lst.addAll(animals.values());
        return lst;
    }

    //public String toString() {
    //    Boundary bounds = getCurrentBounds();
    //    return visualizer.draw(bounds.lowerLeft(), bounds.upperRight());
    //}

    //@Override
    //public void addListener(MapChangeListener listener) {listeners.add(listener);}
    //@Override
    //public void removeListener(MapChangeListener listener) {listeners.remove(listener);}
    //private void mapChanged(String message) {
    //    for (MapChangeListener listener : listeners) {
    //        listener.mapChanged(this, message);
    //    }
    //}

    public int getId() {
        return this.id;
    }

    @Override
    public Boundary getCurrentBounds() {return boundary;}

}
package world.model;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    protected final Map<Vector2d, List<Animal>> animals;
    protected final Map<Vector2d, Grass> grass;

    protected final Vector2d upperRight, lowerLeft;
    protected final Boundary boundary;

    //protected final Set<MapChangeListener> listeners;
    //protected final MapVisualizer visualizer = new MapVisualizer(this);

    protected static int curId = 0;
    protected final int id;

    //public AbstractWorldMap(int initialSize) {
    public AbstractWorldMap(int width, int height, int animal_amount, int grass_amount) {
        animals = new HashMap<>(animal_amount);
        grass = new HashMap<>(grass_amount);

        //listeners = new LinkedHashSet<>();

        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width-1, height-1);
        this.id = curId;
        curId += 1;
        boundary = new Boundary(lowerLeft, upperRight);
    }

    public void place(Animal animal)  {
        List<Animal> animalsAt = animals.getOrDefault(animal.getPosition(), new ArrayList<>());
        animalsAt.add(animal);
        animals.put(animal.getPosition(), animalsAt);
    }

    public void move(Animal animal, MoveDirection direction) {
        //mapChanged("Animal at %s is moving".formatted(
        //        animal.getPosition().toString())
        //);
        Vector2d firstPosition=animal.getPosition();
        List<Animal> animalsAtBefore=animals.get(firstPosition);
        animals.remove(firstPosition);
        animalsAtBefore.remove(animal);
        animal.move(direction, this);
        List<Animal> animalsAtAfter=animals.get(animal.getPosition());
        animalsAtAfter.add(animal);
        animals.put(animal.getPosition(), animalsAtBefore);
        animals.put(animal.getPosition(), animalsAtAfter);
    }

    // obiektów może być wiele
    //@Override
    //public WorldElement objectAt(Vector2d position) {
    //    return animals.get(position);
    //}

//    @Override
//    public boolean canMoveTo(Vector2d position) {
//        return !animals.containsKey(position);
//    }
//
//    @Override
//    public boolean isOccupied(Vector2d position) {
//        return animals.containsKey(position);
//    }
//
//    @Override
//    public List<WorldElement> getElements() {
//        List<WorldElement> lst = new ArrayList<>(animals.size());
//        lst.addAll(animals.values());
//        return lst;
//    }

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
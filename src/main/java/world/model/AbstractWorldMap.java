package world.model;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    protected final Map<Vector2d, List<Animal>> animals;
    protected final Map<Vector2d, Grass> grass;
    protected int grassEnergy;

    protected final Vector2d upperRight, lowerLeft;
//    protected final Boundary boundary;

    //protected final Set<MapChangeListener> listeners;
    //protected final MapVisualizer visualizer = new MapVisualizer(this);

    protected static int curId = 0;
    protected final int id;

    //public AbstractWorldMap(int initialSize) {
    public AbstractWorldMap(int width, int height, int animal_amount, int grass_amount, int grassEnergy) {
        animals = new HashMap<>(animal_amount);
        grass = new HashMap<>(grass_amount);
        grassEnergy = grassEnergy;
        //listeners = new LinkedHashSet<>();

        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width-1, height-1);
        this.id = curId;
        curId += 1;
//        boundary = new Boundary(lowerLeft, upperRight);
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
        Vector2d beforePosition=animal.getPosition();
        List<Animal> animalsAtBefore=animals.getOrDefault(beforePosition, new ArrayList<>());
        animals.remove(beforePosition);
        animalsAtBefore.remove(animal);
        //animal.move(direction, this);
        Vector2d afterPosition=animal.getPosition();
        if(!beforePosition.equals(afterPosition) && grass.containsKey(afterPosition)){
            animal.setEnergy(animal.getEnergy()+grassEnergy);
        }
        List<Animal> animalsAtAfter=animals.getOrDefault(afterPosition, new ArrayList<>());
        animalsAtAfter.add(animal);
        animals.put(beforePosition, animalsAtBefore);
        animals.put(afterPosition, animalsAtAfter);
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

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

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

    public Vector2d getUpperRight() {
        return upperRight;
    }

    public int amountAt(Vector2d position) {
        return 0;
    }

//    @Override
//    public Boundary getCurrentBounds() {return boundary;}

}
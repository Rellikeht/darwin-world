package world.model;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {

    // Elementy
    protected final Map<Vector2d, List<Animal>> animals;
    protected final Map<Vector2d, Grass> grass;
    protected final Vector2d upperRight, lowerLeft;
//    protected final Boundary boundary;
    protected final Set<MapChangeListener> listeners;
    //protected final MapVisualizer visualizer = new MapVisualizer(this);

    protected static int curId = 0;
    protected final int id;

    // Staty
    private int animalAmount = 0; // To będą w praktyce żywe zwierzęta
    private int freeSquares;
    public int getAnimalAmount() { return animalAmount; }
    public int getFreeSquares() { return freeSquares; }
    public int getGrassAmount() { return grass.size(); }

    public int getId() { return this.id; }
    public Vector2d getUpperRight() { return upperRight; }

    //public AbstractWorldMap(int initialSize) {
    public AbstractWorldMap(int width, int height, int animal_amount, int grass_amount) {
        animals = new HashMap<>(animal_amount);
        grass = new HashMap<>(grass_amount);
        listeners = new LinkedHashSet<>();

        // TODO staty

        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width-1, height-1);
        this.id = curId;
        curId += 1;
//        boundary = new Boundary(lowerLeft, upperRight);

        // TODO I tutaj trudna rzecz: stawianie trawy, to losowanie z tym równikiem
        // Nie wiem na razie jak to zrobić, może jutro pomyślę
        // To może wymagać osobnego random position generatora

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
//        animal.move(direction, this);
        Vector2d afterPosition=animal.getPosition();

        // Może to czepialstwo, ale my na pewno chcemy robić to tutaj?
        // Tak swoją drogą to teraz nie wiem, gdzie powinno być to grass energy
        // Tutaj trzeba jakiegoś mechanizmu odwołującego się do symulacji zawierającej to wszystko
//        if(!beforePosition.equals(afterPosition) && grass.containsKey(afterPosition)){
//            animal.setEnergy(animal.getEnergy()+grassEnergy);
//        }

        List<Animal> animalsAtAfter=animals.getOrDefault(afterPosition, new ArrayList<>());
        animalsAtAfter.add(animal);
        animals.put(beforePosition, animalsAtBefore);
        animals.put(afterPosition, animalsAtAfter);
    }

    // obiektów może być wiele
    // Nie wiem, jak to zrobić
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

    @Override
    public void addListener(MapChangeListener listener) {listeners.add(listener);}
    @Override
    public void removeListener(MapChangeListener listener) {listeners.remove(listener);}
    private void mapChanged(String message) {
        for (MapChangeListener listener : listeners) {
            listener.mapChanged(this, message);
        }
    }

    public int amountAt(Vector2d position) {
        return 0;
    }

//    @Override
//    public Boundary getCurrentBounds() {return boundary;}

}
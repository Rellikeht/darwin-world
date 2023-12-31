package world.model;

import world.util.MapVisualizer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractWorldMap implements WorldMap {

    // Elementy
    protected final Map<Vector2d, List<Animal>> animals;
    protected final Map<Vector2d, Grass> grass;
    protected final Vector2d upperRight, lowerLeft;
//    protected final Boundary boundary;
    protected final Set<MapChangeListener> listeners;
    protected final MapVisualizer visualizer = new MapVisualizer(this);
    protected final int jungleSize;

    // Numeracja
    protected static int curId = 0;
    protected final int id;

    public int getId() { return this.id; }
    public Vector2d getUpperRight() { return upperRight; }

    public AbstractWorldMap(int width, int height, int initialGrassAmount, int jungleSize) {
        // Po co nam więcej argumentów
        // Początkowa wielkość i tak ma małe znaczenie
        animals = new HashMap<>(initialGrassAmount);

        grass = new HashMap<>(initialGrassAmount);
        listeners = new LinkedHashSet<>();

        // TODO staty

        this.jungleSize = jungleSize;
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

    public void move(Animal animal, Direction direction) {
        // TODO praktycznie cały ruch do zrobienia

        mapChanged("Animal at %s is moving".formatted(
                animal.getPosition().toString())
        );
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

//    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    public List<Animal> getAnimals() {
        // Przyjemne, trzeba przyznać
        return animals.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<WorldElement> getElements() {
//        List<WorldElement> lst = new ArrayList<>(animals.size());
//        lst.addAll(animals.values());
//        return lst;
//    }

    public String toString() {
        return visualizer.draw(lowerLeft, upperRight);
    }

    @Override
    public void addListener(MapChangeListener listener) { listeners.add(listener); }
    @Override
    public void removeListener(MapChangeListener listener) { listeners.remove(listener); }
    private void mapChanged(String message) {
        for (MapChangeListener listener : listeners) {
            listener.mapChanged(this, message);
        }
    }

    public String getAt(Vector2d position) {
        List<Animal> animalsAt = animals.get(position);
        if (animalsAt != null) {
            if (animalsAt.size() == 1) {
                return animalsAt.get(0).toString();
            } else if (animalsAt.size() > 1 && animalsAt.size() < 10) {
                return Integer.toString(animalsAt.size());
            } else if (animalsAt.size() >= 10) {
                return "#";
            }
        }

        Grass grassAt = grass.get(position);
        if (grassAt != null) { return grassAt.toString(); }
        return "";
    }

    public List<Animal> animalsAt(Vector2d position) {
        return animals.get(position);
    }

//    @Override
//    public Boundary getCurrentBounds() {return boundary;}

}
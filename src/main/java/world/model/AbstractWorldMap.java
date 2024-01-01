package world.model;

import world.util.MapVisualizer;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements WorldMap {

    // Elementy
    protected final Map<Vector2d, List<Animal>> animals;
    protected final Map<Vector2d, Grass> grass;
    protected final Vector2d upperRight, lowerLeft;
    protected final Set<MapChangeListener> listeners;
    protected final MapVisualizer visualizer = new MapVisualizer(this);
    protected final int jungleSize;
//    private final int equatorSize = 1;

    // Numeracja
    protected static int curId = 0;
    protected final int id;

    @Override
    public int getId() { return this.id; }
    @Override
    public Vector2d getUpperRight() { return upperRight; }

    public AbstractWorldMap(int width, int height, int initialGrassAmount, int jungleSize) {
        // Po co nam więcej argumentów
        // Początkowa wielkość i tak ma małe znaczenie
        animals = new HashMap<>(initialGrassAmount);
        grass = new HashMap<>(initialGrassAmount);
        listeners = new LinkedHashSet<>();
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width-1, height-1);
        int jungleStart = (upperRight.getY() - lowerLeft.getY() - jungleSize) / 2;
        int jungleEnd = jungleStart + jungleSize;

        this.jungleSize = jungleSize;
        this.id = curId;
        curId += 1;

        // Początkowa Trawa
        Random random = new Random();

        // I to wszystko trzeba przerobić używając tych 3 generatorów
        for(int i=0;i<initialGrassAmount;i++) {
            int grassProbability = random.nextInt(10);
            if (grassProbability <= 1) {
                if(grassProbability==0) {
                    RandomPositionGenerator overEquator = new RandomPositionGenerator(
                            new Vector2d(upperRight.getX(), upperRight.getY() - jungleEnd),
                            upperRight
                    );
                    for (Vector2d grassPosition : overEquator) {
                        grass.put(grassPosition, new Grass(grassPosition));
                    }
                }
                else{
                    RandomPositionGenerator underEquator = new RandomPositionGenerator(
                            lowerLeft,
                            new Vector2d(lowerLeft.getX(), lowerLeft.getY() + jungleStart)
                    );
                    for (Vector2d grassPosition : underEquator) {
                        grass.put(grassPosition, new Grass(grassPosition));
                    }
                }

            } else {
                RandomPositionGenerator equator = new RandomPositionGenerator(
                        new Vector2d(lowerLeft.getX(), jungleStart),
                        new Vector2d(jungleEnd, upperRight.getX())
                );
                for (Vector2d grassPosition : equator) {
                    grass.put(grassPosition, new Grass(grassPosition));
                }
            }
        }
    }

    @Override
    public void place(Animal animal)  {
        List<Animal> animalsAt = animals.getOrDefault(animal.getPosition(), new ArrayList<>());
        animalsAt.add(animal);
        animals.put(animal.getPosition(), animalsAt);
    }

    @Override
    public void move(Animal animal) {
        // TODO praktycznie cały ruch do zrobienia

        // wiadomość do zmiany, aczkolwiek to tylko debugowanie (na razie)
        mapChanged("Animal at %s is moving".formatted(
                animal.getPosition().toString())
        );

        Vector2d beforePosition=animal.getPosition();
        List<Animal> animalsAtBefore=animals.getOrDefault(beforePosition, new ArrayList<>());
        animals.remove(beforePosition);
        animalsAtBefore.remove(animal);
//        animal.move(direction, this);
        Vector2d afterPosition=animal.getPosition();

        // Tutaj by można rozwiązać konflikt, które zwierze zjada,
        // a potem zapdejtować mu energię i podobnie z rozmnażaniem
        // I też można by to wywalić do osobnej funkcji
        // Albo jedzenie do osobnej funkcji i każda faza ruchu osobno w symulacji

        // IMHO to może w symulacji na koniec sprawdzać jakie zwięrzęta stoją na trawie i tam rozwiązywać problem kto zjada
//        if(!beforePosition.equals(afterPosition) && grass.containsKey(afterPosition)){
//            animal.setEnergy(animal.getEnergy()+grassEnergy);
//        }

        List<Animal> animalsAtAfter=animals.getOrDefault(afterPosition, new ArrayList<>());
        animalsAtAfter.add(animal);
        animals.put(beforePosition, animalsAtBefore);
        animals.put(afterPosition, animalsAtAfter);
    }

//    public boolean isOccupied(Vector2d position) {
//        return animals.containsKey(position);
//    }

    @Override
    public List<Animal> getAnimals() {
        // Przyjemne, trzeba przyznać
        return animals.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
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

    @Override
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

    @Override
    public List<Animal> animalsAt(Vector2d position) {
        return animals.get(position);
    }

    @Override
    public Set<Vector2d> grassPositions() { return grass.keySet(); }
}
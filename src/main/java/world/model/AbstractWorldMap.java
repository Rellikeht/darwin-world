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

    protected static int curId = 0;
    protected final int id;

    // Staty
    private int equatorSize=1;
    private int animalAmount = 0; // To będą w praktyce żywe zwierzęta
    private int freeSquares;
    public int getAnimalAmount() { return animalAmount; }
    public int getFreeSquares() { return freeSquares; }
    public int getGrassAmount() { return grass.size(); }

    public int getId() { return this.id; }
    public Vector2d getUpperRight() { return upperRight; }

    // To jest raczej źle
    // animal amount nic nie robi
    // Nie wiem, jak to zrobić w sumie
    // Chociaż można też zostawić i generować na sztywno
    // ze stałych w simulation
    public AbstractWorldMap(int width, int height, int initialAnimalAmount, int initialGrassAmount) {
        animals = new HashMap<>(initialAnimalAmount);
        grass = new HashMap<>(initialGrassAmount);
        listeners = new LinkedHashSet<>();
        int equatorBottom=(height/2)-equatorSize;
        Vector2d equatorTopVector=new Vector2d(0,(height/2)+equatorSize);
        Vector2d equatorBottomVector=new Vector2d(0,(height/2)-equatorSize+1);
        // TODO staty

        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width-1, height-1);
        this.id = curId;
        curId += 1;
//        boundary = new Boundary(lowerLeft, upperRight);

        // Prowizoryczna Trawa
        Random random = new Random();
        for(int i=0;i<initialGrassAmount;i++) {
            int grassProbability = random.nextInt(10);
            if (grassProbability <= 1) {
                int equatorSide=random.nextInt(2);
                RandomPositionGenerator gen = new RandomPositionGenerator(width, equatorBottom, 1);
                if(equatorSide==0) {
                    for (Vector2d grassPosition : gen) {
                        grass.put(grassPosition, new Grass(grassPosition));
                    }
                }
                else{
                    for (Vector2d grassPosition : gen) {
                        grassPosition.add(equatorTopVector);
                        grass.put(grassPosition, new Grass(grassPosition));
                    }
                }
            } else {
                RandomPositionGenerator gen = new RandomPositionGenerator(width, equatorSize+1, 1);
                for (Vector2d grassPosition : gen) {
                    grassPosition=grassPosition.add(equatorBottomVector);
                    grass.put(grassPosition, new Grass(grassPosition));
                }
            }
        }
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

//    @Override
//    public Boundary getCurrentBounds() {return boundary;}

}
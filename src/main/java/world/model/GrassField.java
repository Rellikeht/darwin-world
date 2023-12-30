package world.model;

// Ta klasa jest tu tylko po to, żeby mieć kod blisko
public class GrassField extends AbstractWorldMap {
    public GrassField(int width, int height, int animal_amount, int grass_amount, int grassEnergy) {
        super(width, height, animal_amount, grass_amount, grassEnergy);
    }


    //public GrassField(int n) {
    //    super(n);
    //    grass = new HashMap<>(n);

    //    int maxDim = (int) Math.sqrt(10*n);
    //    upperRight = new Vector2d(0, 0);
    //    lowerLeft = new Vector2d(maxDim, maxDim);

    //    RandomPositionGenerator gen = new RandomPositionGenerator(maxDim, maxDim, n);
    //    for (Vector2d grassPosition : gen) {
    //        grass.put(grassPosition, new Grass(grassPosition));
    //        upperRight = upperRight.upperRight(grassPosition);
    //        lowerLeft = lowerLeft.lowerLeft(grassPosition);
    //    }

    //};

    //@Override
    //public void place(Animal animal) throws PositionAlreadyOccupiedException {
    //    super.place(animal);
    //    upperRight = upperRight.upperRight(animal.getPosition());
    //    lowerLeft = lowerLeft.lowerLeft(animal.getPosition());
    //}

    //@Override
    //public boolean isOccupied(Vector2d position) {
    //    return super.isOccupied(position) || grass.containsKey(position);
    //}

    //@Override
    //public WorldElement objectAt(Vector2d position) {
    //    WorldElement fromSuper = super.objectAt(position);
    //    if (fromSuper != null) return fromSuper;
    //    return grass.get(position);
    //}

    //@Override
    //public List<WorldElement> getElements() {
    //    List<WorldElement> lst = super.getElements();
    //    for (Grass g : grass.values()) lst.add(g);
    //    return lst;
    //}

    //@Override
    //public Boundary getCurrentBounds() {
    //    for (WorldElement e : getElements()) {
    //        lowerLeft = lowerLeft.lowerLeft(e.getPosition());
    //        upperRight = upperRight.upperRight(e.getPosition());
    //    }
    //    return new Boundary(lowerLeft, upperRight);
    //}

}
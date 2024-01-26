package world.model;

import java.util.*;

public class RandomPositionGenerator implements Iterator<Vector2d> {
    //private int amount = 0;
    private final List<Vector2d> positions;
    private final Set<Vector2d> without; // bez czego?
    private final Random random = new Random();

    private int maxAmount(Vector2d lowerLeft, Vector2d upperRight) {
        return (upperRight.getX() - lowerLeft.getX() + 1) *
                (upperRight.getY() - lowerLeft.getY() + 1);
    }

    public RandomPositionGenerator(Vector2d lowerLeft, Vector2d upperRight, Set<Vector2d> without) {
        int maxSize = maxAmount(lowerLeft, upperRight);
        positions = new ArrayList<>(maxSize);
        this.without = without;
        //this.amount = maxSize - without.size();

        // To wszystko będzie wolne, nie mam pojęcia jak to wszystko dobrze zrobić
        // Co pomysł to gorsze błędy :(
        for (int i = lowerLeft.getX(); i <= upperRight.getX(); i++) {
            for (int j = lowerLeft.getY(); j <= upperRight.getY(); j++) {
                Vector2d vec = new Vector2d(i, j);
                positions.add(vec);
            }
        }
    }

    public RandomPositionGenerator(Vector2d lowerLeft, Vector2d upperRight) {
        this(lowerLeft, upperRight, new HashSet<>(0));
    }

    @Override
    public boolean hasNext() {
        return positions.size() > without.size();
    }
    public boolean hasNext(int N) {
        return positions.size() - without.size() >= N;
    }

    @Override
    public Vector2d next() {
        int i = random.nextInt(positions.size());
        Vector2d val = positions.get(i);
        while (without.contains(val)) {
            i = (i+1)%positions.size();
            val = positions.get(i);
        }
        without.add(val);
        return val;
    }

    public void free(Vector2d position) { without.remove(position); }
    //public void free(Collection<Vector2d> positions) { without.removeAll(positions); }
}

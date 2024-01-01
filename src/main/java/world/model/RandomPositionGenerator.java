package world.model;

import java.util.*;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final int amount;
//    private final int[] xs, ys;
    private final List<Vector2d> positions;

    private int randN(int upperLimit) { return (int) (Math.random() * upperLimit); }

    private int maxAmount(Vector2d lowerLeft, Vector2d upperRight) {
        return (upperRight.getX() - lowerLeft.getX() + 1) *
                (upperRight.getY() - lowerLeft.getY() + 1);
    }

    public RandomPositionGenerator(Vector2d lowerLeft, Vector2d upperRight, Set<Vector2d> without) {
        amount = maxAmount(lowerLeft, upperRight);
        positions = new ArrayList<Vector2d>(amount);
        int c = 0;

        // To będzie wolne, zwłaszcza dla zbioru pustego, ale najważniejsze, że jest proste i
        // To to sie w ogóle wypierdala na "Index 0 out of bounds for length 0"
        for (int i = lowerLeft.getX(); i < upperRight.getX(); i++) {
            for (int j = lowerLeft.getY(); j < upperRight.getY(); j++) {
                Vector2d vec = new Vector2d(i, j);
                if (!without.contains(vec)) {
                    positions.set(c, vec);
                    c++;
                }
            }
        }

        Collections.shuffle(positions);
    }

    public RandomPositionGenerator(Vector2d lowerLeft, Vector2d upperRight) {
        this(lowerLeft, upperRight, new HashSet<>(0));
    }

//    public RandomPositionGenerator(int maxWidth, int maxHeight, int amount) {
//        this.amount = amount;
//        xs = new int[amount];
//        ys = new int[amount];
//
//        for (int i = 0; i < amount; i++) xs[i] = randN(maxWidth);
//        Arrays.sort(xs);
//
//        int i = 0;
//        while (i < amount) {
//            int count = 1;
//            int el = xs[i];
//            i++;
//
//            while (i < amount && xs[i] == el) {
//                i++;
//                count++;
//            }
//
//            if (count == 1) ys[i-1] = randN(maxHeight);
//            else {
//                int[] tmp = new int[count];
//                for (int j = 0; j < count; j++) tmp[j] = randN(maxHeight - count + 1);
//                Arrays.sort(tmp);
//
//                for (int j = 1; j < count; j++) {
//                    if (tmp[j-1] <= tmp[j]) tmp[j] += tmp[j-1]+1;
//                }
//
//                for (int j = 0; j < count; j++) {
//                    tmp[j] -= Math.max(0, tmp[count-1]-maxHeight);
//                }
//
//                for (int j = 0; j < count; j++) ys[i-count+j] = tmp[j];
//            }
//        }
//    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<Vector2d>() {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i < amount;
            }

            @Override
            public Vector2d next() {
                //Vector2d val = new Vector2d(xs[i], ys[i]);
                Vector2d val = positions.get(i);
                i++;
                return val;
            }
        };
    }
}

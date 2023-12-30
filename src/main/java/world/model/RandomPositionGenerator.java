package agh.ics.oop.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final int amount;
    private final int[] xs, ys;

    private int randN(int upperLimit) { return (int) (Math.random() * upperLimit); }

    public RandomPositionGenerator(int maxWidth, int maxHeight, int amount) {
        this.amount = amount;
        xs = new int[amount];
        ys = new int[amount];

        for (int i = 0; i < amount; i++) xs[i] = randN(maxWidth);
        Arrays.sort(xs);

        int i = 0;
        while (i < amount) {
            int count = 1;
            int el = xs[i];
            i++;

            while (i < amount && xs[i] == el) {
                i++;
                count++;
            }

            if (count == 1) ys[i-1] = randN(maxHeight);
            else {
                int[] tmp = new int[count];
                for (int j = 0; j < count; j++) tmp[j] = randN(maxHeight - count + 1);
                Arrays.sort(tmp);

                for (int j = 1; j < count; j++) {
                    if (tmp[j-1] <= tmp[j]) tmp[j] += tmp[j-1]+1;
                }

                for (int j = 0; j < count; j++) {
                    tmp[j] -= Math.max(0, tmp[count-1]-maxHeight);
                }

                for (int j = 0; j < count; j++) ys[i-count+j] = tmp[j];
            }
        }
    }

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
                Vector2d val = new Vector2d(xs[i], ys[i]);
                i++;
                return val;
            }
        };
    }
}

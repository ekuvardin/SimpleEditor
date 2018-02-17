package canvas;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class ConcurrentCanvas extends Canvas {

    private AtomicIntegerArray array;
    private final int xLength;
    private final int yLength;

    public ConcurrentCanvas(int x, int y) {
        array = new AtomicIntegerArray(x * y);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                array.set(i * x + j, ' ');
            }
        }

        xLength = x;
        yLength = y;
    }

    @Override
    public void set(int x, int y, char value) {
        array.set((x - 1) * y + y, value);
    }

    @Override
    public char get(int x, int y) {
        return (char) array.get((x - 1) * y + y);
    }

    public int getxLength() {
        return xLength;
    }

    public int getyLength() {
        return yLength;
    }
}

package canvas;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Canvas to store point which can be accessed parallel
 */
public class ConcurrentCanvas extends Canvas {

    private AtomicIntegerArray array;
    private final int xLength;
    private final int yLength;

    public ConcurrentCanvas(int x, int y) {
        array = new AtomicIntegerArray(x * y);
        for (int i = 0; i < x * y; i++) {
            array.set(i, ' ');
        }

        xLength = x;
        yLength = y;
    }

    @Override
    public void set(int x, int y, char value) {
        array.set(xLength * (y - 1) + (x - 1), value);
    }

    @Override
    public char get(int x, int y) {
        return (char) array.get(xLength * (y - 1) + (x - 1));
    }

    public int getxLength() {
        return xLength;
    }

    public int getyLength() {
        return yLength;
    }

    @Override
    public boolean canBeParallelUsed() {
        return true;
    }
}

package canvas;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Model to store point which can be accessed parallel
 */
public class ConcurrentModel extends Model {

    private AtomicIntegerArray array;
    private final int xLength;
    private final int yLength;

    public ConcurrentModel(int x, int y) {
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

    public int getWidth() {
        return xLength;
    }

    public int getHeight() {
        return yLength;
    }

    @Override
    public boolean canBeParallelUsed() {
        return true;
    }
}

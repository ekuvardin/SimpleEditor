package canvas;

import java.util.Arrays;

/**
 * Simple canvas to store point
 */
public class SimpleModel extends Model {

    private char[][] array;
    private final int xLength;
    private final int yLength;
    private Boundary boundary;

    public SimpleModel(int x, int y) {
        this.array = new char[y][x];
        for (int i = 0; i < y; i++) {
            Arrays.fill(array[i], ' ');
        }

        xLength = x;
        yLength = y;
        boundary = new Boundary(1, xLength, 1, yLength);
    }

    @Override
    public void set(int x, int y, char value) {
        array[y - 1][x - 1] = value;
    }

    @Override
    public char get(int x, int y) {
        return array[y - 1][x - 1];
    }

    @Override
    public int getWidth() {
        return xLength;
    }

    @Override
    public int getHeight() {
        return yLength;
    }

    @Override
    public Boundary getBoundary() {
        return boundary;
    }

}

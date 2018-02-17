package canvas;

import java.util.Arrays;

public class SimpleCanvas extends Canvas {

    private char[][] array;
    private final int xLength;
    private final int yLength;

    public SimpleCanvas(int x, int y) {
        this.array = new char[x][y];
        for (int i = 0; i < x; i++) {
            Arrays.fill(array[i], ' ');
        }

        xLength = x;
        yLength = y;
    }

    @Override
    public void set(int x, int y, char value) {
        array[x - 1][y - 1] = value;
    }

    @Override
    public char get(int x, int y) {
        return array[x - 1][y - 1];
    }

    @Override
    public int getxLength() {
        return xLength;
    }

    @Override
    public int getyLength() {
        return yLength;
    }
}

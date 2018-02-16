package canvas;

import java.util.Arrays;

public class Canvas {

    private char[][] array;
    private final int xLength;
    private final int yLength;
    private static Canvas currentCanvas;

    public static Canvas getCurrentCanvas(){
        return currentCanvas;
    }

    public Canvas(int x, int y){
        this.array = new char[x][y];
        for(int i = 0; i< x; i++){
            Arrays.fill(array[i], ' ');
        }

        xLength = x;
        yLength = y;
        currentCanvas = this;
    }

    public char[][] getArray() {
        return array;
    }

    public int getxLength() {
        return xLength;
    }

    public int getyLength() {
        return yLength;
    }
}

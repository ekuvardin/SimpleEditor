package canvas.Viewer;

import canvas.Canvas;
import canvas.SimpleCanvas;

import java.util.Arrays;

public class ConsoleViewer implements IView {

    @Override
    public void draw(Canvas canvas) {
        int xLength = canvas.getxLength();
        int yLength = canvas.getyLength();
        for (int i = 0; i < xLength + 2; i++) {
            System.out.print("-");
        }

        System.out.println();

        for (int j = 1; j <= yLength; j++) {
            for (int i = 0; i <= xLength + 1; i++) {
                if (i == 0 || i == xLength +1) {
                    System.out.print("|");
                } else {
                    System.out.print(canvas.get(i, j));
                }
            }
            System.out.println();
        }

        for (int i = 0; i < xLength + 2; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    @Override
    public void showError(Exception e) {
        System.err.println("ERROR: " + Arrays.toString(e.getStackTrace()));
        System.err.println();
    }

    @Override
    public void showError(String message) {
        System.err.println("ERROR: " + message);
        System.err.println();
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }
}

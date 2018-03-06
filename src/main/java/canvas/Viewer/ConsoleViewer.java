package canvas.Viewer;

import canvas.Model;

import java.util.Arrays;

public class ConsoleViewer implements IView {

    @Override
    public void draw(Model model) {
        int xLength = model.getWidth();
        int yLength = model.getHeight();
        for (int i = 0; i < xLength + 2; i++) {
            System.out.print("-");
        }

        System.out.println();

        for (int j = 1; j <= yLength; j++) {
            for (int i = 0; i <= xLength + 1; i++) {
                if (i == 0 || i == xLength +1) {
                    System.out.print("|");
                } else {
                    System.out.print(model.get(i, j));
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

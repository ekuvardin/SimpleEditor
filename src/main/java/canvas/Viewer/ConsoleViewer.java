package canvas.Viewer;

import canvas.Canvas;

public class ConsoleViewer implements IView {

    @Override
    public void draw(Canvas canvas) {
        char[][] array = canvas.getArray();
        int xLength = canvas.getxLength();
        int yLength = canvas.getyLength();
        for(int i = 0; i<array.length + 2; i++){
            System.out.print("-");
        }

        System.out.println();

        for(int j = 0; j< yLength; j++ ){
            for(int i = -1; i<array.length + 1; i++){
                if(i == - 1 || i == xLength){
                    System.out.print("|");
                } else {
                    System.out.print(array[i][j]);
                }
            }
            System.out.println();
        }

        for(int i = 0; i<array.length + 2; i++){
            System.out.print("-");
        }
        System.out.println();
    }
}

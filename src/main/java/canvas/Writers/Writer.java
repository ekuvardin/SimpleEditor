package canvas.Writers;

import canvas.Canvas;

public class Writer {

    public static boolean writeRectangle(Canvas canvas, int x1, int y1, int x2, int y2, char colour) {
        char[][] array = canvas.getArray();
        int xLength = canvas.getxLength();
        int yLength = canvas.getyLength();

        if (x1 > xLength || x2 > xLength || y1 > yLength || y2 > yLength) {
            System.out.println(String.format("Couldn't create rectangle inside current canvas width: %d, height: %d", xLength, yLength));
            return false;
        }

        return writeLine(canvas, x1, y1, x1, y2, colour) && writeLine(canvas, x1, y2, x2, y2, colour) && writeLine(canvas, x2, y2, x2, y1, colour) && writeLine(canvas, x2, y1, x1, y1, colour);
    }

    public static boolean writeLine(Canvas canvas, int x1, int y1, int x2, int y2, char colour) {
        char[][] array = canvas.getArray();
        int xLength = canvas.getxLength();
        int yLength = canvas.getyLength();

        if (x1 > xLength || x2 > xLength || y1 > yLength || y2 > yLength) {
            System.out.println(String.format("Couldn't create line inside current canvas width: %d, height: %d", xLength, yLength));
            return false;
        }

        // Simple optimization
        if (x1 == x2 || y1 == y2) {
            for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++)
                for (int j = Math.min(y1, y2); j <= Math.max(y1, y2); j++)
                    array[i - 1][j - 1] = colour;
        } else {
            // Find line equation of the line
            // (y - y1)/(y2-y1) = (x - x1)/(x2 - x1)
            // y - y1 = (x - x1)(y2-y1)/(x2 - x1)
            // y = y1 + (x - x1)(y2-y1)/(x2 - x1)

            // try to draw line from left to write
            // To do this we should find min x coordinate
            int startx, starty, endx, endy;
            if (x1 < x2) {
                startx = x1;
                starty = y1;
                endx = x2;
                endy = y2;
            } else {
                startx = x2;
                starty = y2;
                endx = x1;
                endy = y1;
            }
            array[startx - 1][starty - 1] = colour;
            array[endx - 1][endy - 1] = colour;

            if (y1 < y2) {
                int newX = startx;
                int newY = starty;
                while (newX < endx && newY <= endy) {
                    double newYcompare = findx(newX + 1, startx, starty, endx, endy);

                    if (newYcompare == newY + 1) {
                        newY++;
                        newX++;
                        array[newX - 1][newY - 1] = colour;
                        array[newX - 1][newY - 2] = colour;
                    } else if (newYcompare < newY + 1) {
                        newX++;
                        array[newX - 1][newY - 1] = colour;
                    } else {
                        newY++;
                        array[newX - 1][newY - 1] = colour;
                    }
                }
            }
        }

        return true;
    }

    private static double findx(int x, int x1, int y1, int x2, int y2) {
        return y1 + (x - x1) * (y2 - y1) / (x2 - x1);
    }

    private static int findxAccurately(int x, int x1, int y1, int x2, int y2) {
        return y1 + (x - x1) * (y2 - y1) / (x2 - x1);
    }
}

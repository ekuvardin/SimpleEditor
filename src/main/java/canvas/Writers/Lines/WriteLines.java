package canvas.Writers.Lines;

import canvas.Canvas;

public class WriteLines {

    public static boolean writeRectangle(Canvas canvas, int x1, int y1, int x2, int y2, char colour) {
        int xLength = canvas.getxLength();
        int yLength = canvas.getyLength();

        if (x1 > xLength || x2 > xLength || y1 > yLength || y2 > yLength) {
            System.out.println(String.format("Couldn't create rectangle inside current simpleCanvas width: %d, height: %d", xLength, yLength));
            return false;
        }

        return writeLine(canvas, x1, y1, x1, y2, colour) && writeLine(canvas, x1, y2, x2, y2, colour) && writeLine(canvas, x2, y2, x2, y1, colour) && writeLine(canvas, x2, y1, x1, y1, colour);
    }

    public static boolean writeLine(Canvas canvas, int x1, int y1, int x2, int y2, char colour) {
        int xLength = canvas.getxLength();
        int yLength = canvas.getyLength();

        if (x1 > xLength || x2 > xLength || y1 > yLength || y2 > yLength) {
            System.out.println(String.format("Couldn't create line inside current simpleCanvas width: %d, height: %d", xLength, yLength));
            return false;
        }

        // Simple optimization if the line are orthogonal x or y.
        if (x1 == x2 || y1 == y2) {
            for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++)
                for (int j = Math.min(y1, y2); j <= Math.max(y1, y2); j++)
                    canvas.set(i, j, colour);
        } else {
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

            canvas.set(startx, starty, colour);
            canvas.set(endx, endy, colour);

            if (y1 < y2) {
                // Find line equation of the line
                // (y - y1)/(y2-y1) = (x - x1)/(x2 - x1)
                // y - y1 = (x - x1)(y2-y1)/(x2 - x1)
                // y = y1 + (x - x1)(y2-y1)/(x2 - x1)
                int newX = startx;
                int newY = starty;
                while (newX < endx && newY <= endy) {
                    //TODO optimize coefficient
                    double newYcompare = findY(newX + 1, startx, starty, endx, endy);

                    if (newYcompare == newY + 1) {
                        newY++;
                        newX++;
                        canvas.set(newX, newY, colour);
                        canvas.set(newX, newY - 1, colour);
                    } else if (newYcompare < newY + 1) {
                        newX++;
                        canvas.set(newX, newY, colour);
                    } else {
                        newY++;
                        canvas.set(newX, newY, colour);
                    }
                }
            } else {
                // Find line equation of the line
                // (x - x1)/(x2 - x1) = (y - y1)/(y2-y1)
                // (x - x1) = (y - y1)(x2 - x1)/(y2-y1)
                // x = x1 + (y - y1)(x2 - x1)/(y2-y1)
                int newX = startx;
                int newY = starty;
                while (newX <= endx && newY > endy) {
                    //TODO optimize coefficient
                    double newXcompare = findX(newY - 1, startx, starty, endx, endy);

                    if (newXcompare == newX + 1) {
                        newY--;
                        newX++;
                        canvas.set(newX, newY, colour);
                        //   array[newX - 2][newY - 1] = colour;
                        canvas.set(newX, newY + 1, colour);
                    } else if (newXcompare < newX + 1) {
                        newY--;
                        canvas.set(newX, newY, colour);
                    } else {
                        newX++;
                        canvas.set(newX, newY, colour);

                    }
                }
            }
        }

        return true;
    }

    private static double findY(int x, int x1, int y1, int x2, int y2) {
        return y1 + (double) ((x - x1) * (y2 - y1)) / (x2 - x1);
    }

    private static double findX(int y, int x1, int y1, int x2, int y2) {
        return x1 + (double) ((y - y1) * (x2 - x1)) / (y2 - y1);
    }
}

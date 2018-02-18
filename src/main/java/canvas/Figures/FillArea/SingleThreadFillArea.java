package canvas.Figures.FillArea;

import canvas.Canvas;

import java.util.*;

/**
 * Fill area
 */
public class SingleThreadFillArea {

    /**
     * Fill area with specified colour
     * @param canvas model
     * @param x x
     * @param y y
     * @param colour colour
     */
    public static void fill(Canvas canvas, int x, int y, char colour) {
        char sourceColour = canvas.get(x, y);

        // Nothing to do here
        if (sourceColour == colour) {
            return;
        }

        int xLength = canvas.getxLength();
        int yLength = canvas.getyLength();

        //
        ArrayList<CoordinatesTypeEntry> needToBeChecked = new ArrayList<>((xLength + yLength)*2);

        // first iteration
        CoordinatesTypeEntry coordinatesTypeEntryFirst = new CoordinatesTypeEntry(x, y);
        checkHorizontal(coordinatesTypeEntryFirst, xLength, sourceColour, canvas, needToBeChecked, colour);
        checkVertical(coordinatesTypeEntryFirst, yLength, sourceColour, canvas, needToBeChecked, colour);
        canvas.set(x, y, colour);

        while (!needToBeChecked.isEmpty()) {
            CoordinatesTypeEntry coordinatesTypeEntry = needToBeChecked.remove(needToBeChecked.size() - 1);

            if (coordinatesTypeEntry.typeOfFilling == TypeOfFilling.HorizontalOnly) {
                checkVertical(coordinatesTypeEntry, yLength, sourceColour, canvas, needToBeChecked, colour);
            } else {
                checkHorizontal(coordinatesTypeEntry, xLength, sourceColour, canvas, needToBeChecked, colour);
            }
        }
    }

    private static void checkHorizontal(CoordinatesTypeEntry coordinatesTypeEntry, int xLength, char sourceColour, Canvas canvas, List<CoordinatesTypeEntry> needToBeChecked, char colour) {
        for (int i = coordinatesTypeEntry.x + 1; i <= xLength && canvas.get(i, coordinatesTypeEntry.y) == sourceColour; i++) {
            canvas.set(i, coordinatesTypeEntry.y, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(i, coordinatesTypeEntry.y, TypeOfFilling.HorizontalOnly));
        }

        for (int i = coordinatesTypeEntry.x - 1; i > 0 && canvas.get(i, coordinatesTypeEntry.y) == sourceColour; i--) {
            canvas.set(i, coordinatesTypeEntry.y, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(i, coordinatesTypeEntry.y, TypeOfFilling.HorizontalOnly));
        }
    }

    private static void checkVertical(CoordinatesTypeEntry coordinatesTypeEntry, int yLength, char sourceColour, Canvas canvas, List<CoordinatesTypeEntry> needToBeChecked, char colour) {
        for (int j = coordinatesTypeEntry.y + 1; j <= yLength && canvas.get(coordinatesTypeEntry.x, j) == sourceColour; j++) {
            canvas.set(coordinatesTypeEntry.x, j, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(coordinatesTypeEntry.x, j, TypeOfFilling.VerticalOnly));
        }

        for (int j = coordinatesTypeEntry.y - 1; j > 0 && canvas.get(coordinatesTypeEntry.x, j) == sourceColour; j--) {
            canvas.set(coordinatesTypeEntry.x, j, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(coordinatesTypeEntry.x, j, TypeOfFilling.VerticalOnly));
        }
    }
}

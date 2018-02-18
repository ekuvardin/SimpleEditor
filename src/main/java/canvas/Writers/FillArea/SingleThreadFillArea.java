package canvas.Writers.FillArea;

import canvas.Canvas;

import java.util.*;

public class SingleThreadFillArea implements IFillArea {

    @Override
    public void fill(Canvas canvas, int x, int y, char colour, int threadCount) {
        char sourceColour = canvas.get(x, y);

        // Nothing to do here
        if (sourceColour == colour) {
            return;
        }

        int xLength = canvas.getxLength();
        int yLength = canvas.getyLength();

        //
        ArrayList<CoordinatesEntry> needToBeChecked = new ArrayList<>((xLength + yLength)*2);

        // first iteration
        CoordinatesEntry coordinatesEntryFirst = new CoordinatesEntry(x, y);
        checkHorizontal(coordinatesEntryFirst, xLength, sourceColour, canvas, needToBeChecked, colour);
        checkVertical(coordinatesEntryFirst, yLength, sourceColour, canvas, needToBeChecked, colour);
        canvas.set(x, y, colour);

        while (!needToBeChecked.isEmpty()) {
            CoordinatesEntry coordinatesEntry = needToBeChecked.remove(needToBeChecked.size() - 1);

            if (coordinatesEntry.typeOfFilling == TypeOfFilling.HorizontalOnly) {
                checkVertical(coordinatesEntry, yLength, sourceColour, canvas, needToBeChecked, colour);
            } else {
                checkHorizontal(coordinatesEntry, xLength, sourceColour, canvas, needToBeChecked, colour);
            }
        }
    }

    private void checkHorizontal(CoordinatesEntry coordinatesEntry, int xLength, char sourceColour, Canvas canvas, List<CoordinatesEntry> needToBeChecked, char colour) {
        for (int i = coordinatesEntry.x + 1; i <= xLength && canvas.get(i, coordinatesEntry.y) == sourceColour; i++) {
            canvas.set(i, coordinatesEntry.y, colour);
            needToBeChecked.add(new CoordinatesEntry(i, coordinatesEntry.y, TypeOfFilling.HorizontalOnly));
        }

        for (int i = coordinatesEntry.x - 1; i > 0 && canvas.get(i, coordinatesEntry.y) == sourceColour; i--) {
            canvas.set(i, coordinatesEntry.y, colour);
            needToBeChecked.add(new CoordinatesEntry(i, coordinatesEntry.y, TypeOfFilling.HorizontalOnly));
        }
    }

    private void checkVertical(CoordinatesEntry coordinatesEntry, int yLength, char sourceColour, Canvas canvas, List<CoordinatesEntry> needToBeChecked, char colour) {
        for (int j = coordinatesEntry.y + 1; j <= yLength && canvas.get(coordinatesEntry.x, j) == sourceColour; j++) {
            canvas.set(coordinatesEntry.x, j, colour);
            needToBeChecked.add(new CoordinatesEntry(coordinatesEntry.x, j, TypeOfFilling.VerticalOnly));
        }

        for (int j = coordinatesEntry.y - 1; j > 0 && canvas.get(coordinatesEntry.x, j) == sourceColour; j--) {
            canvas.set(coordinatesEntry.x, j, colour);
            needToBeChecked.add(new CoordinatesEntry(coordinatesEntry.x, j, TypeOfFilling.VerticalOnly));
        }
    }
}

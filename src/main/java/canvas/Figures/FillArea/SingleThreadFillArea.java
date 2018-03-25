package canvas.Figures.FillArea;

import canvas.Model;

import java.util.*;

/**
 * Fill area
 */
public class SingleThreadFillArea implements IFillArea {

    protected Model model;

    public SingleThreadFillArea(Model model) {
        this.model = model;
    }

    @Override
    public void fill(int x, int y, Boundary boundary, char colour) {
        char sourceColour = model.get(x, y);
        // Nothing to do here
        if (sourceColour == colour) {
            return;
        }

        int xLength = model.getWidth();
        int yLength = model.getHeight();

        List<CoordinatesTypeEntry> needToBeChecked = new ArrayList<>((xLength + yLength) * 2);

        needToBeChecked.add(new CoordinatesTypeEntry(x, y));
        model.set(x, y, colour);

        while (!needToBeChecked.isEmpty()) {
            CoordinatesTypeEntry coordinatesTypeEntry = needToBeChecked.remove(needToBeChecked.size() - 1);

            if (coordinatesTypeEntry.typeOfFilling == TypeOfFilling.HorizontalOnly) {
                checkVertical(coordinatesTypeEntry, boundary, needToBeChecked, sourceColour, colour);
            } else if (coordinatesTypeEntry.typeOfFilling == TypeOfFilling.VerticalOnly) {
                checkHorizontal(coordinatesTypeEntry, boundary, needToBeChecked, sourceColour, colour);
            } else {
                checkVertical(coordinatesTypeEntry, boundary, needToBeChecked, sourceColour, colour);
                checkHorizontal(coordinatesTypeEntry, boundary, needToBeChecked, sourceColour, colour);
            }
        }
    }

    private void checkHorizontal(CoordinatesTypeEntry coordinatesTypeEntry, Boundary boundary, List<CoordinatesTypeEntry> needToBeChecked, char sourceColour, char colour) {
        for (int i = coordinatesTypeEntry.x + 1; i <= boundary.maxWidth && model.get(i, coordinatesTypeEntry.y) == sourceColour; i++) {
            model.set(i, coordinatesTypeEntry.y, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(i, coordinatesTypeEntry.y, TypeOfFilling.HorizontalOnly));
        }

        for (int i = coordinatesTypeEntry.x - 1; i >= boundary.minWidth && model.get(i, coordinatesTypeEntry.y) == sourceColour; i--) {
            model.set(i, coordinatesTypeEntry.y, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(i, coordinatesTypeEntry.y, TypeOfFilling.HorizontalOnly));
        }
    }

    private void checkVertical(CoordinatesTypeEntry coordinatesTypeEntry, Boundary boundary, List<CoordinatesTypeEntry> needToBeChecked, char sourceColour, char colour) {
        for (int j = coordinatesTypeEntry.y + 1; j <= boundary.maxHeight && model.get(coordinatesTypeEntry.x, j) == sourceColour; j++) {
            model.set(coordinatesTypeEntry.x, j, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(coordinatesTypeEntry.x, j, TypeOfFilling.VerticalOnly));
        }

        for (int j = coordinatesTypeEntry.y - 1; j >= boundary.minHeight && model.get(coordinatesTypeEntry.x, j) == sourceColour; j--) {
            model.set(coordinatesTypeEntry.x, j, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(coordinatesTypeEntry.x, j, TypeOfFilling.VerticalOnly));
        }
    }
}

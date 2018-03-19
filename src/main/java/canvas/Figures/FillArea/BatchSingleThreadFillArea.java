package canvas.Figures.FillArea;

import canvas.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Fill area
 */
public class BatchSingleThreadFillArea implements IBatchFillArea {

    protected Model model;

    public BatchSingleThreadFillArea(Model model) {
        this.model = model;
    }

    @Override
    public BorderPoints fill(int x, int y, int minWidth, int maxWidth, int minHeight, int maxHeight, char colour) {
        BorderPoints borderPoints = new BorderPoints();
        char sourceColour = model.get(x, y);
        Bound bound = new Bound(minWidth, maxWidth, minHeight, maxHeight);

        if (sourceColour != colour) {

            int xLength = model.getWidth();
            int yLength = model.getHeight();

            List<CoordinatesTypeEntry> needToBeChecked = new ArrayList<>((xLength + yLength) * 2);

            needToBeChecked.add(new CoordinatesTypeEntry(x, y));
            model.set(x, y, colour);

            while (!needToBeChecked.isEmpty()) {
                CoordinatesTypeEntry coordinatesTypeEntry = getAny(needToBeChecked, bound, borderPoints);

                if (coordinatesTypeEntry.typeOfFilling == TypeOfFilling.HorizontalOnly) {
                    checkVertical(coordinatesTypeEntry, bound, needToBeChecked, sourceColour, colour);
                } else if (coordinatesTypeEntry.typeOfFilling == TypeOfFilling.VerticalOnly) {
                    checkHorizontal(coordinatesTypeEntry, bound, needToBeChecked, sourceColour, colour);
                } else {
                    checkVertical(coordinatesTypeEntry, bound, needToBeChecked, sourceColour, colour);
                    checkHorizontal(coordinatesTypeEntry, bound, needToBeChecked, sourceColour, colour);
                }
            }
        }
        return borderPoints;
    }

    protected CoordinatesTypeEntry getAny(List<CoordinatesTypeEntry> needToBeChecked, Bound bound, BorderPoints borderPoints) {
        CoordinatesTypeEntry value = needToBeChecked.remove(needToBeChecked.size() - 1);

        if (value.x == bound.minWidth) {
            borderPoints.left.add((value.y - 1) * model.getWidth() + value.x);
        }
        if (value.x == bound.maxWidth) {
            borderPoints.right.add((value.y + 1) * model.getWidth() + value.x);
        }
        if (value.x == bound.minHeight) {
            borderPoints.top.add(value.y * model.getWidth() + value.x - 1);
        }
        if (value.x == bound.maxHeight) {
            borderPoints.bottom.add(value.y * model.getWidth() + value.x + 1);
        }
        return value;
    }

    private void checkHorizontal(CoordinatesTypeEntry coordinatesTypeEntry, Bound bound, List<CoordinatesTypeEntry> needToBeChecked, char sourceColour, char colour) {
        for (int i = coordinatesTypeEntry.x + 1; i <= bound.maxWidth && model.get(i, coordinatesTypeEntry.y) == sourceColour; i++) {
            model.set(i, coordinatesTypeEntry.y, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(i, coordinatesTypeEntry.y, TypeOfFilling.HorizontalOnly));
        }

        for (int i = coordinatesTypeEntry.x - 1; i > bound.minWidth && model.get(i, coordinatesTypeEntry.y) == sourceColour; i--) {
            model.set(i, coordinatesTypeEntry.y, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(i, coordinatesTypeEntry.y, TypeOfFilling.HorizontalOnly));
        }
    }

    private void checkVertical(CoordinatesTypeEntry coordinatesTypeEntry,  Bound bound, List<CoordinatesTypeEntry> needToBeChecked, char sourceColour, char colour) {
        for (int j = coordinatesTypeEntry.y + 1; j <= bound.maxHeight && model.get(coordinatesTypeEntry.x, j) == sourceColour; j++) {
            model.set(coordinatesTypeEntry.x, j, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(coordinatesTypeEntry.x, j, TypeOfFilling.VerticalOnly));
        }

        for (int j = coordinatesTypeEntry.y - 1; j > bound.minHeight && model.get(coordinatesTypeEntry.x, j) == sourceColour; j--) {
            model.set(coordinatesTypeEntry.x, j, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(coordinatesTypeEntry.x, j, TypeOfFilling.VerticalOnly));
        }
    }
}

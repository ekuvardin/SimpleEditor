package canvas.figures.fillArea;

import canvas.Boundary;
import canvas.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Fill area and pass points that need to be checked into neighbors areas
 */
public class BatchSingleThreadFillArea implements IBatchFillArea {

    protected Model model;

    public BatchSingleThreadFillArea(Model model) {
        this.model = model;
    }

    @Override
    public BorderPoints fill(List<CoordinatesTypeEntry> startPoints, Boundary boundary, char sourceColour, char colour) {
        BorderPoints borderPoints = new BorderPoints();

        int xLength = model.getWidth();
        int yLength = model.getHeight();

        List<CoordinatesTypeEntry> needToBeChecked = new ArrayList<>((xLength + yLength) * 2);

        Boundary mainBorder = model.getBoundary();
        for (CoordinatesTypeEntry value : startPoints) {
            if (mainBorder.checkInBound(value.x, value.y) && sourceColour == model.get(value.x, value.y)) {
                needToBeChecked.add(value);
                model.set(value.x, value.y, colour);
            }
        }

        while (!needToBeChecked.isEmpty()) {
            CoordinatesTypeEntry coordinatesTypeEntry = getAny(needToBeChecked, boundary, mainBorder, borderPoints);

            if (coordinatesTypeEntry.typeOfFilling == TypeOfFilling.HorizontalOnly) {
                checkVertical(coordinatesTypeEntry, boundary, needToBeChecked, sourceColour, colour);
            } else if (coordinatesTypeEntry.typeOfFilling == TypeOfFilling.VerticalOnly) {
                checkHorizontal(coordinatesTypeEntry, boundary, needToBeChecked, sourceColour, colour);
            } else {
                checkVertical(coordinatesTypeEntry, boundary, needToBeChecked, sourceColour, colour);
                checkHorizontal(coordinatesTypeEntry, boundary, needToBeChecked, sourceColour, colour);
            }
        }

        return borderPoints;
    }

    private CoordinatesTypeEntry getAny(List<CoordinatesTypeEntry> needToBeChecked, Boundary boundary, Boundary mainBorder, BorderPoints borderPoints) {
        CoordinatesTypeEntry value = needToBeChecked.remove(needToBeChecked.size() - 1);

        if (value.x == boundary.getMinWidth() && mainBorder.checkInBound(value.x - 1, value.y)) {
            borderPoints.left.add(new CoordinatesTypeEntry(value.x - 1, value.y));
        }
        if (value.x == boundary.getMaxWidth() && mainBorder.checkInBound(value.x + 1, value.y)) {
            borderPoints.right.add(new CoordinatesTypeEntry(value.x + 1, value.y));
        }
        if (value.y == boundary.getMinHeight() && mainBorder.checkInBound(value.x, value.y - 1)) {
            borderPoints.top.add(new CoordinatesTypeEntry(value.x, value.y - 1));
        }
        if (value.y == boundary.getMaxHeight() && mainBorder.checkInBound(value.x, value.y + 1)) {
            borderPoints.bottom.add(new CoordinatesTypeEntry(value.x, value.y + 1));
        }

        return value;
    }

    private void checkHorizontal(CoordinatesTypeEntry coordinatesTypeEntry, Boundary boundary, List<CoordinatesTypeEntry> needToBeChecked, char sourceColour, char colour) {
        for (int i = coordinatesTypeEntry.x + 1; i <= boundary.getMaxWidth() && model.get(i, coordinatesTypeEntry.y) == sourceColour; i++) {
            model.set(i, coordinatesTypeEntry.y, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(i, coordinatesTypeEntry.y, TypeOfFilling.HorizontalOnly));
        }

        for (int i = coordinatesTypeEntry.x - 1; i >= boundary.getMinWidth() && model.get(i, coordinatesTypeEntry.y) == sourceColour; i--) {
            model.set(i, coordinatesTypeEntry.y, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(i, coordinatesTypeEntry.y, TypeOfFilling.HorizontalOnly));
        }
    }

    private void checkVertical(CoordinatesTypeEntry coordinatesTypeEntry, Boundary boundary, List<CoordinatesTypeEntry> needToBeChecked, char sourceColour, char colour) {
        for (int j = coordinatesTypeEntry.y + 1; j <= boundary.getMaxHeight() && model.get(coordinatesTypeEntry.x, j) == sourceColour; j++) {
            model.set(coordinatesTypeEntry.x, j, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(coordinatesTypeEntry.x, j, TypeOfFilling.VerticalOnly));
        }

        for (int j = coordinatesTypeEntry.y - 1; j >= boundary.getMinHeight() && model.get(coordinatesTypeEntry.x, j) == sourceColour; j--) {
            model.set(coordinatesTypeEntry.x, j, colour);
            needToBeChecked.add(new CoordinatesTypeEntry(coordinatesTypeEntry.x, j, TypeOfFilling.VerticalOnly));
        }
    }
}

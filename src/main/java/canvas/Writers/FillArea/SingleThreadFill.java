package canvas.Writers.FillArea;

import canvas.Canvas;
import canvas.SimpleCanvas;

import java.util.*;

public class SingleThreadFill implements IFillArea {

    @Override
    public void fill(Canvas canvas, int x, int y, char colour, int threadCount) {
        char sourceColour = canvas.get(x, y);

        // Nothing to do here
        if (sourceColour == colour) {
            return;
        }

        int xLength = canvas.getxLength();
        int yLength = canvas.getyLength();

        ArrayList<Entry> needToBeChecked = new ArrayList<>(xLength);

        // first iteration
        Entry entryFirst = new Entry(x, y);
        checkHorizontal(entryFirst, xLength, sourceColour, canvas, needToBeChecked, colour);
        checkVertical(entryFirst, yLength, sourceColour, canvas, needToBeChecked, colour);
        canvas.set(x, y, colour);

        while (!needToBeChecked.isEmpty()) {
            Entry entry = needToBeChecked.remove(needToBeChecked.size() - 1);

            if (entry.typeOfFilling == TypeOfFilling.HorizontalOnly) {
                checkVertical(entry, yLength, sourceColour, canvas, needToBeChecked, colour);
            } else {
                checkHorizontal(entry, xLength, sourceColour, canvas, needToBeChecked, colour);
            }
        }
    }

    private void checkHorizontal(Entry entry, int xLength, char sourceColour, Canvas canvas, List<Entry> needToBeChecked, char colour) {
        for (int i = entry.x + 1; i <= xLength && canvas.get(i, entry.y) == sourceColour; i++) {
            canvas.set(i, entry.y, colour);
            needToBeChecked.add(new Entry(i, entry.y, TypeOfFilling.HorizontalOnly));
        }

        for (int i = entry.x - 1; i > 0 && canvas.get(i, entry.y) == sourceColour; i--) {
            canvas.set(i, entry.y, colour);
            needToBeChecked.add(new Entry(i, entry.y, TypeOfFilling.HorizontalOnly));
        }
    }

    private void checkVertical(Entry entry, int yLength, char sourceColour, Canvas canvas, List<Entry> needToBeChecked, char colour) {
        for (int j = entry.y + 1; j <= yLength && canvas.get(entry.x, j) == sourceColour; j++) {
            canvas.set(entry.x, j, colour);
            needToBeChecked.add(new Entry(entry.x, j, TypeOfFilling.VerticalOnly));
        }

        for (int j = entry.y - 1; j > 0 && canvas.get(entry.x, j) == sourceColour; j--) {
            canvas.set(entry.x, j, colour);
            needToBeChecked.add(new Entry(entry.x, j, TypeOfFilling.VerticalOnly));
        }
    }

}

package canvas.Writers.FillArea;

import canvas.Canvas;

import java.util.*;

public class SingleThreadFill implements IFillArea {

    @Override
    public void fill(Canvas canvas, int x, int y, char colour, int threadCount) {
        char[][] array = canvas.getArray();
        char sourceColour = array[x - 1][y - 1];

        // Nothing to do here
        if (sourceColour == colour) {
            return;
        }

        int xLength = canvas.getxLength();
        int yLength = canvas.getyLength();

        ArrayList<Entry> needToBeChecked = new ArrayList<>(xLength);

        // first iteration
        Entry entryFirst = new Entry(x, y);
        checkHorizontal(entryFirst, xLength, sourceColour, array, needToBeChecked, colour);
        checkVertical(entryFirst, yLength, sourceColour, array, needToBeChecked, colour);
        array[x - 1][y - 1] = colour;

        while (!needToBeChecked.isEmpty()) {
            Entry entry = needToBeChecked.remove(needToBeChecked.size() - 1);

            if (entry.typeOfFilling == TypeOfFilling.HorizontalOnly) {
                checkVertical(entry, yLength, sourceColour, array, needToBeChecked, colour);
            } else {
                checkHorizontal(entry, xLength, sourceColour, array, needToBeChecked, colour);
            }
        }
    }

    private void checkHorizontal(Entry entry, int xLength, char sourceColour, char[][] array, List<Entry> needToBeChecked, char colour) {
        for (int i = entry.x + 1; i <= xLength && array[i - 1][entry.y - 1] == sourceColour; i++) {
            array[i - 1][entry.y - 1] = colour;
            needToBeChecked.add(new Entry(i, entry.y, TypeOfFilling.HorizontalOnly));
        }

        for (int i = entry.x - 1; i > 0 && array[i - 1][entry.y - 1] == sourceColour; i--) {
            array[i - 1][entry.y - 1] = colour;
            needToBeChecked.add(new Entry(i, entry.y, TypeOfFilling.HorizontalOnly));
        }
    }

    private void checkVertical(Entry entry, int yLength, char sourceColour, char[][] array, List<Entry> needToBeChecked, char colour) {
        for (int j = entry.y + 1; j <= yLength && array[entry.x - 1][j - 1] == sourceColour; j++) {
            array[entry.x - 1][j - 1] = colour;
            needToBeChecked.add(new Entry(entry.x, j, TypeOfFilling.VerticalOnly));
        }

        for (int j = entry.y - 1; j > 0 && array[entry.x - 1][j - 1] == sourceColour; j--) {
            array[entry.x - 1][j - 1] = colour;
            needToBeChecked.add(new Entry(entry.x, j, TypeOfFilling.VerticalOnly));
        }
    }

    private enum TypeOfFilling {
        AllDirections,
        HorizontalOnly,
        VerticalOnly
    }

    private static class Entry implements Comparable<Entry> {
        int x;
        int y;

        TypeOfFilling typeOfFilling;   // -1 horizont, 0 -all directions, 1- vertical

        public Entry(int x, int y) {
            this.x = x;
            this.y = y;
            typeOfFilling = TypeOfFilling.AllDirections;
        }

        public Entry(int x, int y, TypeOfFilling typeOfFilling) {
            this.x = x;
            this.y = y;
            this.typeOfFilling = typeOfFilling;
        }

        @Override
        public int compareTo(Entry o) {
            if (this == o) return 0;

            if (this.x < o.x)
                return -1;
            if (this.x > o.x)
                return 1;

            if (this.y < o.y)
                return -1;
            if (this.y > o.y)
                return 1;

            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entry entry = (Entry) o;

            if (x != entry.x) return false;
            return y == entry.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}

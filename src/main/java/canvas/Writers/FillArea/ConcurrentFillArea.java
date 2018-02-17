package canvas.Writers.FillArea;

import canvas.SimpleCanvas;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class ConcurrentFillArea{}/* implements IFillArea {

    @Override
    public void fill(SimpleCanvas simpleCanvas, int x, int y, char colour, int threadCount) {
        char[][] array = Collections.synchronizedCollection(simpleCanvas.getArray());
        char sourceColour = array[x - 1][y - 1];

        // Nothing to do here
        if (sourceColour == colour) {
            return;
        }

        int xLength = simpleCanvas.getxLength();
        int yLength = simpleCanvas.getyLength();

        IWaitStrategy strategy = new ThreadInterruptedStrategy();
        ArrayBlockingQueue<Entry> needToBeChecked = new ArrayBlockingQueue<>(1024);

        // first iteration
        Entry entryFirst = new Entry(x, y);
        checkHorizontal(entryFirst, xLength, sourceColour, array, needToBeChecked, colour);
        checkVertical(entryFirst, yLength, sourceColour, array, needToBeChecked, colour);
        array[x - 1][y - 1] = colour;

        while (!needToBeChecked.isEmpty()) {
            SingleThreadFill.Entry entry = needToBeChecked.remove(needToBeChecked.size() - 1);

            if (entry.typeOfFilling == SingleThreadFill.TypeOfFilling.HorizontalOnly) {
                checkVertical(entry, yLength, sourceColour, array, needToBeChecked, colour);
            } else {
                checkHorizontal(entry, xLength, sourceColour, array, needToBeChecked, colour);
            }
        }
    }

    private void checkHorizontal(SingleThreadFill.Entry entry, int xLength, char sourceColour, char[][] array, List<SingleThreadFill.Entry> needToBeChecked, char colour) {
        for (int i = entry.x + 1; i <= xLength && array[i - 1][entry.y - 1] == sourceColour; i++) {
            array[i - 1][entry.y - 1] = colour;
            needToBeChecked.add(new SingleThreadFill.Entry(i, entry.y, SingleThreadFill.TypeOfFilling.HorizontalOnly));
        }

        for (int i = entry.x - 1; i > 0 && array[i - 1][entry.y - 1] == sourceColour; i--) {
            array[i - 1][entry.y - 1] = colour;
            needToBeChecked.add(new SingleThreadFill.Entry(i, entry.y, SingleThreadFill.TypeOfFilling.HorizontalOnly));
        }
    }

    private void checkVertical(SingleThreadFill.Entry entry, int yLength, char sourceColour, char[][] array, List<SingleThreadFill.Entry> needToBeChecked, char colour) {
        for (int j = entry.y + 1; j <= yLength && array[entry.x - 1][j - 1] == sourceColour; j++) {
            array[entry.x - 1][j - 1] = colour;
            needToBeChecked.add(new SingleThreadFill.Entry(entry.x, j, SingleThreadFill.TypeOfFilling.VerticalOnly));
        }

        for (int j = entry.y - 1; j > 0 && array[entry.x - 1][j - 1] == sourceColour; j--) {
            array[entry.x - 1][j - 1] = colour;
            needToBeChecked.add(new SingleThreadFill.Entry(entry.x, j, SingleThreadFill.TypeOfFilling.VerticalOnly));
        }
    }

}*/

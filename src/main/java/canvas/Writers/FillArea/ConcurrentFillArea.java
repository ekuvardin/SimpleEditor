package canvas.Writers.FillArea;

import canvas.Canvas;
import canvas.Writers.FillArea.StopStrategy.WaitingChain;
import canvas.Writers.FillArea.WaitStrategy.IWaitStrategy;
import canvas.Writers.FillArea.WaitStrategy.ThreadStopStrategy;

import java.util.concurrent.*;

public class ConcurrentFillArea implements IFillArea {

    @Override
    public void fill(Canvas canvas, int x, int y, char colour, int threadCount) {
        char sourceColour = canvas.get(x, y);

        // Nothing to do here
        if (sourceColour == colour) {
            return;
        }

        int xLength = canvas.getxLength();
        int yLength = canvas.getyLength();

        ArrayBlockingQueue<CoordinatesEntry> needToBeChecked = new ArrayBlockingQueue<>(yLength * xLength);

        // first iteration
        //CoordinatesEntry entryFirst = new CoordinatesEntry(x, y);
      //  checkHorizontal(entryFirst, xLength, sourceColour, canvas, needToBeChecked, colour);
      //  checkVertical(entryFirst, yLength, sourceColour, canvas, needToBeChecked, colour);
        canvas.set(x, y, colour);
        needToBeChecked.add(new CoordinatesEntry(x, y));

        final IWaitStrategy strategy = new ThreadStopStrategy();
        final WaitingChain chain = new WaitingChain(threadCount);
        final ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        CompletableFuture[] futures = new CompletableFuture[threadCount];
        for (int i = 0; i < threadCount; i++) {
            futures[i] = CompletableFuture.supplyAsync(() -> {
                try {
                    while (strategy.canRun()) {
                        CoordinatesEntry coordinatesEntry = needToBeChecked.poll();

                        if (coordinatesEntry != null) {
                            chain.enter();
                            if (coordinatesEntry.typeOfFilling == TypeOfFilling.HorizontalOnly) {
                                checkVertical(coordinatesEntry, yLength, sourceColour, canvas, needToBeChecked, colour);
                            } else  if (coordinatesEntry.typeOfFilling == TypeOfFilling.VerticalOnly) {
                                checkHorizontal(coordinatesEntry, xLength, sourceColour, canvas, needToBeChecked, colour);
                            } else {
                                checkHorizontal(coordinatesEntry, xLength, sourceColour, canvas, needToBeChecked, colour);
                                checkVertical(coordinatesEntry, yLength, sourceColour, canvas, needToBeChecked, colour);
                            }
                        } else {
                            chain.exit();

                            if(chain.isLimitSucceed())
                                strategy.stop();
                            else
                                strategy.trySpinWait();
                        }
                    }
                    return null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }, executor);
        }

        try {
            if(!executor.awaitTermination(60, TimeUnit.SECONDS)){
                System.err.println("Cann't execute within timeout");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkHorizontal(CoordinatesEntry coordinatesEntry, int xLength, char sourceColour, Canvas canvas, ArrayBlockingQueue<CoordinatesEntry> needToBeChecked, char colour) {
        for (int i = coordinatesEntry.x + 1; i <= xLength && canvas.get(i, coordinatesEntry.y) == sourceColour; i++) {
            canvas.set(i, coordinatesEntry.y, colour);
            needToBeChecked.add(new CoordinatesEntry(i, coordinatesEntry.y, TypeOfFilling.HorizontalOnly));
        }

        for (int i = coordinatesEntry.x - 1; i > 0 && canvas.get(i, coordinatesEntry.y) == sourceColour; i--) {
            canvas.set(i, coordinatesEntry.y, colour);
            needToBeChecked.add(new CoordinatesEntry(i, coordinatesEntry.y, TypeOfFilling.HorizontalOnly));
        }
    }

    private void checkVertical(CoordinatesEntry coordinatesEntry, int yLength, char sourceColour, Canvas canvas, ArrayBlockingQueue<CoordinatesEntry> needToBeChecked, char colour) {
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

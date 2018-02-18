package canvas.Figures.FillArea;

import canvas.Canvas;
import canvas.Figures.FillArea.StopStrategy.WaitingChain;
import canvas.Figures.FillArea.WaitStrategy.IWaitStrategy;
import canvas.Figures.FillArea.WaitStrategy.ThreadStopStrategy;

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
        canvas.set(x, y, colour);
        needToBeChecked.add(new CoordinatesEntry(x, y));

        final IWaitStrategy strategy = new ThreadStopStrategy();
        final WaitingChain chain = new WaitingChain();
        final ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        //TODO do smt with futures results.(Print to console if smt going wrong)
        CompletableFuture[] futures = new CompletableFuture[threadCount];

        for (int i = 0; i < threadCount; i++) {
            futures[i] = CompletableFuture.supplyAsync(() -> {
                try {
                    chain.startIteration();
                    while (strategy.canRun()) {
                        CoordinatesEntry coordinatesEntry = needToBeChecked.poll();

                        if (coordinatesEntry != null) {
                            chain.reset();
                            if (coordinatesEntry.typeOfFilling == TypeOfFilling.HorizontalOnly) {
                                checkVertical(coordinatesEntry, yLength, sourceColour, canvas, needToBeChecked, colour);
                            } else  if (coordinatesEntry.typeOfFilling == TypeOfFilling.VerticalOnly) {
                                checkHorizontal(coordinatesEntry, xLength, sourceColour, canvas, needToBeChecked, colour);
                            } else {
                                checkHorizontal(coordinatesEntry, xLength, sourceColour, canvas, needToBeChecked, colour);
                                checkVertical(coordinatesEntry, yLength, sourceColour, canvas, needToBeChecked, colour);
                            }
                        } else {
                            chain.endIteration();

                            if(chain.isLimitSucceed(threadCount))
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
            executor.shutdown();
            //TODO set timeout in parameters
            if(!executor.awaitTermination(60, TimeUnit.SECONDS)){
                //TODO all errors should be displayed using view
                System.out.println("Cann't execute within timeout 60 seconds");
                for(CompletableFuture future: futures){
                    future.cancel(true);
                }
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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

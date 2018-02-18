package canvas.Figures.FillArea;

import canvas.Canvas;
import canvas.Figures.FillArea.Stores.IStore;
import canvas.Figures.FillArea.Stores.RandomStartStore;
import canvas.Figures.FillArea.WaitStrategy.IWaitStrategy;
import canvas.Figures.FillArea.WaitStrategy.ThreadStopStrategy;

import java.util.concurrent.*;

/**
 * Try to fill area with several threads. (Under construction. May work very long but correct(see integration tests)
 */
public class ConcurrentFillArea{

    /**
     * Fill area with specified colour
     * @param canvas model
     * @param x x
     * @param y y
     * @param colour colour
     * @param threadCount thread count to fill
     */
    public static void fill(Canvas canvas, int x, int y, char colour, int threadCount) {
        char sourceColour = canvas.get(x, y);

        // Nothing to do here
        if (sourceColour == colour) {
            return;
        }

        int xLength = canvas.getxLength();
        int yLength = canvas.getyLength();


        final IWaitStrategy strategy = new ThreadStopStrategy();
        IStore<CoordinatesEntry> store = new RandomStartStore<>(Math.max(yLength, xLength) * threadCount, strategy);
        // first iteration
        canvas.set(x, y, colour);
        try {
            store.put(new CoordinatesEntry(x, y));
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        final ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        //TODO do smt with futures results.(Print to console if smt going wrong)
        CompletableFuture[] futures = new CompletableFuture[threadCount];

        for (int i = 0; i < threadCount; i++) {
            futures[i] = CompletableFuture.supplyAsync(() -> {
                try {
                    int maxTries = 0;
                    while (strategy.canRun()) {
                        CoordinatesEntry coordinatesEntry = store.poll();
                        if (coordinatesEntry != null) {
                            maxTries = 0;
                            checkHorizontal(coordinatesEntry, xLength, sourceColour, canvas, store, colour);
                            checkVertical(coordinatesEntry, yLength, sourceColour, canvas, store, colour);
                        } else {
                            //TODO make some decision what kind stop technique to use
                            // strategy.stop()
                            maxTries++;
                            if (maxTries == 32)
                                return null;
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
            if (!executor.awaitTermination(6000, TimeUnit.SECONDS)) {
                //TODO all errors should be displayed using view
                System.out.println("Cann't execute within timeout 60 seconds");
                for (CompletableFuture future : futures) {
                    future.cancel(true);
                }
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void checkHorizontal(CoordinatesEntry coordinatesEntry, int xLength, char sourceColour, Canvas canvas, IStore<CoordinatesEntry> needToBeChecked, char colour) throws InterruptedException {
        //   for (int i = coordinatesEntry.x + 1; i <= xLength && canvas.get(i, coordinatesEntry.y) == sourceColour; i++) {
        int i = coordinatesEntry.x + 1;
        if (i <= xLength && canvas.get(i, coordinatesEntry.y) == sourceColour) {
            canvas.set(i, coordinatesEntry.y, colour);
            needToBeChecked.put(new CoordinatesEntry(i, coordinatesEntry.y));
        }
        i = coordinatesEntry.x - 1;
        //for (int i = coordinatesEntry.x - 1; i > 0 && canvas.get(i, coordinatesEntry.y) == sourceColour; i--) {
        if (i > 0 && canvas.get(i, coordinatesEntry.y) == sourceColour) {
            canvas.set(i, coordinatesEntry.y, colour);
            needToBeChecked.put(new CoordinatesEntry(i, coordinatesEntry.y));
        }
    }

    private static void checkVertical(CoordinatesEntry coordinatesEntry, int yLength, char sourceColour, Canvas canvas, IStore<CoordinatesEntry> needToBeChecked, char colour) throws InterruptedException {
        // for (int j = coordinatesEntry.y + 1; j <= yLength && canvas.get(coordinatesEntry.x, j) == sourceColour; j++) {
        int j = coordinatesEntry.y + 1;
        if (j <= yLength && canvas.get(coordinatesEntry.x, j) == sourceColour) {
            canvas.set(coordinatesEntry.x, j, colour);
            needToBeChecked.put(new CoordinatesEntry(coordinatesEntry.x, j));
        }


        // for (int j = coordinatesEntry.y - 1; j > 0 && canvas.get(coordinatesEntry.x, j) == sourceColour; j--) {
        j = coordinatesEntry.y - 1;
        if (j > 0 && canvas.get(coordinatesEntry.x, j) == sourceColour) {
            canvas.set(coordinatesEntry.x, j, colour);
            needToBeChecked.put(new CoordinatesEntry(coordinatesEntry.x, j));
        }
    }
}

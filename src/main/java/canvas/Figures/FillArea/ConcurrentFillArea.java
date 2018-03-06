package canvas.Figures.FillArea;

import canvas.Model;
import canvas.Figures.FillArea.Stores.IStore;
import canvas.Figures.FillArea.Stores.RandomStartStore;
import canvas.Figures.FillArea.WaitStrategy.IWaitStrategy;
import canvas.Figures.FillArea.WaitStrategy.ThreadStopStrategy;

import java.util.concurrent.*;

/**
 * Try to fill area with several threads. (Under construction. May work very long but correct(see integration tests)
 */
public class ConcurrentFillArea implements IFillArea {

    protected Model model;
    protected int threadCount;

    public ConcurrentFillArea(Model model, int threadCount) {
        this.model = model;
        this.threadCount = threadCount;
    }

    @Override
    public void fill(int x, int y, int minWidth, int maxWidth, int minHeight, int maxHeight, char colour) {
        char sourceColour = model.get(x, y);

        // Nothing to do here
        if (sourceColour == colour) {
            return;
        }

        int xLength = model.getWidth();
        int yLength = model.getHeight();


        final IWaitStrategy strategy = new ThreadStopStrategy();
        IStore<CoordinatesEntry> store = new RandomStartStore<>(Math.max(yLength, xLength) * threadCount, strategy);
        // first iteration
        model.set(x, y, colour);
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
                            checkHorizontal(coordinatesEntry, xLength, sourceColour, model, store, colour);
                            checkVertical(coordinatesEntry, yLength, sourceColour, model, store, colour);
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

    private static void checkHorizontal(CoordinatesEntry coordinatesEntry, int xLength, char sourceColour, Model model, IStore<CoordinatesEntry> needToBeChecked, char colour) throws InterruptedException {
        //   for (int i = coordinatesEntry.x + 1; i <= xLength && model.get(i, coordinatesEntry.y) == sourceColour; i++) {
        int i = coordinatesEntry.x + 1;
        if (i <= xLength && model.get(i, coordinatesEntry.y) == sourceColour) {
            model.set(i, coordinatesEntry.y, colour);
            needToBeChecked.put(new CoordinatesEntry(i, coordinatesEntry.y));
        }
        i = coordinatesEntry.x - 1;
        //for (int i = coordinatesEntry.x - 1; i > 0 && model.get(i, coordinatesEntry.y) == sourceColour; i--) {
        if (i > 0 && model.get(i, coordinatesEntry.y) == sourceColour) {
            model.set(i, coordinatesEntry.y, colour);
            needToBeChecked.put(new CoordinatesEntry(i, coordinatesEntry.y));
        }
    }

    private static void checkVertical(CoordinatesEntry coordinatesEntry, int yLength, char sourceColour, Model model, IStore<CoordinatesEntry> needToBeChecked, char colour) throws InterruptedException {
        // for (int j = coordinatesEntry.y + 1; j <= yLength && model.get(coordinatesEntry.x, j) == sourceColour; j++) {
        int j = coordinatesEntry.y + 1;
        if (j <= yLength && model.get(coordinatesEntry.x, j) == sourceColour) {
            model.set(coordinatesEntry.x, j, colour);
            needToBeChecked.put(new CoordinatesEntry(coordinatesEntry.x, j));
        }


        // for (int j = coordinatesEntry.y - 1; j > 0 && model.get(coordinatesEntry.x, j) == sourceColour; j--) {
        j = coordinatesEntry.y - 1;
        if (j > 0 && model.get(coordinatesEntry.x, j) == sourceColour) {
            model.set(coordinatesEntry.x, j, colour);
            needToBeChecked.put(new CoordinatesEntry(coordinatesEntry.x, j));
        }
    }

}

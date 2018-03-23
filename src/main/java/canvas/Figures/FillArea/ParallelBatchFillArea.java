package canvas.Figures.FillArea;

import canvas.Model;
import canvas.Viewer.IView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class ParallelBatchFillArea implements IFillArea {

    protected Model model;
    protected IView view;
    protected int blockSizeW;
    protected int blockSizeH;
    protected int threadCount;
    protected IBatchFillArea fillArea;
    protected ConcurrentHashMap<Boundary, Boundary> lockedBoundary;

    public ParallelBatchFillArea(IView view, Model model, int threadCount, int blockSizeW, int blockSizeH, IBatchFillArea fillArea) {
        this.view = view;
        this.threadCount = threadCount;
        this.blockSizeW = blockSizeW;
        this.blockSizeH = blockSizeH;
        this.fillArea = fillArea;
        this.model = model;
        lockedBoundary = new ConcurrentHashMap<>(threadCount * 2);
    }

    @Override
    public void fill(int x, int y, Boundary boundary, char colour) {
        if(model.get(x, y) != colour) {
            try {

                MergeReducer mergeReducer = new MergeReducer(null, List.of(y * (boundary.maxWidth - boundary.minWidth) + x), boundary, model.get(x, y), colour);
                new ForkJoinPool(threadCount).invoke(mergeReducer);
                mergeReducer.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    private void tryLock(Boundary boundary) throws InterruptedException {
        Boundary old;
        while ((old = lockedBoundary.putIfAbsent(boundary, boundary)) != null) {
            synchronized (old) {
                while (lockedBoundary.get(boundary) == old) {
                    old.wait();
                }
            }
        }
    }

    private void releaseLock(Boundary boundary) {
        synchronized (boundary) {
            lockedBoundary.remove(boundary);
            boundary.notifyAll();
        }
    }

    class MergeReducer extends CountedCompleter<Void> {

        private List<Integer> startPoints;
        private Boundary mainBorder;
        private char colour;
        private char sourceColour;

        private MergeReducer(CountedCompleter<Void> parent, List<Integer> startPoints, Boundary mainBorder, char sourceColour, char colour) {
            super(parent);
            this.startPoints = startPoints;
            this.mainBorder = mainBorder;
            this.colour = colour;
            this.sourceColour = sourceColour;
        }

        private Boundary getBlockBorder(int x, int y) {
            int minWidth = x / blockSizeW;
            int minHeight = y / blockSizeH;
            return new Boundary(minWidth, Math.min(minWidth + blockSizeW, mainBorder.maxWidth), minHeight, Math.max(minHeight + blockSizeH, mainBorder.maxHeight));
        }

        @Override
        public void compute() {
            int value = startPoints.get(0);
            int y = value / mainBorder.maxWidth;
            int x = value - y * mainBorder.maxWidth;

            Boundary boundary = getBlockBorder(x, y);
            BorderPoints borderPoints;
            try {
                tryLock(boundary);
                borderPoints = fillArea.fill(startPoints, boundary, sourceColour, colour);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                releaseLock(boundary);
            }

            if (borderPoints.bottom.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.bottom, mainBorder, sourceColour, colour);
                mapReducer.fork();
            }

            if (borderPoints.top.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.top, mainBorder, sourceColour, colour);
                mapReducer.fork();
            }

            if (borderPoints.left.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.left, mainBorder, sourceColour, colour);
                mapReducer.fork();
            }

            if (borderPoints.right.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.right, mainBorder, sourceColour, colour);
                mapReducer.fork();
            }

            tryComplete();
        }
    }
}

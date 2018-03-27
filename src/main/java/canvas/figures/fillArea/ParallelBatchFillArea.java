package canvas.figures.fillArea;

import canvas.Boundary;
import canvas.Model;
import canvas.viewer.IView;

import java.util.List;
import java.util.concurrent.*;

public class ParallelBatchFillArea implements IFillArea {

    private Model model;
    private IView view;
    private int blockSizeW;
    private int blockSizeH;
    private int threadCount;
    private IBatchFillArea fillArea;
    private ConcurrentHashMap<Boundary, Boundary> lockedBoundary;

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
    public void fill(int x, int y, char colour) {
        if (model.get(x, y) != colour) {
            try {

                MergeReducer mergeReducer = new MergeReducer(null, List.of(new CoordinatesTypeEntry(x, y)), model.get(x, y), colour, Move.FIRST);
                new ForkJoinPool(threadCount).invoke(mergeReducer);
                mergeReducer.get();
            } catch (InterruptedException | ExecutionException e) {
                view.showError(e);
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

    private enum Move {
        TOP, BOTTOM, RIGHT, LEFT, FIRST
    }

    class MergeReducer extends CountedCompleter<Void> {

        private List<CoordinatesTypeEntry> startPoints;
        private char colour;
        private char sourceColour;
        private Move move;

        private MergeReducer(CountedCompleter<Void> parent, List<CoordinatesTypeEntry> startPoints, char sourceColour, char colour, Move move) {
            super(parent);
            this.startPoints = startPoints;
            this.colour = colour;
            this.sourceColour = sourceColour;
            this.move = move;
        }

        private Boundary getBlockBorder(int x, int y) {
            int minWidth = (x - 1) / blockSizeW;
            int minHeight = (y - 1) / blockSizeH;
            Boundary mainBorder = model.getBoundary();
            return new Boundary(minWidth * blockSizeW + 1,
                    Math.min((minWidth + 1) * blockSizeW, mainBorder.getMaxWidth()),
                    minHeight * blockSizeH + 1,
                    Math.min((minHeight + 1) * blockSizeH, mainBorder.getMaxHeight()));
        }

        private BorderPoints fillArea() {
            CoordinatesTypeEntry value = startPoints.get(0);
            Boundary boundary = getBlockBorder(value.x, value.y);
            try {
                tryLock(boundary);
                BorderPoints borderPoints = fillArea.fill(startPoints, boundary, sourceColour, colour);
                reduceBorderPoints(borderPoints);
                return borderPoints;
            } catch (InterruptedException e) {
                view.showError(e);
                throw new RuntimeException(e);
            } finally {
                releaseLock(boundary);
            }
        }

        private void reduceBorderPoints(BorderPoints borderPoints) {
            MergeReducer parent = (MergeReducer) this.getCompleter();
            Boundary mainBorder = model.getBoundary();

            if (Move.BOTTOM.equals(parent.move)) {
                for(CoordinatesTypeEntry entry : startPoints){
                    entry.y = entry.y - mainBorder.getMaxHeight();
                    borderPoints.top.remove(entry);
                }
            }

            if (Move.TOP.equals(parent.move)) {
                for(CoordinatesTypeEntry entry : startPoints){
                    entry.y = entry.y + mainBorder.getMaxHeight();
                    borderPoints.bottom.remove(entry);
                }
            }

            if (Move.LEFT.equals(parent.move)) {
                for(CoordinatesTypeEntry entry : startPoints){
                    entry.x = entry.x - mainBorder.getMaxWidth();
                    borderPoints.left.remove(entry);
                }
            }

            if (Move.RIGHT.equals(parent.move)) {
                for(CoordinatesTypeEntry entry : startPoints){
                    entry.y = entry.x + mainBorder.getMaxWidth();
                    borderPoints.right.remove(entry);
                }
            }
        }

        @Override
        public void compute() {
            BorderPoints borderPoints = fillArea();

            if (borderPoints.bottom.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.bottom, sourceColour, colour, Move.BOTTOM);
                mapReducer.fork();
            }

            if (borderPoints.top.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.top, sourceColour, colour, Move.TOP);
                mapReducer.fork();
            }

            if (borderPoints.left.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.left, sourceColour, colour, Move.LEFT);
                mapReducer.fork();
            }

            if (borderPoints.right.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.right, sourceColour, colour, Move.RIGHT);
                mapReducer.fork();
            }

            tryComplete();
        }
    }
}

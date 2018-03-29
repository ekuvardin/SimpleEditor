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
                MergeReducer mergeReducer = new MergeReducer(null, List.of(new CoordinatesTypeEntry(x, y)), model.get(x, y), colour, Move.NONE);
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
        TOP, BOTTOM, RIGHT, LEFT, NONE
    }

    class MergeReducer extends CountedCompleter<Void> {

        private final List<CoordinatesTypeEntry> startPoints;
        private final char colour;
        private final char sourceColour;
        private final Move parentDirection;

        private MergeReducer(CountedCompleter<Void> parent, List<CoordinatesTypeEntry> startPoints, char sourceColour, char colour, Move parentDirection) {
            super(parent);
            this.startPoints = startPoints;
            this.colour = colour;
            this.sourceColour = sourceColour;
            this.parentDirection = parentDirection;
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
            switch (this.parentDirection) {
                case BOTTOM:
                    for (CoordinatesTypeEntry entry : startPoints) {
                        entry.y++;
                        borderPoints.bottom.remove(entry);
                    }
                    break;
                case TOP:
                    for (CoordinatesTypeEntry entry : startPoints) {
                        entry.y--;
                        borderPoints.top.remove(entry);
                    }
                    break;
                case LEFT:
                    for (CoordinatesTypeEntry entry : startPoints) {
                        entry.x--;
                        borderPoints.left.remove(entry);
                    }
                    break;
                case RIGHT:
                    for (CoordinatesTypeEntry entry : startPoints) {
                        entry.x++;
                        borderPoints.right.remove(entry);
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void compute() {
            BorderPoints borderPoints = fillArea();

            if (borderPoints.bottom.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.bottom, sourceColour, colour, Move.TOP);
                mapReducer.fork();
            }

            if (borderPoints.top.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.top, sourceColour, colour, Move.BOTTOM);
                mapReducer.fork();
            }

            if (borderPoints.left.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.left, sourceColour, colour, Move.RIGHT);
                mapReducer.fork();
            }

            if (borderPoints.right.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.right, sourceColour, colour, Move.LEFT);
                mapReducer.fork();
            }

            tryComplete();
        }
    }
}

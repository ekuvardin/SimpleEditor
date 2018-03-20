package canvas.Figures.FillArea;

import canvas.Model;
import canvas.Viewer.IView;

import java.util.List;
import java.util.concurrent.*;

public class ParallelBatchFillArea implements IFillArea {

    protected Model model;
    protected IView view;
    protected int blockSizeW;
    protected int blockSizeH;
    protected int threadCount;
    protected IBatchFillArea fillArea;


    public ParallelBatchFillArea(IView view, Model model, int threadCount, int blockSizeW, int blockSizeH, IBatchFillArea fillArea) {
        this.view = view;
        this.threadCount = threadCount;
        this.blockSizeW = blockSizeW;
        this.blockSizeH = blockSizeH;
        this.fillArea = fillArea;
        this.model = model;
    }

    @Override
    public void fill(int x, int y, Boundary boundary, char colour) {
        try {
            MergeReducer mergeReducer = new MergeReducer(null, List.of(y * (boundary.maxWidth - boundary.minWidth) + x), boundary, colour);
            new ForkJoinPool(threadCount).invoke(mergeReducer);
            mergeReducer.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    class MergeReducer extends CountedCompleter<Void> {

        private List<Integer> startPoints;
        private Boundary mainBorder;
        private char colour;

        private MergeReducer(CountedCompleter<Void> parent, List<Integer> startPoints, Boundary mainBorder, char colour) {
            super(parent);
            this.startPoints = startPoints;
            this.mainBorder = mainBorder;
            this.colour = colour;
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

            BorderPoints borderPoints = fillArea.fill(x, y, getBlockBorder(x, y), colour);

            if (borderPoints.bottom.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.bottom, mainBorder, colour);
                mapReducer.fork();
            }

            if (borderPoints.top.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.top, mainBorder, colour);
                mapReducer.fork();
            }

            if (borderPoints.left.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.left, mainBorder, colour);
                mapReducer.fork();
            }

            if (borderPoints.right.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.right, mainBorder, colour);
                mapReducer.fork();
            }

            tryComplete();
        }
    }
}

package canvas.Figures.FillArea;

import canvas.Model;
import canvas.Viewer.IView;

import java.io.IOException;
import java.util.ArrayList;
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
    public void fill(int x, int y, int minWidth, int maxWidth, int minHeight, int maxHeight, char colour) {
        try {
            MergeReducer mergeReducer = new MergeReducer(null, List.of(y * (maxWidth - minWidth) + x));
            new ForkJoinPool(threadCount).invoke(mergeReducer);
            mergeReducer.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Bound getBlockBorder(Integer value, Bound mainBorder){
        int y = value/(mainBorder.maxHeight - mainBorder.minHeight);
        int x = value - y*(mainBorder.maxHeight - mainBorder.minHeight);

        int widthDelta = x/blockSizeW;
        return new Bound();
    }

    class MergeReducer extends CountedCompleter<BorderPoints> {

        private BorderPoints result;
        private List<Integer> startPoints;

        private MergeReducer(CountedCompleter<BorderPoints> parent, List<Integer> startPoints) {
            super(parent);
            this.startPoints = startPoints;
        }

        @Override
        public BorderPoints getRawResult() {
            return result;
        }

        @Override
        public void compute() {
            BorderPoints borderPoints = fillArea.fill();

            if (borderPoints.bottom.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.bottom);
                mapReducer.fork();
            }

            if (borderPoints.top.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.top);
                mapReducer.fork();
            }

            if (borderPoints.left.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.left);
                mapReducer.fork();
            }

            if (borderPoints.right.size() != 0) {
                this.addToPendingCount(1);
                MergeReducer mapReducer = new MergeReducer(this, borderPoints.right);
                mapReducer.fork();
            }


            tryComplete();
        }
    }


}

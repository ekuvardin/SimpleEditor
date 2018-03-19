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

    @Override
    public void fill(int x, int y, int minWidth, int maxWidth, int minHeight, int maxHeight, char colour) {

    }

    public ParallelBatchFillArea(IView view, Model model, int threadCount, int blockSizeW, int blockSizeH, IBatchFillArea fillArea) {
        this.view = view;
        this.threadCount = threadCount;
        this.blockSizeW = blockSizeW;
        this.blockSizeH = blockSizeH;
        this.fillArea = fillArea;
    }

    class MergeReducer extends CountedCompleter<BorderPoints> {

        private List<MergeReducer> forks;
        private BorderPoints result;
        private List<Integer> startPoints;

        private MergeReducer(CountedCompleter<String> parent, List<Integer> startPoints) {
            super(parent);
            this.startPoints = startPoints;
            this.forks = new ArrayList<>(maxChunkInTask);
        }

        @Override
        public BorderPoints getRawResult() {
            return result;
        }

        @Override
        public void compute() {
            BorderPoints borderPoints = fillArea.fill();

            if()
            final int size = fileNames.size();
            if (size <= maxFileInTask) {
                result = execMerge(fileNames, outputFileName);
            } else {
                int delta = size / maxFileInTask;
                if (delta <= maxFileInTask) {
                    delta = maxFileInTask;
                }

                for (int newSize = delta; newSize < size; newSize = newSize + delta) {
                    addTask(fileNames.subList(newSize - delta, newSize));
                }

                int rem = size % maxFileInTask;
                // If we have only one file then we can't do anything.
                if (rem != 1) {
                    addTask(rem == 0 ? fileNames.subList(size - delta, size) : fileNames.subList(size - rem, size));
                } else {
                    result = fileNames.get(size - 1);
                    forks.add(this);
                }
            }

            tryComplete();
        }

        private void addTask(List<String> fileTmp) {
            this.addToPendingCount(1);
            MergeReducer mapReducer = new MergeReducer(this, fileTmp, maxFileInTask, outputFileName);
            mapReducer.fork();
            forks.add(mapReducer);
        }

        private String execMerge(List<String> fileNames, String outputFileName) {
            try {
                return kWayMerge(fileNames, FileNamesHolder.getNewUniqueName(outputFileName));
            } catch (IOException e) {
                e.printStackTrace();
                this.cancel(true);
                return null;
            }
        }
    }


}

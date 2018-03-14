package canvas.Figures.FillArea;

import canvas.Model;
import canvas.Viewer.IView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ParallelBatchFillArea extends SingleThreadFillArea {

    protected Model model;
    protected IView view;
    protected int blockSizeW;
    protected int blockSizeH;
    int threadCount;

    private static class BorderPoints {
        List<Integer> left = new ArrayList<Integer>();
        List<Integer> right = new ArrayList<Integer>();
        List<Integer> bottom = new ArrayList<Integer>();
        List<Integer> top = new ArrayList<Integer>();
    }

    public ParallelBatchFillArea(IView view, Model model, int threadCount, int blockSizeW, int blockSizeH) {
        super(model);
        this.view = view;
        this.threadCount = threadCount;
        this.blockSizeW = blockSizeW;
        this.blockSizeH = blockSizeH;
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
            fill();

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

    @Override
    protected CoordinatesTypeEntry getAny(List<CoordinatesTypeEntry> needToBeChecked, Bound bound) {
        CoordinatesTypeEntry value = needToBeChecked.remove(needToBeChecked.size() - 1);

        if (value.x == bound.minWidth) {
            borderPoints.get().add((value.y - 1) * model.getWidth() + value.x);
        }
        if (value.x == bound.maxWidth) {
            borderPoints.get().add((value.y + 1) * model.getWidth() + value.x);
        }
        if (value.x == bound.minHeight) {
            borderPoints.get().add(value.y * model.getWidth() + value.x - 1);
        }
        if (value.x == bound.maxHeight) {
            borderPoints.get().add(value.y * model.getWidth() + value.x + 1);
        }
        return value;
    }

}

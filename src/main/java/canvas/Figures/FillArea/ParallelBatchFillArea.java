package canvas.Figures.FillArea;

import canvas.Model;

import java.util.List;

public class ParallelBatchFillArea extends SingleThreadFillArea {

    protected Model model;
    protected ThreadLocal<CoordinatesTypeEntry> borderPoints;

    public ParallelBatchFillArea(Model model) {
        super(model);
    }

    @Override
    public void fill(int x, int y, int minWidth, int maxWidth, int minHeight, int maxHeight, char colour) {

    }

    @Override
    protected CoordinatesTypeEntry getAny(List<CoordinatesTypeEntry> needToBeChecked ){
        CoordinatesTypeEntry value = needToBeChecked.remove(needToBeChecked.size() - 1);
        if(value.x = minWidth || value.x == maxWidth){

        }
        return value;
    }

}

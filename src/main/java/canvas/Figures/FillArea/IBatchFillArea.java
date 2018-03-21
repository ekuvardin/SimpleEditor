package canvas.Figures.FillArea;

import java.util.List;

public interface IBatchFillArea {

    BorderPoints fill(List<Integer> startPoints, Boundary boundary, char colour);
}

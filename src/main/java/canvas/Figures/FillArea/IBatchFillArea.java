package canvas.Figures.FillArea;

import java.util.List;

public interface IBatchFillArea {

    BorderPoints fill(List<CoordinatesTypeEntry> startPoints, Boundary boundary, Boundary mainBorder, char sourceColour, char colour);
}

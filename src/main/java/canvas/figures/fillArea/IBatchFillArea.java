package canvas.figures.fillArea;

import canvas.Boundary;
import canvas.figures.fillArea.BorderPoints;
import canvas.figures.fillArea.CoordinatesTypeEntry;

import java.util.List;

public interface IBatchFillArea {

    BorderPoints fill(List<CoordinatesTypeEntry> startPoints, Boundary boundary, char sourceColour, char colour);
}

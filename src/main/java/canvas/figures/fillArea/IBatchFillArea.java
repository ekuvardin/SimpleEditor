package canvas.figures.fillArea;

import canvas.Boundary;
import canvas.figures.fillArea.BorderPoints;
import canvas.figures.fillArea.CoordinatesTypeEntry;

import java.util.List;

/**
 * Fill small area
 */
public interface IBatchFillArea {

    /**
     * Filling area
     *
     * @param startPoints  first points to fill
     * @param boundary     block boundary
     * @param sourceColour source colour
     * @param colour       fill colour
     * @return Points need to checked in neighbors areas
     */
    BorderPoints fill(List<CoordinatesTypeEntry> startPoints, Boundary boundary, char sourceColour, char colour);
}

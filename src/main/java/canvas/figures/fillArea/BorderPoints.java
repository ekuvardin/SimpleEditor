package canvas.figures.fillArea;

import java.util.ArrayList;
import java.util.List;

/**
 * Points need to checked in neighbors areas
 */
class BorderPoints {

    List<CoordinatesTypeEntry> left = new ArrayList<>();
    List<CoordinatesTypeEntry> right = new ArrayList<>();
    List<CoordinatesTypeEntry> bottom = new ArrayList<>();
    List<CoordinatesTypeEntry> top = new ArrayList<>();

}

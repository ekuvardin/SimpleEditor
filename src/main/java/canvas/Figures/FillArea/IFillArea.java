package canvas.Figures.FillArea;

import canvas.Canvas;

/**
 * Fill area
 */
public interface IFillArea {

    /**
     * Fill area with specified colour
     * @param canvas model
     * @param x x
     * @param y y
     * @param colour colour
     * @param threadCount thread count to fill
     */
    void fill(Canvas canvas, int x, int y, char colour, int threadCount);

}

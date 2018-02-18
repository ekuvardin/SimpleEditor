package canvas.Figures.FillArea;

import canvas.Canvas;

public interface IFillArea {

    void fill(Canvas canvas, int x, int y, char colour, int threadCount);

}

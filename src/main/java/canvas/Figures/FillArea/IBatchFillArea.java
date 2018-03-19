package canvas.Figures.FillArea;

public interface IBatchFillArea {

    BorderPoints fill(int x, int y, int minWidth, int maxWidth, int minHeight, int maxHeight, char colour);
}

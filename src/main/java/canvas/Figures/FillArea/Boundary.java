package canvas.Figures.FillArea;

/**
 * Boundary area description
 */
public class Boundary {
    protected int minWidth, maxWidth, minHeight, maxHeight;

    public Boundary(int minWidth, int maxWidth, int minHeight, int maxHeight) {
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    /**
     * Check that provided values within bounds
     *
     * @param width  width
     * @param height height
     * @return true - provided values within bounds, false - other
     */
    public boolean checkInBound(int width, int height) {
        return width >= minWidth && width < maxWidth && height >= minHeight && height < maxHeight;
    }
}

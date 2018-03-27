package canvas;

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
        return width >= minWidth && width <= maxWidth && height >= minHeight && height <= maxHeight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Boundary boundary = (Boundary) o;

        if (minWidth != boundary.minWidth) return false;
        if (maxWidth != boundary.maxWidth) return false;
        if (minHeight != boundary.minHeight) return false;
        return maxHeight == boundary.maxHeight;
    }

    @Override
    public int hashCode() {
        int result = minWidth;
        result = 31 * result + maxWidth;
        result = 31 * result + minHeight;
        result = 31 * result + maxHeight;
        return result;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }
}

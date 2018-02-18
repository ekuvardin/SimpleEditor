package canvas;

/**
 * Describe model where points are stored
 */
public abstract class Canvas {

    /**
     * Only one canvas can be available at time
     */
    protected Canvas() {
        currentCanvas = this;
    }

    /**
     * Set new colour to coordinates
     *
     * @param x     X-axis
     * @param y     Y-axis
     * @param value colour
     */
    public abstract void set(int x, int y, char value);

    /**
     * Get colour by coordinates
     *
     * @param x X-axis
     * @param y Y-axis
     * @return colour
     */
    public abstract char get(int x, int y);

    /**
     * Get x length
     * @return x length
     */
    public abstract int getxLength();

    /**
     * Get y length
     * @return y length
     */
    public abstract int getyLength();

    private static Canvas currentCanvas;

    /**
     *  Get current canvas
     * @return current canvas
     */
    public final static Canvas getCurrentCanvas() {
        return currentCanvas;
    }

    /**
     * Is canvas can be access in parallel
     * @return true - can be access parallel, false - other
     */
    public abstract boolean canBeParallelUsed();
}

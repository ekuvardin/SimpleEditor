package canvas;

/**
 * Describe model where points are stored
 */
public abstract class Model {

    /**
     * Only one canvas can be available at time
     */
    protected Model() {
        currentModel = this;
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
    public abstract int getWidth();

    /**
     * Get y length
     * @return y length
     */
    public abstract int getHeight();

    private static Model currentModel;

    /**
     *  Get current canvas
     * @return current canvas
     */
    public final static Model getCurrentModel() {
        return currentModel;
    }

    public abstract Boundary getBoundary();
}

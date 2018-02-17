package canvas;

public abstract class Canvas {

    protected Canvas() {
        currentCanvas = this;
    }

    public abstract void set(int x, int y, char value);

    public abstract char get(int x, int y);

    public abstract int getxLength();

    public abstract int getyLength();

    private static Canvas currentCanvas;

    public final static Canvas getCurrentCanvas() {
        return currentCanvas;
    }
}

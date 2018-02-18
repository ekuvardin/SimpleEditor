package comands;

import canvas.Canvas;
import canvas.Viewer.IView;
import canvas.Writers.FillArea.SingleThreadFillArea;

public class FillAreaCommand implements ICommand {

    protected int x;
    protected int y;
    protected char colour;

    public FillAreaCommand(int x, int y, char colour) {
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    @Override
    public void execute(IView view) {
        SingleThreadFillArea singleThreadFillArea = new SingleThreadFillArea();
        singleThreadFillArea.fill(Canvas.getCurrentCanvas(), x, y, colour, 1);

        view.draw(Canvas.getCurrentCanvas());
    }

    @Override
    public boolean quitCommand() {
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getColour() {
        return colour;
    }
}

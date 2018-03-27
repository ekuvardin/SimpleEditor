package comands;

import canvas.figures.fillArea.IFillArea;
import canvas.Model;
import canvas.viewer.IView;
import canvas.figures.fillArea.SingleThreadFillArea;

/*
  Fill area in single thread
 */
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
        Model model = Model.getCurrentModel();
        IFillArea fillArea = new SingleThreadFillArea(Model.getCurrentModel());
        fillArea.fill(x, y, colour);

        view.draw(Model.getCurrentModel());
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

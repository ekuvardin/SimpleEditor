package comands;

import canvas.Figures.FillArea.Boundary;
import canvas.Figures.FillArea.IFillArea;
import canvas.Model;
import canvas.Viewer.IView;
import canvas.Figures.FillArea.SingleThreadFillArea;

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
        fillArea.fill(x, y, new Boundary(0,model.getWidth(), 0, model.getHeight()), colour);

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

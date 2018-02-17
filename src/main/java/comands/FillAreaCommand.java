package comands;

import canvas.SimpleCanvas;
import canvas.Viewer.IView;
import canvas.Writers.FillArea.SingleThreadFill;

public class FillAreaCommand implements ICommand {

    private int x;
    private int y;
    private char colour;

    public FillAreaCommand(int x, int y, char colour) {
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    @Override
    public void execute(IView view) {
        SingleThreadFill f = new SingleThreadFill();
        f.fill(SimpleCanvas.getCurrentCanvas(), x, y, colour, 1);

        view.draw(SimpleCanvas.getCurrentCanvas());
    }
}

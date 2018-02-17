package comands;

import canvas.Canvas;
import canvas.Viewer.ConsoleViewer;
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
    public void execute() {
        SingleThreadFill f = new SingleThreadFill();
        f.fill(Canvas.getCurrentCanvas(), x, y, colour, 1);

        ConsoleViewer consoleViewer = new ConsoleViewer();
        consoleViewer.draw(Canvas.getCurrentCanvas());
    }
}

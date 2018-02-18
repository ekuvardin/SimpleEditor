package comands;

import canvas.Canvas;
import canvas.Viewer.IView;
import canvas.Writers.Lines.WriteLines;

public class CreateLineCommand implements ICommand {

    private int x1, y1, x2, y2;
    private final char colour = 'x';

    public CreateLineCommand(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void execute(IView view) {
        Canvas canvas = Canvas.getCurrentCanvas();
        if (canvas == null) {
            System.out.println("Please, create simpleCanvas first. For example: C 20 4");
            return;
        }

        WriteLines.writeLine(canvas, x1, y1, x2, y2, colour);
        view.draw(canvas);
    }

    @Override
    public boolean quitCommand() {
        return false;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }
}

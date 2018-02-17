package comands;

import canvas.Canvas;
import canvas.SimpleCanvas;
import canvas.Viewer.IView;
import canvas.Writers.Writer;

public class CreateRectangleCommand implements ICommand {

    private int x1, y1, x2, y2;
    private final char colour = 'x';

    public CreateRectangleCommand(int x1, int y1, int x2, int y2) {
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

        Writer.writeRectangle(canvas, x1, y1, x2, y2, colour);
        view.draw(canvas);
    }
}

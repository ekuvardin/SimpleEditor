package comands;

import canvas.Canvas;
import canvas.Viewer.ConsoleViewer;
import canvas.Viewer.IView;

public class CreateCanvasCommand implements ICommand {

    private int weight;
    private int height;

    public CreateCanvasCommand(int weight, int height) {
        this.weight = weight;
        this.height = height;
    }

    @Override
    public void execute(IView view) {
        Canvas canvas = new Canvas(this.weight, this.height);
        view.draw(canvas);
    }
}

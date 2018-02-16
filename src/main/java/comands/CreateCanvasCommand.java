package comands;

import canvas.Canvas;
import canvas.Viewer.ConsoleViewer;

public class CreateCanvasCommand implements ICommand {

    private int weight;
    private int height;

    public CreateCanvasCommand(int weight, int height) {
        this.weight = weight;
        this.height = height;
    }

    @Override
    public void execute() {
        Canvas canvas = new Canvas(this.weight, this.height);
        ConsoleViewer view = new ConsoleViewer();
        view.draw(canvas);
    }
}

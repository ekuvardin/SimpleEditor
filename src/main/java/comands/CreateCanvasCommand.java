package comands;

import canvas.SimpleCanvas;
import canvas.Viewer.IView;

public class CreateCanvasCommand implements ICommand {

    protected int weight;
    protected int height;

    public CreateCanvasCommand(int weight, int height) {
        this.weight = weight;
        this.height = height;
    }

    @Override
    public void execute(IView view) {
        SimpleCanvas simpleCanvas = new SimpleCanvas(this.weight, this.height);
        view.draw(simpleCanvas);
    }
}

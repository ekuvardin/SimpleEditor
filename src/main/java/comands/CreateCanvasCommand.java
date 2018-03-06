package comands;

import canvas.SimpleModel;
import canvas.Viewer.IView;

/**
 * Create simple canvas
 */
public class CreateCanvasCommand implements ICommand {

    protected int width;
    protected int height;

    public CreateCanvasCommand(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void execute(IView view) {
        SimpleModel simpleCanvas = new SimpleModel(this.width, this.height);
        view.draw(simpleCanvas);
    }

    @Override
    public boolean quitCommand() {
        return false;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

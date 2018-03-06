package comands;

import canvas.ConcurrentModel;
import canvas.Viewer.IView;

/*
  Create canvas which can be used due parallel filling
 */
public class CreateConcurrentCanvasCommand extends CreateCanvasCommand {

    public CreateConcurrentCanvasCommand(int weight, int height) {
        super(weight, height);
    }

    @Override
    public void execute(IView view) {
        ConcurrentModel canvas = new ConcurrentModel(this.width, this.height);
        view.draw(canvas);
    }
}

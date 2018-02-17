package comands;

import canvas.Canvas;
import canvas.Viewer.IView;
import canvas.Writers.FillArea.ConcurrentFillArea;

public class FillConcurrentAreaCommand extends FillAreaCommand {

    protected int threadCount;

    public FillConcurrentAreaCommand(int x, int y, char colour, int threadCount) {
        super(x, y, colour);
        this.threadCount = threadCount;
    }

    @Override
    public void execute(IView view) {
        Canvas canvas = Canvas.getCurrentCanvas();
        if(canvas.canBeParallelUsed()) {
            ConcurrentFillArea f = new ConcurrentFillArea();
            f.fill(Canvas.getCurrentCanvas(), x, y, colour, threadCount);

            view.draw(Canvas.getCurrentCanvas());
        } else {
            view.printMessage("Warning. Created not parallel canvas. Will be used single thread implementation");
            super.execute(view);
        }
    }
}

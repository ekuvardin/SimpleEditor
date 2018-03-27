package comands;

import canvas.figures.fillArea.BatchSingleThreadFillArea;
import canvas.figures.fillArea.IFillArea;
import canvas.figures.fillArea.ParallelBatchFillArea;
import canvas.Model;
import canvas.viewer.IView;

/*
  Fill area with several threads
 */
public class FillParallelAreaCommand extends FillAreaCommand {

    protected int threadCount;
    protected int widthBatchSize;
    protected int heightBatchSize;

    public FillParallelAreaCommand(int x, int y, char colour, int threadCount, int widthBatchSize, int heightBatchSize) {
        super(x, y, colour);
        this.threadCount = threadCount;
        this.widthBatchSize = widthBatchSize;
        this.heightBatchSize = heightBatchSize;
    }

    @Override
    public void execute(IView view) {
        Model model = Model.getCurrentModel();
        IFillArea fillArea = new ParallelBatchFillArea(view, model, threadCount, widthBatchSize, heightBatchSize, new BatchSingleThreadFillArea(model));
        fillArea.fill(x, y, colour);
        view.draw(model);
    }

    public int getThreadCount() {
        return threadCount;
    }
}

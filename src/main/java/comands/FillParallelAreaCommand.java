package comands;

import canvas.Figures.FillArea.BatchSingleThreadFillArea;
import canvas.Figures.FillArea.Boundary;
import canvas.Figures.FillArea.IFillArea;
import canvas.Figures.FillArea.ParallelBatchFillArea;
import canvas.Model;
import canvas.Viewer.IView;

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
        fillArea.fill(x, y, new Boundary(1, model.getWidth(), 1, model.getHeight()), colour);
        view.draw(model);
    }

    public int getThreadCount() {
        return threadCount;
    }
}

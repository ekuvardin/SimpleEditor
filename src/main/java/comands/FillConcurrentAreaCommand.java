package comands;

import canvas.Model;
import canvas.Viewer.IView;
import canvas.Figures.FillArea.ConcurrentFillArea;

/*
  Fill area with several threads
 */
public class FillConcurrentAreaCommand extends FillAreaCommand {

    protected int threadCount;

    public FillConcurrentAreaCommand(int x, int y, char colour, int threadCount) {
        super(x, y, colour);
        this.threadCount = threadCount;
    }

    @Override
    public void execute(IView view) {
        Model model = Model.getCurrentModel();
        if(model.canBeParallelUsed()) {
            ConcurrentFillArea.fill(Model.getCurrentModel(), x, y, colour, threadCount);

            view.draw(Model.getCurrentModel());
        } else {
            view.printMessage("Warning. Created not parallel model. Will be used single thread implementation");
            super.execute(view);
        }
    }

    public int getThreadCount() {
        return threadCount;
    }
}

package comands;

import canvas.Figures.FillArea.Boundary;
import canvas.Figures.FillArea.IFillArea;
import canvas.Figures.FillArea.SingleThreadFillArea;
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
            IFillArea fillArea = new ConcurrentFillArea(Model.getCurrentModel(), threadCount);
            fillArea.fill(x, y, new Boundary(0,model.getWidth(), 0, model.getHeight()), colour);

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

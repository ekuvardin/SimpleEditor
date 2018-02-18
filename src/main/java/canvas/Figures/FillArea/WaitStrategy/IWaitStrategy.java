package canvas.Figures.FillArea.WaitStrategy;

public interface IWaitStrategy {

    boolean canRun() throws InterruptedException;

    void trySpinWait() throws InterruptedException;

    void stop() throws InterruptedException;;
}

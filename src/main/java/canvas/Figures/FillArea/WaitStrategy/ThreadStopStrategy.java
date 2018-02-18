package canvas.Figures.FillArea.WaitStrategy;

public class ThreadStopStrategy implements IWaitStrategy {

    private volatile boolean stopTolerant = false;

    @Override
    public boolean canRun() throws InterruptedException {
        return !Thread.currentThread().isInterrupted() && !stopTolerant;
    }

    @Override
    public void trySpinWait() throws InterruptedException {
        Thread.yield();
    }

    @Override
    public void stop() throws InterruptedException {
        stopTolerant = true;
    }
}

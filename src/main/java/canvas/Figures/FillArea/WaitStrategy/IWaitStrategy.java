package canvas.Figures.FillArea.WaitStrategy;


/**
 * Describe strategy which will be used during handle thread execution
 */
public interface IWaitStrategy {

    /**
     * Can thread continue working
     * @return true - continue
     * @throws InterruptedException may thrown due thread termination
     */
    boolean canRun() throws InterruptedException;

    /**
     * Try smt do when work is not present
     * @throws InterruptedException may thrown due thread termination
     */
    void trySpinWait() throws InterruptedException;

    /**
     * Signal thread to stop execution
     * @throws InterruptedException may thrown due thread termination
     */
    void stop() throws InterruptedException;;
}

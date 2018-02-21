package FillArea;

import canvas.Figures.FillArea.WaitStrategy.IWaitStrategy;
import org.openjdk.jmh.infra.Control;

public class JmhWaitStrategy implements IWaitStrategy {

    private Control control;

    public void setControl(Control control) {
        this.control = control;
    }

    @Override
    public boolean canRun() throws InterruptedException {
       return !control.stopMeasurement;
    }

    @Override
    public void trySpinWait()  throws InterruptedException {
        if(!Thread.currentThread().isInterrupted()) {
            Thread.yield();
        }
    }

    @Override
    public void stop() throws InterruptedException {
        
    }
}
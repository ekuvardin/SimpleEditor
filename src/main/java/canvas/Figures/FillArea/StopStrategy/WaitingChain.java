package canvas.Figures.FillArea.StopStrategy;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicReference;

public class WaitingChain {

    private final AtomicReference<Entry> last = new AtomicReference<>();
    private final ThreadLocal<Entry> curValue = ThreadLocal.withInitial(() -> {
        Entry currentValue = new Entry();
        Entry curLast;
        do {
            curLast = last.get();
            currentValue.prev = curLast;
        } while (!last.compareAndSet(curLast, currentValue));
        return currentValue;
    });

    private static final class Entry extends WeakReference<Thread> {

        Entry prev;
        volatile boolean iterate;

        Entry() {
            super(Thread.currentThread());
        }
    }

    public void startIteration() {
        curValue.get().iterate = true;
    }

    public void endIteration() {
        curValue.get().iterate = false;
    }

    public void reset() {
        for (Entry e = last.get(); e != null; e = e.prev) {
            if (e.get() != null) {
                if (!e.iterate) {
                    e.iterate = true;
                }
            }
        }
    }

    public boolean isLimitSucceed(int minWaitThreads) {
        int localEnded = 0;
        for (Entry e = last.get(); e != null && minWaitThreads > localEnded; e = e.prev) {
            if (e.get() != null) {
                {
                    if (e.iterate) {
                        continue;
                    }
                }
            }
            localEnded++;
        }
        return minWaitThreads <= localEnded;
    }
}

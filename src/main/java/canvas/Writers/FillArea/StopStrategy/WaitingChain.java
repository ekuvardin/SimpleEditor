package canvas.Writers.FillArea.StopStrategy;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicReference;

public class WaitingChain {

    private final int minWaitThreads;
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

    public WaitingChain(int minWaitThreads) {
        this.minWaitThreads = minWaitThreads;
    }

    private static final class Entry extends WeakReference<Thread> {

        Entry prev;
        volatile boolean used;

        Entry() {
            super(Thread.currentThread());
        }
    }

    public void enter() {
        curValue.get().used = true;
    }

    public void exit() {
        curValue.get().used = false;
    }

    public boolean isLimitSucceed() {
        retry:
        while (true) {
            // remember 1st
            Entry f = last.get();
            Entry d = null;
            int localEnded = 0;
            for (Entry e = f; e != null && minWaitThreads > localEnded; e = e.prev) {
                if (e.get() == null) {
                    // entry is cleared, thread is gone -> expunge
                    if (d == null) {
                       /* if (last.compareAndSet(f, e.prev)) {
                            f = e.prev;*/
                        if (last.get() != f) {
                            // last changed concurrently -> retry loop
                            continue retry;
                        }
                    } else {
                        d.prev = e.prev;
                    }
                } else {
                    if (e.used) {
                        return false;
                    }
                    d = e;
                }
                localEnded++;
            }
            return minWaitThreads >= localEnded;
        }
    }
}

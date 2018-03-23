package canvas.Figures.FillArea.Stores;

import canvas.Figures.FillArea.Boundary;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WaitedEntry<T> {

    final T entry;
    final ReentrantLock currentLock;
    final Condition notFull;

    public WaitedEntry(T entry){
        currentLock = new ReentrantLock();
        notFull = currentLock.newCondition();
        this.entry = entry;
    }

    public void tryWait() throws InterruptedException {
        try{
            currentLock.tryLock();
            notFull.wait();
        } finally {
            currentLock.unlock();
        }
    }

    public void release(){
        try{
            currentLock.tryLock();
            notFull.signalAll();
        } finally {
            currentLock.unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WaitedEntry<?> that = (WaitedEntry<?>) o;

        return entry != null ? entry.equals(that.entry) : that.entry == null;
    }

    @Override
    public int hashCode() {
        return entry != null ? entry.hashCode() : 0;
    }
}

package canvas.Figures.FillArea.Stores;

import canvas.Figures.FillArea.Boundary;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WaitedEntry<T> {

    T entry;
    ReentrantLock currentLock;
    Condition notFull;

    public WaitedEntry(){
        currentLock = new ReentrantLock();
        notFull = currentLock.newCondition();
    }

    public void tryPut(){
        try{
            currentLock.tryLock();
            Set<Boundary> boundarySet = ConcurrentHashMap.newKeySet();
            boundarySet.add()
        } finally {
            currentLock.unlock();
        }
    }
}

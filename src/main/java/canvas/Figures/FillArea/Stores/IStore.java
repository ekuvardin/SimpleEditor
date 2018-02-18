package canvas.Figures.FillArea.Stores;

public interface IStore<T> {

    /**
     * Get item until interrupted
     *
     * @return item
     */
    T get() throws InterruptedException;

    /**
     * Put item until interrupted
     *
     * @param item putted item
     */
    void put(T item) throws InterruptedException;

    /**
     * Is empty
     *
     * @return is store empty
     */
    boolean IsEmpty();

    /**
     * Clear store
     */
    void clear() throws InterruptedException;
}

package canvas.Writers.FillArea;

public interface IStore<T> {

    /**
     * Get item
     *
     * @return item
     */
    T get() throws InterruptedException;

    /**
     * Put item
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

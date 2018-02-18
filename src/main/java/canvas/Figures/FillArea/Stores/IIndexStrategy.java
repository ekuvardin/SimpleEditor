package canvas.Figures.FillArea.Stores;

/**
 * Strategy to access array elements
 */
@FunctionalInterface
public interface IIndexStrategy {
    int getIndex(int p1);
}
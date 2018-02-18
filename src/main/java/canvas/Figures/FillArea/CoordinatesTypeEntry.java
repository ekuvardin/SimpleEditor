package canvas.Figures.FillArea;

/**
 * Describe point to handling + in which direction it has already been handled
 */
public class CoordinatesTypeEntry extends CoordinatesEntry {

    TypeOfFilling typeOfFilling;

    public CoordinatesTypeEntry(int x, int y) {
        super(x, y);
        typeOfFilling = TypeOfFilling.AllDirections;
    }

    CoordinatesTypeEntry(int x, int y, TypeOfFilling typeOfFilling) {
        super(x, y);
        this.typeOfFilling = typeOfFilling;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoordinatesTypeEntry coordinatesTypeEntry = (CoordinatesTypeEntry) o;

        if (x != coordinatesTypeEntry.x) return false;
        return y == coordinatesTypeEntry.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}

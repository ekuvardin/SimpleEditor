package canvas.Writers.FillArea;

class CoordinatesEntry {
    int x;
    int y;

    TypeOfFilling typeOfFilling;

    CoordinatesEntry(int x, int y) {
        this.x = x;
        this.y = y;
        typeOfFilling = TypeOfFilling.AllDirections;
    }

    CoordinatesEntry(int x, int y, TypeOfFilling typeOfFilling) {
        this.x = x;
        this.y = y;
        this.typeOfFilling = typeOfFilling;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CoordinatesEntry coordinatesEntry = (CoordinatesEntry) o;

        if (x != coordinatesEntry.x) return false;
        return y == coordinatesEntry.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}

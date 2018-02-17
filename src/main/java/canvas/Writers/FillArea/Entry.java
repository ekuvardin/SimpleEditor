package canvas.Writers.FillArea;

class Entry implements Comparable<Entry> {
    int x;
    int y;

    TypeOfFilling typeOfFilling;   // -1 horizont, 0 -all directions, 1- vertical

    Entry(int x, int y) {
        this.x = x;
        this.y = y;
        typeOfFilling = TypeOfFilling.AllDirections;
    }

    Entry(int x, int y, TypeOfFilling typeOfFilling) {
        this.x = x;
        this.y = y;
        this.typeOfFilling = typeOfFilling;
    }

    @Override
    public int compareTo(Entry o) {
        if (this == o) return 0;

        if (this.x < o.x)
            return -1;
        if (this.x > o.x)
            return 1;

        if (this.y < o.y)
            return -1;
        if (this.y > o.y)
            return 1;

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (x != entry.x) return false;
        return y == entry.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}

package pl.edu.pg.eti.biocomp.models;

import java.util.Arrays;
import java.util.List;

public class Point {
    private final int x;
    private final int y;
    private final double value;

    public Point(int x, int y, double value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int[] positionAsArray() {
        return new int[]{x, y};
    }

    public List<Integer> positionAsList() {
        return Arrays.asList(x, y);
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ":" + value + "]";
    }
}

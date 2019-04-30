package com.github.luddwichr.trionimos;

import java.util.ArrayList;
import java.util.List;

public class Mesh {

    private final List<Triangle> triangles = new ArrayList<>();

    public void addFirstStone(Stone stone) {
        if (firstStonePlayed()) {
            throw new IllegalStateException("First stone was already added");
        }
        triangles.add(new Triangle(stone));
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public boolean firstStonePlayed() {
        return !triangles.isEmpty();
    }

}

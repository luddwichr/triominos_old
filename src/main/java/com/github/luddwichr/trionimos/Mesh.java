package com.github.luddwichr.trionimos;

import java.util.ArrayList;
import java.util.List;

public class Mesh {

    private final List<Triangle> triangles = new ArrayList<>();

    public void addStone(Stone stone) {
        triangles.add(new Triangle(stone));
    }

    public List<Triangle> getTriangles() {
        return triangles;
    }

    public boolean canAddStone(Stone stone) {
        for (Triangle triangle : triangles) {
            if (matchingEdges(triangle.getStone(), stone)) {
                return true;
            }
        }
        return triangles.isEmpty();
    }

    private boolean matchingEdges(Stone stoneA, Stone stoneB) {
        return false;
    }
}

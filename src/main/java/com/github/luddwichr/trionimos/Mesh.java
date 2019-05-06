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


    public void addStone(Stone stone, Triangle triangle, MatchingEdges matchingEdges) {
        if (!triangles.contains(triangle)) {
            throw new IllegalArgumentException();
        }

        validateStoneCanBePlaced(stone, triangle, matchingEdges);
        Triangle newTriangle = new Triangle(stone);
        triangle.setNeighbor(newTriangle, matchingEdges.getFirstEdge());
        newTriangle.setNeighbor(triangle, matchingEdges.getSecondEdge());

        triangles.add(newTriangle);
    }

    private void validateStoneCanBePlaced(Stone stone, Triangle triangle, MatchingEdges matchingEdges) {
        validateTriangleHasNoNeighbor(triangle, matchingEdges.getFirstEdge());
        Stone otherStone = triangle.getStone();
        if (matchingEdges == MatchingEdges.A_TO_A) {
            validateMatchingEdges(otherStone.a, otherStone.b, stone.b, stone.a);
        } else if (matchingEdges == MatchingEdges.A_TO_B) {
            validateMatchingEdges(otherStone.a, otherStone.b, stone.c, stone.b);
        } else if (matchingEdges == MatchingEdges.A_TO_C) {
            validateMatchingEdges(otherStone.a, otherStone.b, stone.a, stone.c);
        } else if (matchingEdges == MatchingEdges.B_TO_B) {
            validateMatchingEdges(otherStone.b, otherStone.c, stone.c, stone.b);
        } else if (matchingEdges == MatchingEdges.B_TO_C) {
            validateMatchingEdges(otherStone.b, otherStone.c, stone.a, stone.c);
        }  else if (matchingEdges == MatchingEdges.C_TO_C) {
            validateMatchingEdges(otherStone.a, otherStone.c, stone.c, stone.a);
        }
    }

    private void validateTriangleHasNoNeighbor(Triangle triangle, Edge edge) {
        if (triangle.getNeighbor(edge) != null) {
            throw new IllegalStateException();
        }
    }

    private void validateMatchingEdges(int x1, int y1, int x2, int y2) {
        if (x1 != x2 || y1 != y2) {
            throw new IllegalStateException();
        }
    }

}

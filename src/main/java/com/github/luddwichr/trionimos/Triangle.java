package com.github.luddwichr.trionimos;

/*
 *    C
 *   / \
 *  /   \
 * A-----B
 *
 */
public class Triangle {

    private final Stone stone;
    private Triangle neighborAB;
    private Triangle neighborBC;

    public Triangle(Stone stone) {
        this.stone = stone;
    }

    public Stone getStone() {
        return stone;
    }

    public void attach(Triangle otherTriangle, MatchingEdges matchingEdges) {
        if (matchingEdges.equals(MatchingEdges.AB_TO_BC)) {
            if (neighborBC != null) {
                throw new IllegalStateException();
            }
            if (stone.c != otherTriangle.stone.a || stone.b != otherTriangle.stone.b) {
                throw new IllegalStateException();
            }
            neighborBC = otherTriangle;
            otherTriangle.neighborAB = this;
        }
    }

    public Triangle neighborAB() {
        return neighborAB;
    }

    public Triangle neighborBC() {
        return neighborBC;
    }
}

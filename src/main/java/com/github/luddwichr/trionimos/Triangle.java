package com.github.luddwichr.trionimos;

import java.util.HashMap;
import java.util.Map;

/*
 *      c
 *     / \
 * B  /   \ A
 *   /     \
 *  a-------b
 *      C
 */
public class Triangle {

    private final Stone stone;
    private Triangle neighborA;
    private Triangle neighborB;
    private Triangle neighborC;
    private Map<Edge, Triangle> neighbors = new HashMap<>();

    public Triangle(Stone stone) {
        this.stone = stone;
    }

    public Stone getStone() {
        return stone;
    }

    public Triangle getNeighbor(Edge edge) {
        return neighbors.get(edge);
    }

    public void setNeighbor(Triangle neighbor, Edge edge) {
        this.neighbors.put(edge, neighbor);
    }

}

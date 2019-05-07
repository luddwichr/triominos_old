package com.github.luddwichr.triominos;

import java.util.EnumMap;
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
	private Map<Edge, Triangle> neighbors = new EnumMap<>(Edge.class);

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

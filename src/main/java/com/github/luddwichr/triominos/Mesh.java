package com.github.luddwichr.triominos;

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
		validateMatchingEdges(matchingEdges, triangle.getStone(), stone);
	}

	private void validateTriangleHasNoNeighbor(Triangle triangle, Edge edge) {
		if (triangle.getNeighbor(edge) != null) {
			throw new IllegalStateException();
		}
	}

	private void validateMatchingEdges(MatchingEdges matchingEdges, Stone firstStone, Stone secondStone) {
		if (!matchingEdges.isMatching(firstStone, secondStone)) {
			throw new IllegalStateException();
		}
	}

}

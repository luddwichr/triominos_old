package com.github.luddwichr.triominos;

import java.util.ArrayList;
import java.util.List;

public class Mesh {

	private final List<Triangle> triangles = new ArrayList<>();

	public void addFirstTile(Tile tile) {
		if (firstTilePlayed()) {
			throw new IllegalStateException("First tile was already added");
		}
		triangles.add(new Triangle(tile));
	}

	public List<Triangle> getTriangles() {
		return triangles;
	}

	public boolean firstTilePlayed() {
		return !triangles.isEmpty();
	}

	public void addTile(Tile tile, Triangle triangle, MatchingEdges matchingEdges) {
		if (!triangles.contains(triangle)) {
			throw new IllegalArgumentException();
		}

		validateTileCanBePlaced(tile, triangle, matchingEdges);
		Triangle newTriangle = new Triangle(tile);
		triangle.setNeighbor(newTriangle, matchingEdges.getFirstEdge());
		newTriangle.setNeighbor(triangle, matchingEdges.getSecondEdge());

		triangles.add(newTriangle);
	}

	private void validateTileCanBePlaced(Tile tile, Triangle triangle, MatchingEdges matchingEdges) {
		validateTriangleHasNoNeighbor(triangle, matchingEdges.getFirstEdge());
		validateMatchingEdges(matchingEdges, triangle.getTile(), tile);
	}

	private void validateTriangleHasNoNeighbor(Triangle triangle, Edge edge) {
		if (triangle.getNeighbor(edge) != null) {
			throw new IllegalStateException();
		}
	}

	private void validateMatchingEdges(MatchingEdges matchingEdges, Tile firstTile, Tile secondTile) {
		if (!matchingEdges.isMatching(firstTile, secondTile)) {
			throw new IllegalStateException();
		}
	}

}

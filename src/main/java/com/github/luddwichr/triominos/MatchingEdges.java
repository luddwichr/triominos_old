package com.github.luddwichr.triominos;

import java.util.function.BiFunction;

public enum MatchingEdges {
	A_TO_A(Edge.A, Edge.A, (firstTile, secondTile) -> firstTile.a == secondTile.b && firstTile.b == secondTile.a),
	A_TO_B(Edge.A, Edge.B, (firstTile, secondTile) -> firstTile.a == secondTile.c && firstTile.b == secondTile.b),
	A_TO_C(Edge.A, Edge.C, (firstTile, secondTile) -> firstTile.a == secondTile.a && firstTile.b == secondTile.c),
	B_TO_B(Edge.B, Edge.B, (firstTile, secondTile) -> firstTile.b == secondTile.c && firstTile.c == secondTile.b),
	B_TO_C(Edge.B, Edge.C, (firstTile, secondTile) -> firstTile.b == secondTile.a && firstTile.c == secondTile.c),
	C_TO_C(Edge.C, Edge.C, (firstTile, secondTile) -> firstTile.a == secondTile.c && firstTile.c == secondTile.a);

	private final Edge firstEdge;
	private final Edge secondEdge;
	private final BiFunction<Tile, Tile, Boolean> matchingFunction;

	MatchingEdges(Edge firstEdge, Edge secondEdge, BiFunction<Tile, Tile, Boolean> matchingFunction) {
		this.firstEdge = firstEdge;
		this.secondEdge = secondEdge;
		this.matchingFunction = matchingFunction;
	}

	public Edge getFirstEdge() {
		return firstEdge;
	}

	public Edge getSecondEdge() {
		return secondEdge;
	}

	public boolean isMatching(Tile firstTile, Tile secondTile) {
		return matchingFunction.apply(firstTile, secondTile);
	}
}

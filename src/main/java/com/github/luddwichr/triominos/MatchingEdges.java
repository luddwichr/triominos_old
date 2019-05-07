package com.github.luddwichr.triominos;

import java.util.function.BiFunction;

public enum MatchingEdges {
	A_TO_A(Edge.A, Edge.A, (firstStone, secondStone) -> firstStone.a == secondStone.b && firstStone.b == secondStone.a),
	A_TO_B(Edge.A, Edge.B, (firstStone, secondStone) -> firstStone.a == secondStone.c && firstStone.b == secondStone.b),
	A_TO_C(Edge.A, Edge.C, (firstStone, secondStone) -> firstStone.a == secondStone.a && firstStone.b == secondStone.c),
	B_TO_B(Edge.B, Edge.B, (firstStone, secondStone) -> firstStone.b == secondStone.c && firstStone.c == secondStone.b),
	B_TO_C(Edge.B, Edge.C, (firstStone, secondStone) -> firstStone.b == secondStone.a && firstStone.c == secondStone.c),
	C_TO_C(Edge.C, Edge.C, (firstStone, secondStone) -> firstStone.a == secondStone.c && firstStone.c == secondStone.a);

	private final Edge firstEdge;
	private final Edge secondEdge;
	private final BiFunction<Stone, Stone, Boolean> matchingFunction;

	MatchingEdges(Edge firstEdge, Edge secondEdge, BiFunction<Stone, Stone, Boolean> matchingFunction) {
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

	public boolean isMatching(Stone firstStone, Stone secondStone) {
		return matchingFunction.apply(firstStone, secondStone);
	}
}

package com.github.luddwichr.trionimos;

public enum MatchingEdges {
	A_TO_A(Edge.A, Edge.A),
	A_TO_B(Edge.A, Edge.B),
	A_TO_C(Edge.A, Edge.C),
	B_TO_B(Edge.B, Edge.B),
	B_TO_C(Edge.B, Edge.C),
	C_TO_C(Edge.C, Edge.C);

	private final Edge firstEdge;
	private final Edge secondEdge;

	MatchingEdges(Edge firstEdge, Edge secondEdge) {
		this.firstEdge = firstEdge;
		this.secondEdge = secondEdge;
	}

	public Edge getFirstEdge() {
		return firstEdge;
	}

	public Edge getSecondEdge() {
		return secondEdge;
	}
}

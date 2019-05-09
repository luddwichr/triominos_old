package com.github.luddwichr.triominos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TriangleTest {

	@Test
	void getTile() {
		Tile tile = new Tile(0, 0, 0);
		assertThat(new Triangle(tile).getTile()).isSameAs(tile);
	}

	@Test
	void neighborAccessors() {
		Triangle triangle = new Triangle(new Tile(0, 0, 0));
		Triangle neighbor = new Triangle(new Tile(1, 1, 1));
		Edge edge = Edge.A;
		assertThat(triangle.getNeighbor(edge)).isNull();

		triangle.setNeighbor(neighbor, edge);

		assertThat(triangle.getNeighbor(edge)).isSameAs(neighbor);
	}

}

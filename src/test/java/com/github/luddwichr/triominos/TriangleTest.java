package com.github.luddwichr.triominos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TriangleTest {

	@Test
	void getStone() {
		Stone stone = new Stone(0, 0, 0);
		assertThat(new Triangle(stone).getStone()).isSameAs(stone);
	}

	@Test
	void neighborAccessors() {
		Triangle triangle = new Triangle(new Stone(0, 0, 0));
		Triangle neigbor = new Triangle(new Stone(1, 1, 1));
		Edge edge = Edge.A;
		assertThat(triangle.getNeighbor(edge)).isNull();

		triangle.setNeighbor(neigbor, edge);

		assertThat(triangle.getNeighbor(edge)).isSameAs(neigbor);
	}

}

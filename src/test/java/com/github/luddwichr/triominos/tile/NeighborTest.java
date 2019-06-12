package com.github.luddwichr.triominos.tile;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NeighborTest {

	private static final Location upLocation = Location.at(0, 0);
	private static final Location downLocation = Location.at(1, 0);

	@Test
	void leftNeighbor() {
		assertThat(Neighbor.LEFT.relativeTo(upLocation)).isEqualTo(Location.at(-1, 0));
		assertThat(Neighbor.LEFT.relativeTo(downLocation)).isEqualTo(Location.at(0, 0));
	}

	@Test
	void rightNeighbor() {
		assertThat(Neighbor.RIGHT.relativeTo(upLocation)).isEqualTo(Location.at(1, 0));
		assertThat(Neighbor.RIGHT.relativeTo(downLocation)).isEqualTo(Location.at(2, 0));
	}

	@Test
	void middleNeighbor() {
		assertThat(Neighbor.MIDDLE.relativeTo(upLocation)).isEqualTo(Location.at(0, 1));
		assertThat(Neighbor.MIDDLE.relativeTo(downLocation)).isEqualTo(Location.at(1, -1));
	}

	@Test
	void farLeftNeighbor() {
		assertThat(Neighbor.FAR_LEFT.relativeTo(upLocation)).isEqualTo(Location.at(-2, 0));
		assertThat(Neighbor.FAR_LEFT.relativeTo(downLocation)).isEqualTo(Location.at(-1, 0));
	}

	@Test
	void farRightNeighbor() {
		assertThat(Neighbor.FAR_RIGHT.relativeTo(upLocation)).isEqualTo(Location.at(2, 0));
		assertThat(Neighbor.FAR_RIGHT.relativeTo(downLocation)).isEqualTo(Location.at(3, 0));
	}

	@Test
	void leftToMiddleNeighbor() {
		assertThat(Neighbor.LEFT_TO_MIDDLE.relativeTo(upLocation)).isEqualTo(Location.at(-1, 1));
		assertThat(Neighbor.LEFT_TO_MIDDLE.relativeTo(downLocation)).isEqualTo(Location.at(0, -1));
	}

	@Test
	void farLeftToMiddleNeighbor() {
		assertThat(Neighbor.FAR_LEFT_TO_MIDDLE.relativeTo(upLocation)).isEqualTo(Location.at(-2, 1));
		assertThat(Neighbor.FAR_LEFT_TO_MIDDLE.relativeTo(downLocation)).isEqualTo(Location.at(-1, -1));
	}

	@Test
	void farRightToMiddleNeighbor() {
		assertThat(Neighbor.FAR_RIGHT_TO_MIDDLE.relativeTo(upLocation)).isEqualTo(Location.at(2, 1));
		assertThat(Neighbor.FAR_RIGHT_TO_MIDDLE.relativeTo(downLocation)).isEqualTo(Location.at(3, -1));
	}

	@Test
	void rightToMiddleNeighbor() {
		assertThat(Neighbor.RIGHT_TO_MIDDLE.relativeTo(upLocation)).isEqualTo(Location.at(1, 1));
		assertThat(Neighbor.RIGHT_TO_MIDDLE.relativeTo(downLocation)).isEqualTo(Location.at(2, -1));
	}

	@Test
	void oppositeNeighbor() {
		assertThat(Neighbor.OPPOSITE.relativeTo(upLocation)).isEqualTo(Location.at(0, -1));
		assertThat(Neighbor.OPPOSITE.relativeTo(downLocation)).isEqualTo(Location.at(1, 1));
	}

	@Test
	void leftToOppositeNeighbor() {
		assertThat(Neighbor.LEFT_TO_OPPOSITE.relativeTo(upLocation)).isEqualTo(Location.at(-1, -1));
		assertThat(Neighbor.LEFT_TO_OPPOSITE.relativeTo(downLocation)).isEqualTo(Location.at(0, 1));
	}

	@Test
	void rightToOppositeNeighbor() {
		assertThat(Neighbor.RIGHT_TO_OPPOSITE.relativeTo(upLocation)).isEqualTo(Location.at(1, -1));
		assertThat(Neighbor.RIGHT_TO_OPPOSITE.relativeTo(downLocation)).isEqualTo(Location.at(2, 1));
	}
}

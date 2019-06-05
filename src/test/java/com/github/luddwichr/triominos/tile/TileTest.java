package com.github.luddwichr.triominos.tile;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TileTest {

	@Test
	void getValueYieldsValueForGivenCorner() {
		Tile tile = new Tile(1, 2, 3);
		assertThat(tile.getNumber(Corner.LEFT)).isEqualTo(1);
		assertThat(tile.getNumber(Corner.MIDDLE)).isEqualTo(2);
		assertThat(tile.getNumber(Corner.RIGHT)).isEqualTo(3);
	}

	@Test
	void pointsYieldsSumOfCornerValues() {
		assertThat(new Tile(3, 2, 1).points()).isEqualTo(6);
	}
}

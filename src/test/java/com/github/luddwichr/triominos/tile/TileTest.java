package com.github.luddwichr.triominos.tile;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TileTest {

	@Test
	void getValue() {
		Tile tile = new Tile(1, 2, 3);
		assertThat(tile.getValue(Corner.A)).isEqualTo(1);
		assertThat(tile.getValue(Corner.B)).isEqualTo(2);
		assertThat(tile.getValue(Corner.C)).isEqualTo(3);
	}

	@Test
	void points() {
		assertThat(new Tile(3, 2, 1).points()).isEqualTo(6);
	}
}

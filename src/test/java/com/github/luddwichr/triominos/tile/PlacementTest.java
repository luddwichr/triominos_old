package com.github.luddwichr.triominos.tile;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlacementTest {

	@Test
	void accessors() {
		Location location = new Location(0, 0);
		Orientation orientation = Orientation.ABC;
		Tile tile = new Tile(1, 2, 3);

		Placement placement = new Placement(tile, orientation, location);

		assertThat(placement.getTile()).isSameAs(tile);
		assertThat(placement.getOrientation()).isSameAs(orientation);
		assertThat(placement.getLocation()).isSameAs(location);
	}

	@Test
	void placementWithOrientationAndLocationFacingInDifferentDirection() {
		assertThatThrownBy(() -> new Placement( new Tile(1, 2, 3), Orientation.ABC, new Location(0, 1)))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Orientation and location of placement do not face in the same direction!");
		assertThatThrownBy(() -> new Placement( new Tile(1, 2, 3), Orientation.ACB, new Location(0, 0)))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Orientation and location of placement do not face in the same direction!");
	}

}

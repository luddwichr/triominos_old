package com.github.luddwichr.triominos.tile;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlacementTest {

	private static final Location UP_LOCATION = new Location(0, 0);
	private static final Location DOWN_LOCATION = new Location(0, 1);

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
		assertThatThrownBy(() -> new Placement( new Tile(1, 2, 3), Orientation.ABC, DOWN_LOCATION))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Orientation and location of placement do not face in the same direction!");
		assertThatThrownBy(() -> new Placement( new Tile(1, 2, 3), Orientation.ACB, UP_LOCATION))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Orientation and location of placement do not face in the same direction!");
	}

	@ParameterizedTest
	@MethodSource("providePlacementsForGetLeftFace")
	void getLeftFace(Placement placement, Face expectedFace) {
		assertThat(placement.getLeftFace()).isEqualTo(expectedFace);
	}

	private static Stream<Arguments> providePlacementsForGetLeftFace() {
		Tile tile = new Tile(1, 2, 3);
		return Stream.of(
				Arguments.of(new Placement(tile, Orientation.ABC, UP_LOCATION), new Face(1, 2)),
				Arguments.of(new Placement(tile, Orientation.ACB, DOWN_LOCATION), new Face(3, 1)),
				Arguments.of(new Placement(tile, Orientation.CAB, UP_LOCATION), new Face(3, 1)),
				Arguments.of(new Placement(tile, Orientation.CBA, DOWN_LOCATION), new Face(2, 3)),
				Arguments.of(new Placement(tile, Orientation.BCA, UP_LOCATION), new Face(2, 3)),
				Arguments.of(new Placement(tile, Orientation.BAC, DOWN_LOCATION), new Face(1, 2))
		);
	}

	@ParameterizedTest
	@MethodSource("providePlacementsForGetRightFace")
	void getRightFace(Placement placement, Face expectedFace) {
		assertThat(placement.getRightFace()).isEqualTo(expectedFace);
	}

	private static Stream<Arguments> providePlacementsForGetRightFace() {
		Tile tile = new Tile(1, 2, 3);
		return Stream.of(
				Arguments.of(new Placement(tile, Orientation.ABC, UP_LOCATION), new Face(2, 3)),
				Arguments.of(new Placement(tile, Orientation.ACB, DOWN_LOCATION), new Face(2, 3)),
				Arguments.of(new Placement(tile, Orientation.CAB, UP_LOCATION), new Face(1, 2)),
				Arguments.of(new Placement(tile, Orientation.CBA, DOWN_LOCATION), new Face(1, 2)),
				Arguments.of(new Placement(tile, Orientation.BCA, UP_LOCATION), new Face(3, 1)),
				Arguments.of(new Placement(tile, Orientation.BAC, DOWN_LOCATION), new Face(3, 1))
		);
	}

	@ParameterizedTest
	@MethodSource("providePlacementsForGetMiddleFace")
	void getMiddleFace(Placement placement, Face expectedFace) {
		assertThat(placement.getMiddleFace()).isEqualTo(expectedFace);
	}

	private static Stream<Arguments> providePlacementsForGetMiddleFace() {
		Tile tile = new Tile(1, 2, 3);
		return Stream.of(
				Arguments.of(new Placement(tile, Orientation.ABC, UP_LOCATION), new Face(1, 3)),
				Arguments.of(new Placement(tile, Orientation.ACB, DOWN_LOCATION), new Face(1, 2)),
				Arguments.of(new Placement(tile, Orientation.CAB, UP_LOCATION), new Face(3, 2)),
				Arguments.of(new Placement(tile, Orientation.CBA, DOWN_LOCATION), new Face(3, 1)),
				Arguments.of(new Placement(tile, Orientation.BCA, UP_LOCATION), new Face(2, 1)),
				Arguments.of(new Placement(tile, Orientation.BAC, DOWN_LOCATION), new Face(2, 3))
		);
	}
}

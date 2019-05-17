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
	private static final Tile tile = new Tile(1, 2, 3);

	@Test
	void getLocation() {
		Location location = new Location(0, 0);
		Placement placement = new Placement(tile, Orientation.ABC, location);
		assertThat(placement.getLocation()).isSameAs(location);
	}

	@Test
	void placementWithOrientationAndLocationFacingInDifferentDirection() {
		assertThatThrownBy(() -> new Placement(tile, Orientation.ABC, DOWN_LOCATION))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Orientation and location of placement do not face in the same direction!");
		assertThatThrownBy(() -> new Placement(tile, Orientation.ACB, UP_LOCATION))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Orientation and location of placement do not face in the same direction!");
	}

	@ParameterizedTest
	@MethodSource("providePlacementsForGetLeftFace")
	void getLeftFace(Placement placement, Face expectedFace) {
		assertThat(placement.getLeftFace()).isEqualTo(expectedFace);
	}

	private static Stream<Arguments> providePlacementsForGetLeftFace() {
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
		return Stream.of(
				Arguments.of(new Placement(tile, Orientation.ABC, UP_LOCATION), new Face(3, 1)),
				Arguments.of(new Placement(tile, Orientation.ACB, DOWN_LOCATION), new Face(1, 2)),
				Arguments.of(new Placement(tile, Orientation.CAB, UP_LOCATION), new Face(2, 3)),
				Arguments.of(new Placement(tile, Orientation.CBA, DOWN_LOCATION), new Face(3, 1)),
				Arguments.of(new Placement(tile, Orientation.BCA, UP_LOCATION), new Face(1, 2)),
				Arguments.of(new Placement(tile, Orientation.BAC, DOWN_LOCATION), new Face(2, 3))
		);
	}

	@ParameterizedTest
	@MethodSource("providePlacementsForGetRightCorner")
	void getRightCorner(Placement placement, int expectedRightCorner) {
		assertThat(placement.getRightCorner()).isEqualTo(expectedRightCorner);
	}

	private static Stream<Arguments> providePlacementsForGetRightCorner() {
		return Stream.of(
				Arguments.of(new Placement(tile, Orientation.ABC, UP_LOCATION), 3),
				Arguments.of(new Placement(tile, Orientation.ACB, DOWN_LOCATION), 2),
				Arguments.of(new Placement(tile, Orientation.CAB, UP_LOCATION), 2),
				Arguments.of(new Placement(tile, Orientation.CBA, DOWN_LOCATION), 1),
				Arguments.of(new Placement(tile, Orientation.BCA, UP_LOCATION), 1),
				Arguments.of(new Placement(tile, Orientation.BAC, DOWN_LOCATION), 3)
		);
	}

	@ParameterizedTest
	@MethodSource("providePlacementsForGetLeftCorner")
	void getLeftCorner(Placement placement, int expectedLeftCorner) {
		assertThat(placement.getLeftCorner()).isEqualTo(expectedLeftCorner);
	}

	private static Stream<Arguments> providePlacementsForGetLeftCorner() {
		return Stream.of(
				Arguments.of(new Placement(tile, Orientation.ABC, UP_LOCATION), 1),
				Arguments.of(new Placement(tile, Orientation.ACB, DOWN_LOCATION), 1),
				Arguments.of(new Placement(tile, Orientation.CAB, UP_LOCATION), 3),
				Arguments.of(new Placement(tile, Orientation.CBA, DOWN_LOCATION), 3),
				Arguments.of(new Placement(tile, Orientation.BCA, UP_LOCATION), 2),
				Arguments.of(new Placement(tile, Orientation.BAC, DOWN_LOCATION), 2)
		);
	}

	@ParameterizedTest
	@MethodSource("providePlacementsForGetMiddleCorner")
	void getMiddleCorner(Placement placement, int expectedMiddleCorner) {
		assertThat(placement.getMiddleCorner()).isEqualTo(expectedMiddleCorner);
	}

	private static Stream<Arguments> providePlacementsForGetMiddleCorner() {
		return Stream.of(
				Arguments.of(new Placement(tile, Orientation.ABC, UP_LOCATION), 2),
				Arguments.of(new Placement(tile, Orientation.ACB, DOWN_LOCATION), 3),
				Arguments.of(new Placement(tile, Orientation.CAB, UP_LOCATION), 1),
				Arguments.of(new Placement(tile, Orientation.CBA, DOWN_LOCATION), 2),
				Arguments.of(new Placement(tile, Orientation.BCA, UP_LOCATION), 3),
				Arguments.of(new Placement(tile, Orientation.BAC, DOWN_LOCATION), 1)
		);
	}

}

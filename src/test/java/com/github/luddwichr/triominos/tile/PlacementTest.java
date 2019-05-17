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
	@MethodSource("providePlacementsForValueOfRotatedCornerRight")
	void getValueOfRotatedCornerRight(Placement placement, int expectedRightCorner) {
		assertThat(placement.getValueOfRotatedCorner(RotatedCorner.RIGHT)).isEqualTo(expectedRightCorner);
	}

	private static Stream<Arguments> providePlacementsForValueOfRotatedCornerRight() {
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
	@MethodSource("providePlacementsForValueOfRotatedCornerLeft")
	void getValueOfRotatedCornerLeft(Placement placement, int expectedLeftCorner) {
		assertThat(placement.getValueOfRotatedCorner(RotatedCorner.LEFT)).isEqualTo(expectedLeftCorner);
	}

	private static Stream<Arguments> providePlacementsForValueOfRotatedCornerLeft() {
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
	@MethodSource("providePlacementsForValueOfRotatedCornerMiddle")
	void getValueOfRotatedCornerMiddle(Placement placement, int expectedMiddleCorner) {
		assertThat(placement.getValueOfRotatedCorner(RotatedCorner.MIDDLE)).isEqualTo(expectedMiddleCorner);
	}

	private static Stream<Arguments> providePlacementsForValueOfRotatedCornerMiddle() {
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

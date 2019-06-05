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
	void getLocationYieldsPassedLocation() {
		Location location = new Location(0, 0);
		Placement placement = new Placement(tile, Orientation.ABC, location);
		assertThat(placement.getLocation()).isSameAs(location);
	}

	@Test
	void placementCreationShouldThrowIfOrientationAndLocationFaceInDifferentDirection() {
		assertThatThrownBy(() -> new Placement(tile, Orientation.ABC, DOWN_LOCATION))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Orientation and location of placement do not face in the same direction!");
		assertThatThrownBy(() -> new Placement(tile, Orientation.ACB, UP_LOCATION))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Orientation and location of placement do not face in the same direction!");
	}

	@ParameterizedTest
	@MethodSource("providePlacementsForRotatedNumberRightCorner")
	void getRotatedNumberShouldYieldCorrectNumberForRightCorner(Placement placement, int expectedRightCorner) {
		assertThat(placement.getRotatedNumber(Corner.RIGHT)).isEqualTo(expectedRightCorner);
	}

	private static Stream<Arguments> providePlacementsForRotatedNumberRightCorner() {
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
	@MethodSource("providePlacementsForRotatedNumberLeftCorner")
	void getRotatedNumberShouldYieldCorrectNumberForLeftCorner(Placement placement, int expectedLeftCorner) {
		assertThat(placement.getRotatedNumber(Corner.LEFT)).isEqualTo(expectedLeftCorner);
	}

	private static Stream<Arguments> providePlacementsForRotatedNumberLeftCorner() {
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
	@MethodSource("providePlacementsForRotatedNumberMiddleCorner")
	void getRotatedNumberShouldYieldCorrectNumberForMiddleCorner(Placement placement, int expectedMiddleCorner) {
		assertThat(placement.getRotatedNumber(Corner.MIDDLE)).isEqualTo(expectedMiddleCorner);
	}

	private static Stream<Arguments> providePlacementsForRotatedNumberMiddleCorner() {
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

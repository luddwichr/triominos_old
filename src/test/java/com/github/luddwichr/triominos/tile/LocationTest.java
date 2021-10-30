package com.github.luddwichr.triominos.tile;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class LocationTest {

	@Test
	void constructorAndAccessors() {
		Location location = Location.at(2, 1);
		assertThat(location.row()).isEqualTo(1);
		assertThat(location.column()).isEqualTo(2);
	}

	@ParameterizedTest(name = "{0} facing up should be {1}")
	@MethodSource("provideLocationsForIsFacingUp")
	void isFacingUp(Location location, boolean shouldFaceUp) {
		assertThat(location.isFacingUp()).isEqualTo(shouldFaceUp);
	}

	private static Stream<Arguments> provideLocationsForIsFacingUp() {
		return Stream.of(
				Arguments.of(Location.at(0, 0), true),
				Arguments.of(Location.at(1, 0), false),
				Arguments.of(Location.at(2, 0), true),
				Arguments.of(Location.at(-1, 0), false),
				Arguments.of(Location.at(-2, 0), true),
				Arguments.of(Location.at(0, 1), false),
				Arguments.of(Location.at(1, 1), true),
				Arguments.of(Location.at(0, -1), false),
				Arguments.of(Location.at(-1, -1), true)
		);
	}

}

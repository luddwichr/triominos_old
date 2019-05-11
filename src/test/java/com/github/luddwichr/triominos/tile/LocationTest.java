package com.github.luddwichr.triominos.tile;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class LocationTest {

	@Test
	void constructorAndAccessors() {
		Location location = new Location(1, 2);
		assertThat(location.getRow()).isEqualTo(1);
		assertThat(location.getColumn()).isEqualTo(2);
	}

	@ParameterizedTest(name = "Location({0}/{1}) facing up should be {2}")
	@MethodSource("provideLocationsForIsFacingUp")
	void isFacingUp(int row, int column, boolean shouldFaceUp) {
		assertThat(new Location(row, column).isFacingUp()).isEqualTo(shouldFaceUp);
	}

	private static Stream<Arguments> provideLocationsForIsFacingUp() {
		return Stream.of(
				Arguments.of(0, 0, true),
				Arguments.of(0, 1, false),
				Arguments.of(0, 2, true),
				Arguments.of(0, -1, false),
				Arguments.of(0, -2, true),
				Arguments.of(1, 0, false),
				Arguments.of(1, 1, true),
				Arguments.of(-1, 0, false),
				Arguments.of(-1, -1, true)
		);
	}

	@Test
	void ensureEqualsHashCodeContract() {
		EqualsVerifier.forClass(Location.class).verify();
	}

}

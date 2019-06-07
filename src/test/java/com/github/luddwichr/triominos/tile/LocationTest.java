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
		Location location = Location.at(1, 2);
		assertThat(location.getRow()).isEqualTo(1);
		assertThat(location.getColumn()).isEqualTo(2);
	}

	@ParameterizedTest(name = "{0} facing up should be {1}")
	@MethodSource("provideLocationsForIsFacingUp")
	void isFacingUp(Location location, boolean shouldFaceUp) {
		assertThat(location.isFacingUp()).isEqualTo(shouldFaceUp);
	}

	private static Stream<Arguments> provideLocationsForIsFacingUp() {
		return Stream.of(
				Arguments.of(Location.at(0, 0), true),
				Arguments.of(Location.at(0, 1), false),
				Arguments.of(Location.at(0, 2), true),
				Arguments.of(Location.at(0, -1), false),
				Arguments.of(Location.at(0, -2), true),
				Arguments.of(Location.at(1, 0), false),
				Arguments.of(Location.at(1, 1), true),
				Arguments.of(Location.at(-1, 0), false),
				Arguments.of(Location.at(-1, -1), true)
		);
	}

	@Test
	void ensureEqualsHashCodeContract() {
		EqualsVerifier.forClass(Location.class).verify();
	}

}

package com.github.luddwichr.triominos.tile;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class LocationTest {

	@Test
	void constructorAndAccessors() {
		Location location = new Location(1, 2);
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
				Arguments.of(new Location(0, 0), true),
				Arguments.of(new Location(0, 1), false),
				Arguments.of(new Location(0, 2), true),
				Arguments.of(new Location(0, -1), false),
				Arguments.of(new Location(0, -2), true),
				Arguments.of(new Location(1, 0), false),
				Arguments.of(new Location(1, 1), true),
				Arguments.of(new Location(-1, 0), false),
				Arguments.of(new Location(-1, -1), true)
		);
	}

	@Test
	void ensureEqualsHashCodeContract() {
		EqualsVerifier.forClass(Location.class).verify();
	}

	@Test
	void isEdgeSharedForUpFacingLocation() {
		Location location = new Location(0, 0);
		Set<Location> locationsWithSharedEdge = Set.of(new Location(0, -1), new Location(0, 1), new Location(-1, 0));
		Set<Location> locationsWithNoSharedEdge = grid().stream().filter(Predicate.not(locationsWithSharedEdge::contains)).collect(Collectors.toSet());
		locationsWithSharedEdge.forEach(otherLocation -> assertThat(location.isEdgeShared(otherLocation)).isTrue());
		locationsWithNoSharedEdge.forEach(otherLocation -> assertThat(location.isEdgeShared(otherLocation)).isFalse());
	}

	@Test
	void isEdgeSharedForDownFacingLocation() {
		Location location = new Location(0, 1);
		Set<Location> locationsWithSharedEdge = Set.of(new Location(0, 0), new Location(0, 2), new Location(1, 1));
		Set<Location> locationsWithNoSharedEdge = grid().stream().filter(Predicate.not(locationsWithSharedEdge::contains)).collect(Collectors.toSet());
		locationsWithSharedEdge.forEach(otherLocation -> assertThat(location.isEdgeShared(otherLocation)).isTrue());
		locationsWithNoSharedEdge.forEach(otherLocation -> assertThat(location.isEdgeShared(otherLocation)).isFalse());
	}

	private static Set<Location> grid() {
		Set<Location> locations = new HashSet<>();
		for (int row = -3; row <= 3; row++) {
			for (int column = -3; column <= 3; column++) {
				locations.add(new Location(row, column));
			}
		}
		return locations;
	}

}

package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Orientation;
import com.github.luddwichr.triominos.tile.Placement;
import com.github.luddwichr.triominos.tile.Tile;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ScoreCalculatorTest {

	private ScoreCalculator scoreCalculator = new ScoreCalculator(this::getPlacement);
	private Set<Location> locationsWithPlacement = new HashSet<>();

	private void addLocationsWithPlacement(Location... locations) {
		Collections.addAll(locationsWithPlacement, locations);
	}

	private Optional<Placement> getPlacement(Location location) {
		return locationsWithPlacement.stream().anyMatch(location::equals) ? Optional.of(mock(Placement.class)) : Optional.empty();
	}

	@Test
	void getScoreShouldReturnPointsOfRegularPlacedTile() {
		Placement placement = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));

		assertThat(scoreCalculator.getScore(placement)).isEqualTo(6);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus50PointsForCompletedHexagonAtMiddleCorner() {
		Placement placement = new Placement(new Tile(3, 2, 1), Orientation.ABC, Location.at(0, 0));

		addLocationsWithPlacement(Location.at(-1, 0));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(6);
		addLocationsWithPlacement(Location.at(-1, -1));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(6);
		addLocationsWithPlacement(Location.at(0, -1));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(6);
		addLocationsWithPlacement(Location.at(1, -1));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(6);
		addLocationsWithPlacement(Location.at(1, 0));

		assertThat(scoreCalculator.getScore(placement)).isEqualTo(56);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus50PointsForCompletedHexagonAtRightCorner() {
		Placement placement = new Placement(new Tile(0, 1, 2), Orientation.ACB, Location.at(-1, 0));

		addLocationsWithPlacement(Location.at(0, 0));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(1, 0));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(1, -1));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(0, -1));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(-1, -1));

		assertThat(scoreCalculator.getScore(placement)).isEqualTo(53);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus50PointsForCompletedHexagonAtLeftCorner() {
		Placement placement = new Placement(new Tile(2, 1, 0), Orientation.ACB, Location.at(1, 0));

		addLocationsWithPlacement(Location.at(0, 0));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(-1, 0));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(-1, -1));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(0, -1));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(1, -1));

		assertThat(scoreCalculator.getScore(placement)).isEqualTo(53);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus40PointsForCompletedBridgeAtMiddleCorner() {
		addLocationsWithPlacement(Location.at(0, 1), Location.at(0, -1));
		Placement placement = new Placement(new Tile(1, 1, 1), Orientation.ABC, Location.at(0, 0));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(43);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus40PointsForCompletedBridgeAtLeftCorner() {
		addLocationsWithPlacement(Location.at(1, 0), Location.at(-2, 0));
		Placement placement = new Placement(new Tile(2, 2, 2), Orientation.ABC, Location.at(0, 0));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(46);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus40PointsForCompletedBridgeAtRightCorner() {
		addLocationsWithPlacement(Location.at(-1, 0), Location.at(2, 0));
		Placement placement = new Placement(new Tile(3, 3, 3), Orientation.ABC, Location.at(0, 0));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(49);
	}

}

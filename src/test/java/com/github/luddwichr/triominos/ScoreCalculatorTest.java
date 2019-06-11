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

	private void setLocationsWithPlacement(Location ...locations) {
		Collections.addAll(locationsWithPlacement, locations);
	}

	private Optional<Placement> getPlacement(Location location) {
		return locationsWithPlacement.stream().anyMatch(location::equals) ? Optional.of(mock(Placement.class)) : Optional.empty();
	}

	@Test
	void getScoreShouldReturnPointsOfRegularPlacedTile() {
		Placement placement = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		int score = scoreCalculator.getScore(placement);
		assertThat(score).isEqualTo(6);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus50PointsForCompletedHexagonAtMiddleCorner() {
		Placement placement = new Placement(new Tile(5, 4, 3), Orientation.ABC, Location.at(0, 0));
		setLocationsWithPlacement(
				Location.at(0, 1),
				Location.at(0, -1),
				Location.at(1, 1),
				Location.at(1, 0),
				Location.at(1, -1));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(62);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus50PointsForCompletedHexagonAtRightCorner() {
		Placement placement = new Placement(new Tile(2, 1, 0), Orientation.ACB, Location.at(0, -1));
		setLocationsWithPlacement(
				Location.at(0, 0),
				Location.at(0, 1),
				Location.at(1, 1),
				Location.at(1, 0),
				Location.at(1, -1));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(53);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus50PointsForCompletedHexagonAtLeftCorner() {
		Placement placement = new Placement(new Tile(3, 4, 2), Orientation.ACB, Location.at(0, 1));
		setLocationsWithPlacement(
				Location.at(0, 0),
				Location.at(0, -1),
				Location.at(1, 1),
				Location.at(1, 0),
				Location.at(1, -1));
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(59);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus40PointsForCompletedBridgeAtMiddleCorner() {
		Placement placement = new Placement(new Tile(5, 4, 3), Orientation.ABC, Location.at(0, 0));
		setLocationsWithPlacement(
				Location.at(0, 1),
				Location.at(0, -2));
		int score = scoreCalculator.getScore(placement);
		assertThat(score).isEqualTo(52);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus40PointsForCompletedBridgeAtLeftCorner() {
		Placement placement = new Placement(new Tile(5, 4, 3), Orientation.ABC, Location.at(0, 0));
		setLocationsWithPlacement(
				Location.at(0, 1),
				Location.at(0, -2));
		int score = scoreCalculator.getScore(placement);
		assertThat(score).isEqualTo(52);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus40PointsForCompletedBridgeAtRightCorner() {
		Placement placement = new Placement(new Tile(5, 4, 3), Orientation.ABC, Location.at(0, 0));
		setLocationsWithPlacement(
				Location.at(-1, 0),
				Location.at(1, 0));
		int score = scoreCalculator.getScore(placement);
		assertThat(score).isEqualTo(52);
	}

}

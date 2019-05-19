package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.board.PlacementAccessor;
import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Orientation;
import com.github.luddwichr.triominos.tile.Placement;
import com.github.luddwichr.triominos.tile.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScoreCalculatorTest {

	private ScoreCalculator scoreCalculator;
	private PlacementAccessor placementAccessor;

	@BeforeEach
	void setup() {
		placementAccessor = mock(PlacementAccessor.class);
		scoreCalculator = new ScoreCalculator(placementAccessor);
	}

	@Test
	void getScoreShouldReturnPointsOfRegularPlacedTile() {
		Placement placement = new Placement(new Tile(1, 2, 3), Orientation.ABC, new Location(0, 0));
		int score = scoreCalculator.getScore(placement);
		assertThat(score).isEqualTo(6);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus50PointsForCompletedHexagonAtMiddleCorner() {
		Placement placement = new Placement(new Tile(5, 4, 3), Orientation.ABC, new Location(0, 0));
		when(placementAccessor.getPlacement(eq(new Location(0, 1)))).thenReturn(existingPlacementResponse());
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(12);
		when(placementAccessor.getPlacement(eq(new Location(0, -1)))).thenReturn(existingPlacementResponse());
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(12);
		when(placementAccessor.getPlacement(eq(new Location(1, 1)))).thenReturn(existingPlacementResponse());
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(12);
		when(placementAccessor.getPlacement(eq(new Location(1, 0)))).thenReturn(existingPlacementResponse());
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(12);
		when(placementAccessor.getPlacement(eq(new Location(1, -1)))).thenReturn(existingPlacementResponse());
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(62);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus50PointsForCompletedHexagonAtRightCorner() {
		Placement placement = new Placement(new Tile(2, 1, 0), Orientation.ACB, new Location(0, -1));
		when(placementAccessor.getPlacement(eq(new Location(0, 0)))).thenReturn(existingPlacementResponse());
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(3);
		when(placementAccessor.getPlacement(eq(new Location(0, 1)))).thenReturn(existingPlacementResponse());
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(3);
		when(placementAccessor.getPlacement(eq(new Location(1, 1)))).thenReturn(existingPlacementResponse());
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(3);
		when(placementAccessor.getPlacement(eq(new Location(1, 0)))).thenReturn(existingPlacementResponse());
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(3);
		when(placementAccessor.getPlacement(eq(new Location(1, -1)))).thenReturn(existingPlacementResponse());
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(53);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus50PointsForCompletedHexagonAtLeftCorner() {
		Placement placement = new Placement(new Tile(3, 4, 2), Orientation.ACB, new Location(0, 1));
		when(placementAccessor.getPlacement(eq(new Location(0, 0)))).thenReturn(existingPlacementResponse());
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(9);
		when(placementAccessor.getPlacement(eq(new Location(0, -1)))).thenReturn(existingPlacementResponse());
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(9);
		when(placementAccessor.getPlacement(eq(new Location(1, 1)))).thenReturn(existingPlacementResponse());
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(9);
		when(placementAccessor.getPlacement(eq(new Location(1, 0)))).thenReturn(existingPlacementResponse());
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(9);
		when(placementAccessor.getPlacement(eq(new Location(1, -1)))).thenReturn(existingPlacementResponse());
		assertThat(scoreCalculator.getScore(placement)).isEqualTo(59);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus40PointsForCompletedBridgeAtMiddleCorner() {
		Placement placement = new Placement(new Tile(5, 4, 3), Orientation.ABC, new Location(0, 0));
		when(placementAccessor.getPlacement(eq(new Location(1, 1)))).thenReturn(existingPlacementResponse());
		when(placementAccessor.getPlacement(eq(new Location(1, 0)))).thenReturn(existingPlacementResponse());
		when(placementAccessor.getPlacement(eq(new Location(1, -1)))).thenReturn(existingPlacementResponse());
		int score = scoreCalculator.getScore(placement);
		assertThat(score).isEqualTo(52);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus40PointsForCompletedBridgeAtLeftCorner() {
		Placement placement = new Placement(new Tile(5, 4, 3), Orientation.ABC, new Location(0, 0));
		when(placementAccessor.getPlacement(eq(new Location(0, -2)))).thenReturn(existingPlacementResponse());
		when(placementAccessor.getPlacement(eq(new Location(-1, -2)))).thenReturn(existingPlacementResponse());
		when(placementAccessor.getPlacement(eq(new Location(-1, -1)))).thenReturn(existingPlacementResponse());
		int score = scoreCalculator.getScore(placement);
		assertThat(score).isEqualTo(52);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus40PointsForCompletedBridgeAtRightCorner() {
		Placement placement = new Placement(new Tile(5, 4, 3), Orientation.ABC, new Location(0, 0));
		when(placementAccessor.getPlacement(eq(new Location(0, 2)))).thenReturn(existingPlacementResponse());
		when(placementAccessor.getPlacement(eq(new Location(-1, 2)))).thenReturn(existingPlacementResponse());
		when(placementAccessor.getPlacement(eq(new Location(-1, 1)))).thenReturn(existingPlacementResponse());
		int score = scoreCalculator.getScore(placement);
		assertThat(score).isEqualTo(52);
	}

	private static Optional<Placement> existingPlacementResponse() {
		return Optional.of(mock(Placement.class));
	}
}

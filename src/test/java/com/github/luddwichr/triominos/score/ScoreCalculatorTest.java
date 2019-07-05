package com.github.luddwichr.triominos.score;

import com.github.luddwichr.triominos.board.Board;
import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Orientation;
import com.github.luddwichr.triominos.tile.Placement;
import com.github.luddwichr.triominos.tile.Tile;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ScoreCalculatorTest {

	private final Board board = new Board();
	private final ScoreCalculator scoreCalculator = new ScoreCalculator();

	private void addLocationsWithPlacement(Location... locations) {
		for (Location location : locations) {
			board.placeTile(new Placement(mock(Tile.class), location.isFacingUp() ? Orientation.ABC : Orientation.ACB, location));
		}
	}

	@Test
	void getScoreShouldReturnPointsOfRegularPlacedTile() {
		Placement placement = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));

		assertThat(getScore(placement)).isEqualTo(6);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus50PointsForCompletedHexagonAtMiddleCorner() {
		Placement placement = new Placement(new Tile(3, 2, 1), Orientation.ABC, Location.at(0, 0));

		addLocationsWithPlacement(Location.at(-1, 0));
		assertThat(getScore(placement)).isEqualTo(6);
		addLocationsWithPlacement(Location.at(-1, -1));
		assertThat(getScore(placement)).isEqualTo(6);
		addLocationsWithPlacement(Location.at(0, -1));
		assertThat(getScore(placement)).isEqualTo(6);
		addLocationsWithPlacement(Location.at(1, -1));
		assertThat(getScore(placement)).isEqualTo(6);
		addLocationsWithPlacement(Location.at(1, 0));

		assertThat(getScore(placement)).isEqualTo(56);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus50PointsForCompletedHexagonAtRightCorner() {
		Placement placement = new Placement(new Tile(0, 1, 2), Orientation.ACB, Location.at(-1, 0));

		addLocationsWithPlacement(Location.at(0, 0));
		assertThat(getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(1, 0));
		assertThat(getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(1, -1));
		assertThat(getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(0, -1));
		assertThat(getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(-1, -1));

		assertThat(getScore(placement)).isEqualTo(53);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus50PointsForCompletedHexagonAtLeftCorner() {
		Placement placement = new Placement(new Tile(2, 1, 0), Orientation.ACB, Location.at(1, 0));

		addLocationsWithPlacement(Location.at(0, 0));
		assertThat(getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(-1, 0));
		assertThat(getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(-1, -1));
		assertThat(getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(0, -1));
		assertThat(getScore(placement)).isEqualTo(3);
		addLocationsWithPlacement(Location.at(1, -1));

		assertThat(getScore(placement)).isEqualTo(53);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus40PointsForCompletedBridgeAtMiddleCorner() {
		addLocationsWithPlacement(Location.at(0, 1), Location.at(0, -1));
		Placement placement = new Placement(new Tile(1, 1, 1), Orientation.ABC, Location.at(0, 0));
		assertThat(getScore(placement)).isEqualTo(43);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus40PointsForCompletedBridgeAtLeftCorner() {
		addLocationsWithPlacement(Location.at(1, 0), Location.at(-2, 0));
		Placement placement = new Placement(new Tile(2, 2, 2), Orientation.ABC, Location.at(0, 0));
		assertThat(getScore(placement)).isEqualTo(46);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus40PointsForCompletedBridgeAtRightCorner() {
		addLocationsWithPlacement(Location.at(-1, 0), Location.at(2, 0));
		Placement placement = new Placement(new Tile(3, 3, 3), Orientation.ABC, Location.at(0, 0));
		assertThat(getScore(placement)).isEqualTo(49);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus60PointsForTwoCompletedHexagonsAtLeftAndMiddleCorner() {
		Placement placement = new Placement(new Tile(1, 0, 0), Orientation.ABC, Location.at(0, 0));

		addLocationsWithPlacement(
				Location.at(0, 1), Location.at(-1, 1), Location.at(-2, 1), Location.at(-2, 0), Location.at(-1, 0),
				Location.at(-1, -1), Location.at(0, -1), Location.at(1, -1), Location.at(1, 0)
		);

		assertThat(getScore(placement)).isEqualTo(61);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus60PointsForTwoCompletedHexagonsAtLeftAndRightCorner() {
		Placement placement = new Placement(new Tile(1, 0, 0), Orientation.ABC, Location.at(0, 0));

		addLocationsWithPlacement(
				Location.at(0, 1), Location.at(-1, 1), Location.at(-2, 1), Location.at(-2, 0), Location.at(-1, 0),
				Location.at(1, 0), Location.at(2, 0), Location.at(2, 1), Location.at(1, 1)
		);

		assertThat(getScore(placement)).isEqualTo(61);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus60PointsForTwoCompletedHexagonsAtRightAndMiddleCorner() {
		Placement placement = new Placement(new Tile(1, 0, 0), Orientation.ABC, Location.at(0, 0));

		addLocationsWithPlacement(
				Location.at(-1, 0), Location.at(-1, -1), Location.at(0, -1), Location.at(1, -1), Location.at(1, 0),
				Location.at(2, 0), Location.at(2, 1), Location.at(1, 1), Location.at(0, 1)
		);

		assertThat(getScore(placement)).isEqualTo(61);
	}

	@Test
	void getScoreShouldReturnPointsOfTilePlus70PointsForThreeCompletedHexagons() {
		Placement placement = new Placement(new Tile(0, 2, 0), Orientation.ABC, Location.at(0, 0));

		addLocationsWithPlacement(
				Location.at(0, 1), Location.at(-1, 1), Location.at(-2, 1), Location.at(-2, 0), Location.at(-1, 0),
				Location.at(-1, -1), Location.at(0, -1), Location.at(1, -1), Location.at(1, 0),
				Location.at(2, 0), Location.at(2, 1), Location.at(1, 1), Location.at(0, 1)

		);

		assertThat(getScore(placement)).isEqualTo(72);
	}
	
	private int getScore(Placement placement) {
		return scoreCalculator.getScore(board, placement);
	}
}

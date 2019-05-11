package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Orientation;
import com.github.luddwichr.triominos.tile.Placement;
import com.github.luddwichr.triominos.tile.Tile;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

	private final Board board = new Board();

	@Test
	void placeTileWithOrientationAndLocationFacingInDifferentDirection() {
		assertThatThrownBy(() -> board.placeTile(placementFacingUp(new Location(0, 1))))
				.isInstanceOf(IllegalPlacementException.class)
				.hasMessage("Orientation and location of placement do not face in the same direction!");
		assertThatThrownBy(() -> board.placeTile(placementFacingDown(new Location(0, 0))))
				.isInstanceOf(IllegalPlacementException.class)
				.hasMessage("Orientation and location of placement do not face in the same direction!");
	}

	@Test
	void placeTileFacingUpOnEmptyBoardAtCenterLocation() {
		Placement placement = placementFacingUp(new Location(0, 0));
		board.placeTile(placement);
		assertThat(board.getTilePlacements()).containsExactly(placement);
	}

	@Test
	void placeTileFacingUpOnEmptyBoardAtNonCenterLocation() {
		Placement placement = placementFacingUp(new Location(1, 1));
		assertThatThrownBy(() -> board.placeTile(placement))
				.isInstanceOf(IllegalPlacementException.class)
				.hasMessage("First placement must be located at 0/0 when facing up!");
		assertThat(board.getTilePlacements()).isEmpty();
	}

	@Test
	void placeTileFacingDownOnEmptyBoardAtCenterLocation() {
		Placement placement = placementFacingDown(new Location(0, 1));
		board.placeTile(placement);
		assertThat(board.getTilePlacements()).containsExactly(placement);
	}

	@Test
	void placeTileFacingDownOnEmptyBoardAtNonCenterLocation() {
		Placement placement = placementFacingDown(new Location(0, 3));
		assertThatThrownBy(() -> board.placeTile(placement))
				.isInstanceOf(IllegalPlacementException.class)
				.hasMessage("First placement must be located at 0/1 when facing down!");
		assertThat(board.getTilePlacements()).isEmpty();
	}

	private static Placement placementFacingUp(Location location) {
		return new Placement(new Tile(1, 2, 3), Orientation.ABC, location);
	}

	private static Placement placementFacingDown(Location location) {
		return new Placement(new Tile(1, 2, 3), Orientation.CBA, location);
	}

}

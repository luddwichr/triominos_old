package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Orientation;
import com.github.luddwichr.triominos.tile.Placement;
import com.github.luddwichr.triominos.tile.Tile;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

	private final Board board = new Board();

	@Test
	void placeTileOnEmptyBoardAddsTileToBoard() {
		Placement placement = new Placement(new Tile(1, 2, 3), Orientation.ABC, new Location(0, 0));
		Placement performedPlacement = board.placeTile(placement);
		assertThat(board.getTilePlacements()).containsExactly(performedPlacement);
	}

	@Test
	void placeTileFacingUpOnEmptyBoardIsRelocatedToRow0AndColumn1() {
		Placement placement = new Placement(new Tile(1, 2, 3), Orientation.ABC, new Location(-5, 5));
		Placement expectedPlacement = new Placement(placement.getTile(), placement.getOrientation(), new Location(0, 0));
		Placement actualPlacement = board.placeTile(placement);
		assertThat(actualPlacement).isEqualToComparingFieldByFieldRecursively(expectedPlacement);
	}

	@Test
	void placeTileFacingDownOnEmptyBoardIsRelocatedToRow0AndColumn0() {
		Placement placement = new Placement(new Tile(1, 2, 3), Orientation.ACB, new Location(5, -5));
		Placement expectedPlacement = new Placement(placement.getTile(), placement.getOrientation(), new Location(0, 1));
		Placement actualPlacement = board.placeTile(placement);
		assertThat(actualPlacement).isEqualToComparingFieldByFieldRecursively(expectedPlacement);
	}

}

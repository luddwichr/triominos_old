package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Orientation;
import com.github.luddwichr.triominos.tile.Placement;
import com.github.luddwichr.triominos.tile.Tile;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BoardTest {

	private final Board board = new Board();

	@Test
	void placeTileWithInvalidPlacement() {
		Board boardMock = mock(Board.class);
		Placement placement = mock(Placement.class);
		when(boardMock.isValidPlacement(placement)).thenReturn(false);
		doCallRealMethod().when(boardMock).placeTile(placement);
		assertThatThrownBy(() -> boardMock.placeTile(placement))
				.isInstanceOf(IllegalPlacementException.class);
	}

	@Test
	void placeTileOnEmptyBoard() {
		Placement placement = placementFacingUp(new Location(0, 0));
		board.placeTile(placement);
		assertThat(board.getTilePlacements()).containsExactly(placement);
	}

	@Test
	void isValidPlacementWithTileFacingUpOnEmptyBoardAtCenterLocation() {
		Placement placement = placementFacingUp(new Location(0, 0));
		assertThat(board.isValidPlacement(placement)).isTrue();
	}

	@Test
	void isValidPlacementWithTileFacingUpOnEmptyBoardAtNonCenterLocation() {
		Placement placement = placementFacingUp(new Location(1, 1));
		assertThat(board.isValidPlacement(placement)).isFalse();
	}

	@Test
	void isValidPlacementWithTileFacingDownOnEmptyBoardAtCenterLocation() {
		Placement placement = placementFacingDown(new Location(0, 1));
		assertThat(board.isValidPlacement(placement)).isTrue();
	}

	@Test
	void isValidPlacementWithTileFacingDownOnEmptyBoardAtNonCenterLocation() {
		Placement placement = placementFacingDown(new Location(0, 3));
		assertThat(board.isValidPlacement(placement)).isFalse();
	}

	@Test
	void isValidPlacementWithTileOnAlreadyExistingPlacement(){
		Placement placement = placementFacingUp(new Location(0, 0));
		board.placeTile(placement);
		assertThat(board.isValidPlacement(placement)).isFalse();
	}

	@Test
	void placeTileNextToFirstTile() {
		Placement firstPlacement = new Placement(new Tile(1, 2, 3), Orientation.ABC, new Location(0, 0));
		Placement secondPlacement = new Placement(new Tile(2, 3, 3), Orientation.ACB, new Location(0, 1));
		board.placeTile(firstPlacement);
		board.placeTile(secondPlacement);
		assertThat(board.getTilePlacements()).containsExactlyInAnyOrder(firstPlacement, secondPlacement);
	}

	@Test
	void isValidPlacementWithNoAdjacentPlacement() {
		Placement firstPlacement = placementFacingUp(new Location(0, 0));
		Placement secondPlacement = placementFacingUp(new Location(1, 1));
		board.placeTile(firstPlacement);
		assertThat(board.isValidPlacement(secondPlacement)).isFalse();
	}

	@Test
	void isValidPlacementWithNonMatchingLeftEdge() {
		Placement firstPlacement = new Placement(new Tile(1, 2, 3), Orientation.ABC, new Location(0, 0));
		Placement secondPlacement = new Placement(new Tile(0, 0, 0), Orientation.ACB, new Location(0, 1));
		board.placeTile(firstPlacement);
		assertThat(board.isValidPlacement(secondPlacement)).isFalse();

	}

	@Test
	void isValidPlacementWithNonMatchingRightEdge() {
		Placement firstPlacement = new Placement(new Tile(1, 2, 3), Orientation.ABC, new Location(0, 0));
		Placement secondPlacement = new Placement(new Tile(0, 0, 0), Orientation.ACB, new Location(0, -1));
		board.placeTile(firstPlacement);
		assertThat(board.isValidPlacement(secondPlacement)).isFalse();
	}

	@Test
	void isValidPlacementWithNonMatchingMiddleEdge() {
		Placement firstPlacement = new Placement(new Tile(1, 2, 3), Orientation.ABC, new Location(0, 0));
		Placement secondPlacement = new Placement(new Tile(0, 0, 0), Orientation.ACB, new Location(-1, 0));
		board.placeTile(firstPlacement);
		assertThat(board.isValidPlacement(secondPlacement)).isFalse();
	}

	@Test
	void isValidPlacementFormingHexagon() {
		createIncompleteHexagon();
		Placement placement = new Placement(new Tile(0, 3, 4), Orientation.BAC, new Location(0, -1));
		assertThat(board.isValidPlacement(placement)).isTrue();
	}

	@Test
	void isValidPlacementFormingInvalidHexagon() {
		createIncompleteHexagon();
		Placement placement = new Placement(new Tile(0, 3, 5), Orientation.BAC, new Location(0, -1));
		assertThat(board.isValidPlacement(placement)).isFalse();
	}

	private void createIncompleteHexagon() {
		board.placeTile(new Placement(new Tile(0, 0, 4), Orientation.BCA, new Location(0, 0)));
		board.placeTile(new Placement(new Tile(0, 0, 0), Orientation.ACB, new Location(-1, 0)));
		board.placeTile(new Placement(new Tile(0, 0, 1), Orientation.CAB, new Location(-1, -1)));
		board.placeTile(new Placement(new Tile(0, 1, 2), Orientation.CBA, new Location(-1, -2)));
		board.placeTile(new Placement(new Tile(0, 2, 3), Orientation.BCA, new Location(0, -2)));
	}

	@Test
	void isValidPlacementFormingBridgeMatchRightCorner() {
		createIncompleteBridgeMissingRightCornerMatch();
		Placement placement = new Placement(new Tile(3, 3, 4), Orientation.ABC, new Location(1, -1));
		assertThat(board.isValidPlacement(placement)).isTrue();
	}

	@Test
	void isValidPlacementFormingInvalidBridgeNotMatchingRightCorner() {
		createIncompleteBridgeMissingRightCornerMatch();
		Placement placement = new Placement(new Tile(3, 3, 5), Orientation.ABC, new Location(1, -1));
		assertThat(board.isValidPlacement(placement)).isFalse();
	}

	private void createIncompleteBridgeMissingRightCornerMatch() {
		createIncompleteHexagon();
		board.placeTile(new Placement(new Tile(2, 2, 3), Orientation.BAC, new Location(0, -3)));
		board.placeTile(new Placement(new Tile(2, 3, 3), Orientation.ABC, new Location(1, -3)));
		board.placeTile(new Placement(new Tile(3, 3, 3), Orientation.ACB, new Location(1, -2)));
	}

	@Test
	void isValidPlacementFormingBridgeMatchLeftCorner() {
		createIncompleteBridgeMissingLeftCornerMatch();
		Placement placement = new Placement(new Tile(3, 3, 4), Orientation.ABC, new Location(1, -1));
		assertThat(board.isValidPlacement(placement)).isTrue();
	}

	@Test
	void isValidPlacementFormingInvalidBridgeNotMatchingLeftCorner() {
		createIncompleteBridgeMissingLeftCornerMatch();
		Placement placement = new Placement(new Tile(5, 3, 4), Orientation.ABC, new Location(1, -1));
		assertThat(board.isValidPlacement(placement)).isFalse();
	}

	private void createIncompleteBridgeMissingLeftCornerMatch() {
		createIncompleteHexagon();
		board.placeTile(new Placement(new Tile(0, 4, 4), Orientation.BAC, new Location(0, 1)));
		board.placeTile(new Placement(new Tile(4, 4, 4), Orientation.ABC, new Location(1, 1)));
		board.placeTile(new Placement(new Tile(3, 4, 4), Orientation.ACB, new Location(1, 0)));
	}

	private static Placement placementFacingUp(Location location) {
		return new Placement(new Tile(1, 2, 3), Orientation.ABC, location);
	}

	private static Placement placementFacingDown(Location location) {
		return new Placement(new Tile(1, 2, 3), Orientation.CBA, location);
	}

}

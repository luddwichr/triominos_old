package com.github.luddwichr.triominos.logic;

import com.github.luddwichr.triominos.board.Board;
import com.github.luddwichr.triominos.tile.*;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class PlacementValidatorTest {

	private final Board board = new Board();
	private final PlacementValidator placementValidator = new PlacementValidator(board);

	private void setExistingPlacements(Placement... placements) {
		Stream.of(placements).forEach(board::placeTile);
	}

	@Test
	void isValidPlacement_firstUpwardsFacingPlacement_atCenterLocation() {
		Placement placement = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		assertThat(placementValidator.isValidPlacement(placement)).isTrue();
	}

	@Test
	void isValidPlacement_firstUpwardsFacingPlacement_atNonCenterLocation() {
		Placement placement = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(2, 0));
		assertThat(placementValidator.isValidPlacement(placement)).isFalse();
	}

	@Test
	void isValidPlacement_firstDownwardsFacingPlacement_atCenterLocation() {
		Placement placement = new Placement(new Tile(1, 2, 3), Orientation.ACB, Location.at(1, 0));
		assertThat(placementValidator.isValidPlacement(placement)).isTrue();
	}

	@Test
	void isValidPlacement_firstDownwardsFacingPlacement_atNonCenterLocation() {
		Placement placement = new Placement(new Tile(1, 2, 3), Orientation.ACB, Location.at(-1, 0));
		assertThat(placementValidator.isValidPlacement(placement)).isFalse();
	}

	@Test
	void isValidPlacement_locationAlreadyOccupied() {
		Placement firstPlacement = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		setExistingPlacements(firstPlacement);
		Placement placement = new Placement(new Tile(3, 4, 5), Orientation.ABC, Location.at(0, 0));

		assertThat(placementValidator.isValidPlacement(placement)).isFalse();
	}

	@Test
	void isValidPlacement_tileAlreadyPlaced() {
		Placement placement = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		setExistingPlacements(placement);
		Placement duplicateTilePlacement = new Placement(placement.getTile(), Orientation.ACB, Neighbor.RIGHT.relativeTo(placement.getLocation()));

		assertThat(placementValidator.isValidPlacement(duplicateTilePlacement)).isFalse();
	}

	@Test
	void isValidPlacement_noAdjacentPlacement() {
		Placement firstPlacement = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		setExistingPlacements(firstPlacement);
		Placement secondPlacement = new Placement(new Tile(2, 2, 2), Orientation.ABC, Location.at(1, 1));
		assertThat(placementValidator.isValidPlacement(secondPlacement)).isFalse();
	}

	// --- left corner ---

	@Test
	void isValidPlacement_leftCorner_leftNeighbor() {
		Placement leftNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		setExistingPlacements(leftNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(2, 0, 3), Orientation.ACB, Location.at(1, 0));
		Placement nextInvalidPlacement = new Placement(new Tile(1, 0, 3), Orientation.ACB, Location.at(1, 0));
		assertThat(placementValidator.isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(placementValidator.isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_leftCorner_middleNeighbor() {
		Placement middleNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		setExistingPlacements(middleNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(1, 3, 0), Orientation.ACB, Location.at(0, 1));
		Placement nextInvalidPlacement = new Placement(new Tile(2, 3, 0), Orientation.ACB, Location.at(0, 1));
		assertThat(placementValidator.isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(placementValidator.isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_leftCorner_leftToMiddleNeighbor() {
		Placement rightNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement leftToMiddleNeighbor = new Placement(new Tile(0, 0, 4), Orientation.ACB, Location.at(-2, -1));
		setExistingPlacements(rightNeighbor, leftToMiddleNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(4, 2, 1), Orientation.ACB, Location.at(-1, 0));
		Placement nextInvalidPlacement = new Placement(new Tile(5, 2, 1), Orientation.ACB, Location.at(-1, 0));
		assertThat(placementValidator.isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(placementValidator.isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_leftCorner_farLeftNeighbor() {
		Placement rightNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement farLeftNeighbor = new Placement(new Tile(0, 4, 0), Orientation.ACB, Location.at(-3, 0));
		setExistingPlacements(rightNeighbor, farLeftNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(4, 2, 1), Orientation.ACB, Location.at(-1, 0));
		Placement nextInvalidPlacement = new Placement(new Tile(5, 2, 1), Orientation.ACB, Location.at(-1, 0));
		assertThat(placementValidator.isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(placementValidator.isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_leftCorner_farLeftToMiddleNeighbor() {
		Placement rightNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement farLeftToMiddleNeighbor = new Placement(new Tile(0, 0, 4), Orientation.ABC, Location.at(-3, -1));
		setExistingPlacements(rightNeighbor, farLeftToMiddleNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(4, 2, 1), Orientation.ACB, Location.at(-1, 0));
		Placement nextInvalidPlacement = new Placement(new Tile(5, 2, 1), Orientation.ACB, Location.at(-1, 0));
		assertThat(placementValidator.isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(placementValidator.isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	// --- right corner

	@Test
	void isValidPlacement_rightCorner_rightNeighbor() {
		Placement rightNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		setExistingPlacements(rightNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(0, 2, 1), Orientation.ACB, Location.at(-1, 0));
		Placement nextInvalidPlacement = new Placement(new Tile(0, 1, 1), Orientation.ACB, Location.at(-1, 0));
		assertThat(placementValidator.isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(placementValidator.isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_rightCorner_middleNeighbor() {
		Placement middleNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		setExistingPlacements(middleNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(1, 3, 0), Orientation.ACB, Location.at(0, 1));
		Placement nextInvalidPlacement = new Placement(new Tile(1, 2, 0), Orientation.ACB, Location.at(0, 1));
		assertThat(placementValidator.isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(placementValidator.isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_rightCorner_rightToMiddleNeighbor() {
		Placement leftNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement rightToMiddleNeighbor = new Placement(new Tile(0, 0, 4), Orientation.ACB, Location.at(2, -1));
		setExistingPlacements(leftNeighbor, rightToMiddleNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(2, 4, 3), Orientation.ACB, Location.at(1, 0));
		Placement nextInvalidPlacement = new Placement(new Tile(2, 5, 3), Orientation.ACB, Location.at(1, 0));
		assertThat(placementValidator.isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(placementValidator.isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_rightCorner_farRightNeighbor() {
		Placement leftNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement farRightNeighbor = new Placement(new Tile(4, 0, 0), Orientation.ACB, Location.at(3, 0));
		setExistingPlacements(leftNeighbor, farRightNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(2, 4, 3), Orientation.ACB, Location.at(1, 0));
		Placement nextInvalidPlacement = new Placement(new Tile(2, 5, 3), Orientation.ACB, Location.at(1, 0));
		assertThat(placementValidator.isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(placementValidator.isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_rightCorner_farRightToMiddleNeighbor() {
		Placement leftNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement farRightToMiddleNeighbor = new Placement(new Tile(4, 0, 0), Orientation.ABC, Location.at(3, -1));
		setExistingPlacements(leftNeighbor, farRightToMiddleNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(2, 4, 3), Orientation.ACB, Location.at(1, 0));
		Placement nextInvalidPlacement = new Placement(new Tile(2, 5, 3), Orientation.ACB, Location.at(1, 0));
		assertThat(placementValidator.isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(placementValidator.isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	// --- middle corner

	@Test
	void isValidPlacement_middleCorner_rightNeighbor() {
		Placement rightNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		setExistingPlacements(rightNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(0, 2, 1), Orientation.ACB, Location.at(-1, 0));
		Placement nextInvalidPlacement = new Placement(new Tile(0, 2, 2), Orientation.ACB, Location.at(-1, 0));
		assertThat(placementValidator.isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(placementValidator.isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_middleCorner_leftNeighbor() {
		Placement leftNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		setExistingPlacements(leftNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(2, 0, 3), Orientation.ACB, Location.at(1, 0));
		Placement nextInvalidPlacement = new Placement(new Tile(2, 0, 2), Orientation.ACB, Location.at(1, 0));
		assertThat(placementValidator.isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(placementValidator.isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_middleCorner_rightToOppositeNeighbor() {
		Placement middleNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement rightToOppositeNeighbor = new Placement(new Tile(4, 0, 0), Orientation.ACB, Location.at(1, 2));
		setExistingPlacements(middleNeighbor, rightToOppositeNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(1, 3, 4), Orientation.ACB, Location.at(0, 1));
		Placement nextInvalidPlacement = new Placement(new Tile(1, 3, 5), Orientation.ACB, Location.at(0, 1));
		assertThat(placementValidator.isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(placementValidator.isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_middleCorner_leftToOppositeNeighbor() {
		Placement middleNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement leftToOppositeNeighbor = new Placement(new Tile(0, 4, 0), Orientation.ACB, Location.at(-1, 2));
		setExistingPlacements(middleNeighbor, leftToOppositeNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(1, 3, 4), Orientation.ACB, Location.at(0, 1));
		Placement nextInvalidPlacement = new Placement(new Tile(1, 3, 5), Orientation.ACB, Location.at(0, 1));
		assertThat(placementValidator.isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(placementValidator.isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_middleCorner_oppositeNeighbor() {
		Placement middleNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement oppositeNeighbor = new Placement(new Tile(0, 4, 0), Orientation.ABC, Location.at(0, 2));
		setExistingPlacements(middleNeighbor, oppositeNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(1, 3, 4), Orientation.ACB, Location.at(0, 1));
		Placement nextInvalidPlacement = new Placement(new Tile(1, 3, 5), Orientation.ACB, Location.at(0, 1));
		assertThat(placementValidator.isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(placementValidator.isValidPlacement(nextInvalidPlacement)).isFalse();
	}

}

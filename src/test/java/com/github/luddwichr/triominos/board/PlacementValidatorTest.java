package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Orientation;
import com.github.luddwichr.triominos.tile.Placement;
import com.github.luddwichr.triominos.tile.Tile;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class PlacementValidatorTest {

	private final PlacementValidator placementValidator = new PlacementValidator();
	private final List<Placement> placements = new LinkedList<>();

	private Optional<Placement> getPlacement(Location location) {
		return placements.stream().filter(placement -> placement.getLocation().equals(location)).findFirst();
	}

	private void initializeWith(Placement ...placements) {
		Collections.addAll(this.placements, placements);
	}

	private boolean isValidPlacement(Placement placement) {
		return placementValidator.isValidPlacement(this::getPlacement, placement);
	}

	@Test
	void isValidPlacementShouldFailIfLocationAlreadyOccupied(){
		Placement placement = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		initializeWith(placement);

		assertThat(isValidPlacement(placement)).isFalse();
	}

	// --- left corner ---

	@Test
	void isValidPlacement_leftCorner_leftNeighbor() {
		Placement leftNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		initializeWith(leftNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(2, 0, 3), Orientation.ACB, Location.at(0, 1));
		Placement nextInvalidPlacement = new Placement(new Tile(1, 0, 3), Orientation.ACB, Location.at(0, 1));
		assertThat(isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_leftCorner_middleNeighbor() {
		Placement middleNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		initializeWith(middleNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(1, 3, 0), Orientation.ACB, Location.at(-1, 0));
		Placement nextInvalidPlacement = new Placement(new Tile(2, 3, 0), Orientation.ACB, Location.at(-1, 0));
		assertThat(isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_leftCorner_leftToMiddleNeighbor() {
		Placement rightNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement leftToMiddleNeighbor = new Placement(new Tile(0, 0, 4), Orientation.ACB, Location.at(1, -2));
		initializeWith(rightNeighbor, leftToMiddleNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(4, 2, 1), Orientation.ACB, Location.at(0, -1));
		Placement nextInvalidPlacement = new Placement(new Tile(5, 2, 1), Orientation.ACB, Location.at(0, -1));
		assertThat(isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_leftCorner_farLeftNeighbor() {
		Placement rightNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement farLeftNeighbor = new Placement(new Tile(0, 4, 0), Orientation.ACB, Location.at(0, -3));
		initializeWith(rightNeighbor, farLeftNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(4, 2, 1), Orientation.ACB, Location.at(0, -1));
		Placement nextInvalidPlacement = new Placement(new Tile(5, 2, 1), Orientation.ACB, Location.at(0, -1));
		assertThat(isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_leftCorner_farLeftToMiddleNeighbor() {
		Placement rightNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement farLeftToMiddleNeighbor = new Placement(new Tile(0, 0, 4), Orientation.ABC, Location.at(1, -3));
		initializeWith(rightNeighbor, farLeftToMiddleNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(4, 2, 1), Orientation.ACB, Location.at(0, -1));
		Placement nextInvalidPlacement = new Placement(new Tile(5, 2, 1), Orientation.ACB, Location.at(0, -1));
		assertThat(isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	// --- right corner

	@Test
	void isValidPlacement_rightCorner_rightNeighbor() {
		Placement rightNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		initializeWith(rightNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(0, 2, 1), Orientation.ACB, Location.at(0, -1));
		Placement nextInvalidPlacement = new Placement(new Tile(0, 1, 1), Orientation.ACB, Location.at(0, -1));
		assertThat(isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_rightCorner_middleNeighbor() {
		Placement middleNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		initializeWith(middleNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(1, 3, 0), Orientation.ACB, Location.at(-1, 0));
		Placement nextInvalidPlacement = new Placement(new Tile(1, 2, 0), Orientation.ACB, Location.at(-1, 0));
		assertThat(isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_rightCorner_rightToMiddleNeighbor() {
		Placement leftNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement rightToMiddleNeighbor = new Placement(new Tile(0, 0, 4), Orientation.ACB, Location.at(1, 2));
		initializeWith(leftNeighbor, rightToMiddleNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(2, 4, 3), Orientation.ACB, Location.at(0, 1));
		Placement nextInvalidPlacement = new Placement(new Tile(2, 5, 3), Orientation.ACB, Location.at(0, 1));
		assertThat(isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_rightCorner_farRightNeighbor() {
		Placement leftNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement farRightNeighbor = new Placement(new Tile(4, 0, 0), Orientation.ACB, Location.at(0, 3));
		initializeWith(leftNeighbor, farRightNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(2, 4, 3), Orientation.ACB, Location.at(0, 1));
		Placement nextInvalidPlacement = new Placement(new Tile(2, 5, 3), Orientation.ACB, Location.at(0, 1));
		assertThat(isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_rightCorner_farRightToMiddleNeighbor() {
		Placement leftNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement farRightToMiddleNeighbor = new Placement(new Tile(4, 0, 0), Orientation.ABC, Location.at(1, 3));
		initializeWith(leftNeighbor, farRightToMiddleNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(2, 4, 3), Orientation.ACB, Location.at(0, 1));
		Placement nextInvalidPlacement = new Placement(new Tile(2, 5, 3), Orientation.ACB, Location.at(0, 1));
		assertThat(isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	// --- middle corner

	@Test
	void isValidPlacement_middleCorner_rightNeighbor() {
		Placement rightNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		initializeWith(rightNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(0, 2, 1), Orientation.ACB, Location.at(0, -1));
		Placement nextInvalidPlacement = new Placement(new Tile(0, 2, 2), Orientation.ACB, Location.at(0, -1));
		assertThat(isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_middleCorner_leftNeighbor() {
		Placement leftNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		initializeWith(leftNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(2, 0, 3), Orientation.ACB, Location.at(0, 1));
		Placement nextInvalidPlacement = new Placement(new Tile(2, 0, 2), Orientation.ACB, Location.at(0, 1));
		assertThat(isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_middleCorner_rightToOppositeMiddleNeighbor() {
		Placement middleNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement rightToOppositeMiddleNeighbor = new Placement(new Tile(4, 0, 0), Orientation.ACB, Location.at(-2, 1));
		initializeWith(middleNeighbor, rightToOppositeMiddleNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(1, 3, 4), Orientation.ACB, Location.at(-1, 0));
		Placement nextInvalidPlacement = new Placement(new Tile(1, 3, 5), Orientation.ACB, Location.at(-1, 0));
		assertThat(isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_middleCorner_leftToOppositeMiddleNeighbor() {
		Placement middleNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement leftToOppositeMiddleNeighbor = new Placement(new Tile(0, 4, 0), Orientation.ACB, Location.at(-2, -1));
		initializeWith(middleNeighbor, leftToOppositeMiddleNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(1, 3, 4), Orientation.ACB, Location.at(-1, 0));
		Placement nextInvalidPlacement = new Placement(new Tile(1, 3, 5), Orientation.ACB, Location.at(-1, 0));
		assertThat(isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_middleCorner_oppositeMiddleNeighbor() {
		Placement middleNeighbor = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		Placement oppositeMiddleNeighbor = new Placement(new Tile(0, 4, 0), Orientation.ABC, Location.at(-2, 0));
		initializeWith(middleNeighbor, oppositeMiddleNeighbor);

		Placement nextValidPlacement = new Placement(new Tile(1, 3, 4), Orientation.ACB, Location.at(-1, 0));
		Placement nextInvalidPlacement = new Placement(new Tile(1, 3, 5), Orientation.ACB, Location.at(-1, 0));
		assertThat(isValidPlacement(nextValidPlacement)).isTrue();
		assertThat(isValidPlacement(nextInvalidPlacement)).isFalse();
	}

	@Test
	void isValidPlacement_noAdjacentPlacement() {
		Placement firstPlacement = new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
		initializeWith(firstPlacement);
		Placement secondPlacement = new Placement(new Tile(2, 2, 2), Orientation.ABC, Location.at(1, 1));
		assertThat(isValidPlacement(secondPlacement)).isFalse();
	}

}

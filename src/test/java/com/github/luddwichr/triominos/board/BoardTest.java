package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BoardTest {

	private Board board;
	private PlacementValidator placementValidator;

	@BeforeEach
	void setup() {
		placementValidator = mock(PlacementValidator.class);
		board = new Board(placementValidator);
	}

	@Test
	void getPlacementsShouldYieldEmptyCollectionInitially() {
		assertThat(board.getPlacements()).isEmpty();
	}

	@Test
	void getPlacementsShouldYieldUnmodifiableCollection() {
		assertThatThrownBy(() -> board.getPlacements().add(someValidFirstPlacement()))
				.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> board.getPlacements().remove(someValidFirstPlacement()))
				.isInstanceOf(UnsupportedOperationException.class);
	}

	@Test
	void isValidPlacementShouldBeTrueIfFirstUpwardsFacingPlacementAtCenterLocation() {
		Placement placement = validFirstUpwardsPlacement();
		assertThat(board.isValidPlacement(placement)).isTrue();
	}

	@Test
	void placeTileShouldAddFirstUpwardsFacingPlacementAtCenterLocation() {
		Placement placement = validFirstUpwardsPlacement();
		board.placeTile(placement);
		assertThat(board.getPlacements()).containsExactly(placement);
	}

	@Test
	void isValidPlacementShouldBeFalseIfFirstUpwardsFacingPlacementAtNonCenterLocation() {
		Placement placement = invalidFirstUpwardsPlacement();
		assertThat(board.isValidPlacement(placement)).isFalse();
	}

	@Test
	void placeTileShouldThrowIfFirstUpwardsFacingPlacementAtNonCenterLocation() {
		Placement placement = invalidFirstUpwardsPlacement();
		assertThatThrownBy(() -> board.placeTile(placement)).isInstanceOf(IllegalPlacementException.class);
	}

	@Test
	void isValidPlacementShouldBeTrueIfFirstDownwardsFacingPlacementAtCenterLocation() {
		Placement placement = validFirstDownwardsPlacement();
		assertThat(board.isValidPlacement(placement)).isTrue();
	}

	@Test
	void placeTileShouldAddFirstDownwardsFacingPlacementAtCenterLocation() {
		Placement placement = validFirstDownwardsPlacement();
		board.placeTile(placement);
		assertThat(board.getPlacements()).containsExactly(placement);
	}

	@Test
	void isValidPlacementShouldBeFalseIfFirstDownwardsFacingPlacementAtNonCenterLocation() {
		Placement placement = invalidFirstDownwardsPlacement();
		assertThat(board.isValidPlacement(placement)).isFalse();
	}

	@Test
	void placeTileShouldThrowIfFirstDownwardsFacingPlacementAtNonCenterLocation() {
		Placement placement = invalidFirstDownwardsPlacement();
		assertThatThrownBy(() -> board.placeTile(placement)).isInstanceOf(IllegalPlacementException.class);
	}

	@Test
	void placeTileShouldAddPlacementIfPlacementValidationSucceeds() {
		Placement firstPlacement = someValidFirstPlacement();
		board.placeTile(firstPlacement);
		Placement placement = placementFacingDown(Location.at(0, 1));
		when(placementValidator.isValidPlacement(any(), eq(placement))).thenReturn(true);

		board.placeTile(placement);

		assertThat(board.getPlacements()).containsExactlyInAnyOrder(placement, firstPlacement);
	}

	@Test
	void placeTileShouldThrowIfPlacementValidationFails() {
		board.placeTile(someValidFirstPlacement());
		Placement placement = placementFacingDown(Location.at(0, 1));
		when(placementValidator.isValidPlacement(any(), eq(placement))).thenReturn(false);

		assertThatThrownBy(() -> board.placeTile(placement)).isInstanceOf(IllegalPlacementException.class);
	}

	@Test
	void isEmptyShouldBeTrueInitially() {
		assertThat(board.isEmpty()).isTrue();
	}

	@Test
	void isEmptyShouldBeFalseAfterFirstTileWasPlaced() {
		board.placeTile(someValidFirstPlacement());
		assertThat(board.isEmpty()).isFalse();
	}

	@Test
	void getPlacementShouldYieldEmptyIfNoPlacementAtGivenLocation() {
		board.placeTile(validFirstUpwardsPlacement());
		assertThat(board.getPlacement(Location.at(0, -1))).isEmpty();
	}

	@Test
	void getPlacementShouldYieldPlacementIfPlacementsAtGivenLocation() {
		Placement placement = validFirstUpwardsPlacement();
		board.placeTile(placement);
		assertThat(board.getPlacement(Location.at(0, 0))).get().isEqualTo(placement);
	}

	@Test
	void isValidPlacementShouldYieldFalseIfTileAlreadyPlaced(){
		Placement placement = validFirstUpwardsPlacement();
		board.placeTile(placement);
		Placement duplicateTilePlacement = new Placement(placement.getTile(), Orientation.ACB, Neighbor.RIGHT.relativeTo(placement.getLocation()));
		// lets assume the placement is legit...
		when(placementValidator.isValidPlacement(any(), eq(duplicateTilePlacement))).thenReturn(true);

		assertThat(board.isValidPlacement(duplicateTilePlacement)).isFalse();
	}

	@Test
	void placeTileShouldThrowIfTileAlreadyPlaced(){
		Placement placement = validFirstUpwardsPlacement();
		board.placeTile(placement);
		Placement duplicateTilePlacement = new Placement(placement.getTile(), Orientation.ACB, Neighbor.RIGHT.relativeTo(placement.getLocation()));
		// lets assume the placement is legit...
		when(placementValidator.isValidPlacement(any(), eq(duplicateTilePlacement))).thenReturn(true);

		assertThatThrownBy(() -> board.placeTile(duplicateTilePlacement))
				.isInstanceOf(IllegalPlacementException.class);
	}

	private static Placement someValidFirstPlacement() {
		return placementFacingUp(Location.at(0, 0));
	}

	private static Placement validFirstUpwardsPlacement() {
		return placementFacingUp(Location.at(0, 0));
	}

	private static Placement validFirstDownwardsPlacement() {
		return placementFacingDown(Location.at(0, 1));
	}

	private static Placement invalidFirstUpwardsPlacement() {
		return placementFacingUp(Location.at(1, 1));
	}

	private static Placement invalidFirstDownwardsPlacement() {
		return placementFacingDown(Location.at(0, -1));
	}

	private static Placement placementFacingUp(Location location) {
		return new Placement(new Tile(1, 2, 3), Orientation.ABC, location);
	}

	private static Placement placementFacingDown(Location location) {
		return new Placement(new Tile(1, 2, 3), Orientation.CBA, location);
	}

}

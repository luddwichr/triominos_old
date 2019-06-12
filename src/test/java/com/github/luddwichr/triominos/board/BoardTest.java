package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BoardTest {

	private Board board = new Board();

	@Test
	void getPlacementsShouldYieldEmptyCollectionInitially() {
		assertThat(board.getPlacements()).isEmpty();
	}

	@Test
	void getPlacementsShouldYieldUnmodifiableCollection() {
		assertThatThrownBy(() -> board.getPlacements().add(centerUpFacingPlacement()))
				.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> board.getPlacements().remove(centerUpFacingPlacement()))
				.isInstanceOf(UnsupportedOperationException.class);
	}

	@Test
	void placeTileShouldAddPlacement() {
		Placement placement = centerUpFacingPlacement();
		board.placeTile(placement);
		assertThat(board.getPlacements()).containsExactly(placement);
	}

	@Test
	void isEmptyShouldBeTrueInitially() {
		assertThat(board.isEmpty()).isTrue();
	}

	@Test
	void isEmptyShouldBeFalseAfterFirstTileWasPlaced() {
		board.placeTile(centerUpFacingPlacement());
		assertThat(board.isEmpty()).isFalse();
	}

	@Test
	void getPlacementShouldYieldEmptyIfNoPlacementAtGivenLocation() {
		assertThat(board.getPlacement(Location.at(-1, 0))).isEmpty();
	}

	@Test
	void getPlacementShouldYieldPlacementIfPlacementsAtGivenLocation() {
		Placement placement = centerUpFacingPlacement();
		board.placeTile(placement);
		assertThat(board.getPlacement(Location.at(0, 0))).get().isEqualTo(placement);
	}

	private Placement centerUpFacingPlacement() {
		return new Placement(new Tile(1, 2, 3), Orientation.ABC, Location.at(0, 0));
	}
}

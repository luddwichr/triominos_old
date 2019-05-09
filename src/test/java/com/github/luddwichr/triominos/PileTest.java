package com.github.luddwichr.triominos;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PileTest {

	private final Pile pile = new Pile();

	@Test
	void pileHasAsManyRemainingTilesAsClassicTileSetContains() {
		assertThat(pile.remainingTiles()).isEqualTo(TileSet.CLASSIC.size());
	}

	@Test
	void isEmptyIsFalseForInitialPile() {
		assertThat(pile.isEmpty()).isFalse();
	}

	@Test
	void isEmptyAfterAllTilesHaveBeenDrawn() {
		while (pile.remainingTiles() > 0) {
			pile.drawRandomTile();
		}
		assertThat(pile.isEmpty()).isTrue();
	}

	@Test
	void drawRandomTileEventuallyReturnsAllTilesFromTileSet() {
		Set<Tile> stonesFromPile = Stream.generate(pile::drawRandomTile).limit(TileSet.CLASSIC.size()).collect(toSet());
		assertThat(stonesFromPile).containsExactlyInAnyOrderElementsOf(TileSet.CLASSIC);
	}

	@Test
	void drawRandomTileFromEmptyPileThrows() {
		TileSet.CLASSIC.forEach(stone -> pile.drawRandomTile());
		assertThatThrownBy(pile::drawRandomTile).isInstanceOf(Pile.EmptyPileException.class);
	}
}

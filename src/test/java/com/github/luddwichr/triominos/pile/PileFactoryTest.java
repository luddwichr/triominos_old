package com.github.luddwichr.triominos.pile;

import com.github.luddwichr.triominos.tile.Tile;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

class PileFactoryTest {

	@Test
	void classicGamePile() {
		Set<Tile> classicSet = TileSet.getClassicSet();
		PileFactory pileFactory = new PileFactory();

		Pile pile = pileFactory.classicGamePile();

		Set<Tile> tilesFromPile = Stream.generate(pile::drawRandomTile).limit(classicSet.size()).collect(toSet());
		assertThat(tilesFromPile).containsExactlyInAnyOrderElementsOf(classicSet);
	}
}

package com.github.luddwichr.triominos.analysis;

import com.github.luddwichr.triominos.pile.TileSet;
import com.github.luddwichr.triominos.tile.Tile;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import static com.github.luddwichr.triominos.ToStringUtil.tilesToString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class TileFriendsAnalyzerTest {

	private final TileFriendsAnalyzer tileFriendsAnalyzer = new TileFriendsAnalyzer();

	@Test
	void findMatchingTilesInEmptySet() {
		Map<Tile, Set<Tile>> matchingTiles = findMatchingTiles(Set.of());
		assertThat(matchingTiles).isEmpty();
	}

	@Test
	void findMatchingTilesInSingleElementSet() {
		Tile singleTile = new Tile(1, 2, 3);
		Map<Tile, Set<Tile>> matchingTiles = findMatchingTiles(Set.of(singleTile));
		assertThat(matchingTiles).containsOnly(entry(singleTile, Set.of()));
	}

	@Test
	void findMatchingTilesInSetWithMatchingTiles() {
		Tile tileA = new Tile(0, 0, 0);
		Tile tileB = new Tile(0, 0, 1);

		Map<Tile, Set<Tile>> matchingTiles = findMatchingTiles(Set.of(tileA, tileB));

		assertThat(matchingTiles).containsOnly(entry(tileA, Set.of(tileB)), entry(tileB, Set.of(tileA)));
	}

	@Test
	void findMatchingTilesInSetWithoutMatchingTiles() {
		Tile tileA = new Tile(0, 0, 0);
		Tile tileB = new Tile(1, 1, 1);

		Map<Tile, Set<Tile>> matchingTiles = findMatchingTiles(Set.of(tileA, tileB));

		assertThat(matchingTiles).containsOnly(entry(tileA, Set.of()), entry(tileB, Set.of()));
	}

	@Test
	void findMatchingTilesInClassicSet() {
		findMatchingTiles(TileSet.getClassicSet())
				.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.comparingInt(Set::size)))
				.forEachOrdered(TileFriendsAnalyzerTest::printMatchingTiles);
	}

	private Map<Tile, Set<Tile>> findMatchingTiles(Set<Tile> tiles) {
		return tileFriendsAnalyzer.findMatchingTiles(tiles);
	}

	private static void printMatchingTiles(Map.Entry<Tile, Set<Tile>> matchingTiles) {
		System.out.println(matchingTiles.getKey() + ": " +
				matchingTiles.getValue().size() +
				" (" + tilesToString(matchingTiles.getValue()) + ")");
	}

}

package com.github.luddwichr.triominos.analysis;

import com.github.luddwichr.triominos.pile.TileSet;
import com.github.luddwichr.triominos.tile.Corner;
import com.github.luddwichr.triominos.tile.Tile;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

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
		assertThat(matchingTiles).containsEntry(singleTile, Set.of());
	}

	@Test
	void findMatchingTilesInSetWithMatchingTiles() {
		Tile tileA = new Tile(0, 0, 0);
		Tile tileB = new Tile(0, 0, 1);

		Map<Tile, Set<Tile>> matchingTiles = findMatchingTiles(Set.of(tileA, tileB));

		assertThat(matchingTiles).containsEntry(tileA, Set.of(tileB));
		assertThat(matchingTiles).containsEntry(tileB, Set.of(tileA));
	}

	@Test
	void findMatchingTilesInSetWithoutMatchingTiles() {
		Tile tileA = new Tile(0, 0, 0);
		Tile tileB = new Tile(1, 1, 1);

		Map<Tile, Set<Tile>> matchingTiles = findMatchingTiles(Set.of(tileA, tileB));

		assertThat(matchingTiles).containsEntry(tileA, Set.of());
		assertThat(matchingTiles).containsEntry(tileB, Set.of());
	}

	@Test
	void findMatchingTilesInClassicSet() {
		findMatchingTiles(TileSet.getClassicSet())
				.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.comparingInt(Set::size)))
				.forEach(TileFriendsAnalyzerTest::printMatchingTiles);
	}

	private Map<Tile, Set<Tile>> findMatchingTiles(Set<Tile> tiles) {
		return tileFriendsAnalyzer.findMatchingTiles(tiles);
	}

	private static void printMatchingTiles(Map.Entry<Tile, Set<Tile>> matchingTiles) {
		System.out.println(tileToString(matchingTiles.getKey()) + ": " + matchingTiles.getValue().size());
	}

	private static String tileToString(Tile tile) {
		return tile.getNumber(Corner.LEFT) + "-" + tile.getNumber(Corner.MIDDLE) + "-" + tile.getNumber(Corner.RIGHT);
	}
}

package com.github.luddwichr.triominos.analysis;

import com.github.luddwichr.triominos.pile.TileSet;
import com.github.luddwichr.triominos.tile.Tile;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.github.luddwichr.triominos.ToStringUtil.tilesToString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class TilePointsAnalyzerTest {

	private final TilePointsAnalyzer tilePointsAnalyzer = new TilePointsAnalyzer();

	@Test
	void tilesGroupByPointsForSingleElementSet() {
		Tile singleTile = new Tile(1, 2, 3);
		Map<Integer, List<Tile>> tilesByPoints = tilePointsAnalyzer.tilesGroupByPoints(Set.of(singleTile));
		assertThat(tilesByPoints).containsOnly(entry(6, List.of(singleTile)));
	}

	@Test
	void tilesGroupByPointsForSetWithTwoTilesHavingSamePoints() {
		Tile tileA = new Tile(1, 2, 3);
		Tile tileB = new Tile(0, 3, 3);
		Map<Integer, List<Tile>> tilesByScore = tilePointsAnalyzer.tilesGroupByPoints(Set.of(tileA, tileB));
		assertThat(tilesByScore).containsOnly(entry(6, List.of(tileA, tileB)));
	}

	@Test
	void tilesGroupByPointsForSetWithTwoTilesHavingDifferentPoints() {
		Tile tileA = new Tile(1, 2, 3);
		Tile tileB = new Tile(1, 1, 1);
		Map<Integer, List<Tile>> tilesByScore = tilePointsAnalyzer.tilesGroupByPoints(Set.of(tileA, tileB));
		assertThat(tilesByScore).containsOnly(entry(3, List.of(tileB)), entry(6, List.of(tileA)));
	}

	@Test
	void tilesGroupByPointsForClassicSet() {
		tilePointsAnalyzer.tilesGroupByPoints(TileSet.getClassicSet())
				.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.forEachOrdered(TilePointsAnalyzerTest::printTilesWithPoints);
	}

	private static void printTilesWithPoints(Map.Entry<Integer, List<Tile>> tilesHavingSamePoints) {
		System.out.println(tilesHavingSamePoints.getKey() + ": " + tilesToString(tilesHavingSamePoints.getValue()));
	}

}

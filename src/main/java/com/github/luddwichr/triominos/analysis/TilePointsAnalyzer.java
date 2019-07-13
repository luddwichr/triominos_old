package com.github.luddwichr.triominos.analysis;

import com.github.luddwichr.triominos.tile.Tile;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TilePointsAnalyzer {

	public Map<Integer, List<Tile>> tilesGroupByPoints(Set<Tile> tileSet) {
		return tileSet.stream().collect(Collectors.groupingBy(Tile::points));
	}
}

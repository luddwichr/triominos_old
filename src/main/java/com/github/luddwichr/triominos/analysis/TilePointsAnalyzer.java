package com.github.luddwichr.triominos.analysis;

import com.github.luddwichr.triominos.tile.Tile;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class TilePointsAnalyzer {

	public Map<Integer, Set<Tile>> tilesGroupByPoints(Set<Tile> tileSet) {
		return tileSet.stream().collect(Collectors.groupingBy(Tile::points, toSet()));
	}
}

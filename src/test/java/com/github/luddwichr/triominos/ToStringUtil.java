package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.tile.Tile;

import java.util.Collection;
import java.util.stream.Collectors;

public class ToStringUtil {

	public static String tilesToString(Collection<Tile> tiles) {
		return tiles.stream()
				.map(Tile::toString)
				.sorted(String::compareTo)
				.collect(Collectors.joining(", "));
	}
}

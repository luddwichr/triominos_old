package com.github.luddwichr.triominos.tray;

import com.github.luddwichr.triominos.tile.Tile;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Tray {

	private final Set<Tile> tiles = new LinkedHashSet<>();

	public Set<Tile> getTiles() {
		return Collections.unmodifiableSet(tiles);
	}

	public void addTile(Tile tile) {
		tiles.add(tile);
	}

	public void removeTile(Tile tile) {
		tiles.remove(tile);
	}

	public int pointsInTray() {
		return tiles.stream().mapToInt(Tile::points).sum();
	}

}

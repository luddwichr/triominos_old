package com.github.luddwichr.triominos.pile;

import com.github.luddwichr.triominos.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pile {

	private final List<Tile> tiles;

	public Pile() {
		tiles = new ArrayList<>(TileSet.getClassicSet());
		Collections.shuffle(tiles);
	}

	public boolean isEmpty() {
		return tiles.isEmpty();
	}

	public int remainingTiles() {
		return tiles.size();
	}

	public Tile drawRandomTile() {
		if (isEmpty()) {
			throw new EmptyPileException();
		}
		return tiles.remove(tiles.size() - 1);
	}

}

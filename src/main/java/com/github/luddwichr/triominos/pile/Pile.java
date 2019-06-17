package com.github.luddwichr.triominos.pile;

import com.github.luddwichr.triominos.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class Pile {

	private final List<Tile> tiles;

	public Pile(Set<Tile> tiles) {
		this.tiles = new ArrayList<>(tiles);
		Collections.shuffle(this.tiles);
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

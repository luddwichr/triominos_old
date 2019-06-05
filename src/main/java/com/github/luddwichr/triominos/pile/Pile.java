package com.github.luddwichr.triominos.pile;

import com.github.luddwichr.triominos.tile.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pile {

	private final List<Tile> pile;

	public Pile() {
		pile = new ArrayList<>(TileSet.getClassicSet());
		Collections.shuffle(pile);
	}

	public boolean isEmpty() {
		return pile.isEmpty();
	}

	public int remainingTiles() {
		return pile.size();
	}

	public Tile drawRandomTile() {
		if (isEmpty()) {
			throw new EmptyPileException();
		}
		return pile.remove(pile.size() - 1);
	}

}

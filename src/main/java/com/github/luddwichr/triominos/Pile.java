package com.github.luddwichr.triominos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pile {

	public static class EmptyPileException extends RuntimeException {
	}

	private final List<Tile> pile;

	public Pile() {
		pile = new ArrayList<>(TileSet.CLASSIC);
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

package com.github.luddwichr.triominos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pile {

	public static class PileIsEmptyException extends RuntimeException {
	}

	private final List<Stone> pile;

	public Pile() {
		pile = new ArrayList<>(StoneSet.CLASSIC);
		Collections.shuffle(pile);
	}

	public boolean isEmpty() {
		return pile.isEmpty();
	}

	public int remainingStones() {
		return pile.size();
	}

	public Stone drawStone() {
		if (isEmpty()) {
			throw new PileIsEmptyException();
		}
		return pile.remove(pile.size() - 1);
	}

}

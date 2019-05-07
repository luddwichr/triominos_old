package com.github.luddwichr.triominos;

import java.util.ArrayList;
import java.util.List;

public class Player {
	private final List<Stone> stonesOnHand = new ArrayList<>();

	public void play() {

	}

	public int pointsOnHand() {
		return stonesOnHand.stream().map(Stone::points).reduce(0, Integer::sum);
	}

	public boolean hasStones() {
		return !stonesOnHand.isEmpty();
	}
}

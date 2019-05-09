package com.github.luddwichr.triominos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Player {
	private final List<Tile> tray;
	private int score;

	public Player(Collection<Tile> initialTray) {
		tray = new ArrayList<>(initialTray);
	}

	public void play() {

	}

	public int getNumberOfTilesInTray() {
		return tray.size();
	}

	public int getPointsOfTilesInTray() {
		return tray.stream().map(Tile::points).reduce(0, Integer::sum);
	}

	public int getScore() {
		return score;
	}
}

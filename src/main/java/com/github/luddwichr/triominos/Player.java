package com.github.luddwichr.triominos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Player {
	private final List<Tile> tray;

	public Player(Collection<Tile> initialTray) {
		tray = new ArrayList<>(initialTray);
	}

	public void play() {

	}

	public int numberOfTilesInTray() {
		return tray.size();
	}

	public int pointsOfTilesInTray() {
		return tray.stream().map(Tile::points).reduce(0, Integer::sum);
	}

}

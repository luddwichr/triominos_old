package com.github.luddwichr.triominos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Player {
	private final List<Stone> tray;

	public Player(Collection<Stone> initialTray) {
		tray = new ArrayList<>(initialTray);
	}

	public void play() {

	}

	public int numberOfStonesInTray() {
		return tray.size();
	}

	public int pointsOfStonesInTray() {
		return tray.stream().map(Stone::points).reduce(0, Integer::sum);
	}

}

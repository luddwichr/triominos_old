package com.github.luddwichr.triominos.round;

import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.tile.Tile;
import com.github.luddwichr.triominos.tray.Tray;

import java.util.Map;

public class RoundRules {

	public int getNumberOfTilesToDrawForInitialTray(int numberOfPlayers) {
		return numberOfPlayers == 2 ? 9 : 7;
	}

	public Player determineFirstPlayer(Map<Player, Tray> initialTrayPerPlayer) {
		return initialTrayPerPlayer.entrySet().stream()
				.min((a, b) -> compareTray(a.getValue(), b.getValue()))
				.map(Map.Entry::getKey)
				.orElseThrow(IllegalArgumentException::new);
	}

	private int compareTray(Tray trayA, Tray trayB) {
		Tile highestRankedTileTrayA = getHighestRankedTile(trayA);
		Tile highestRankedTileTrayB = getHighestRankedTile(trayB);
		return compareTile(highestRankedTileTrayA, highestRankedTileTrayB);
	}

	private Tile getHighestRankedTile(Tray trayA) {
		return trayA.getTiles().stream().min(this::compareTile).orElseThrow();
	}

	private int compareTile(Tile tileA, Tile tileB) {
		return tileB.points() - tileA.points();
	}

}

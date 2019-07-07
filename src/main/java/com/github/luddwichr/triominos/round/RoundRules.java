package com.github.luddwichr.triominos.round;

public class RoundRules {

	public int getNumberOfTilesToDrawForInitialTray(int numberOfPlayers) {
		return numberOfPlayers == 2 ? 9 : 7;
	}

}

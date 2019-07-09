package com.github.luddwichr.triominos.turn;

import com.github.luddwichr.triominos.player.Player;

public class TurnResult {

	private final Player roundWinner;

	public TurnResult(Player roundWinner) {
		this.roundWinner = roundWinner;
	}

	public Player getRoundWinner() {
		return roundWinner;
	}
}

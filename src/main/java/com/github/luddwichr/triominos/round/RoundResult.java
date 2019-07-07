package com.github.luddwichr.triominos.round;

import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.score.ScoreCard;

public class RoundResult {

	private final Player roundWinner;
	private final ScoreCard scoreCard;

	public RoundResult(Player roundWinner, ScoreCard scoreCard) {
		this.roundWinner = roundWinner;
		this.scoreCard = scoreCard;
	}

	public Player getRoundWinner() {
		return roundWinner;
	}

	public ScoreCard getScoreCard() {
		return scoreCard;
	}

}

package com.github.luddwichr.triominos.turn;

import com.github.luddwichr.triominos.game.GameRules;
import com.github.luddwichr.triominos.move.MoveProcessor;
import com.github.luddwichr.triominos.move.MoveResult;
import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.round.RoundState;
import com.github.luddwichr.triominos.score.ScoreCalculator;

public class TurnProcessor {

	private final MoveProcessor moveProcessor;
	private final ScoreCalculator scoreCalculator;
	private final GameRules gameRules;

	public TurnProcessor(GameRules gameRules, MoveProcessor moveProcessor, ScoreCalculator scoreCalculator) {
		this.gameRules = gameRules;
		this.moveProcessor = moveProcessor;
		this.scoreCalculator = scoreCalculator;
	}

	public TurnResult playTurn(RoundState roundState, TurnState turnState) {
		for (Player player : roundState.getPlayersInMoveOrder()) {
			MoveResult moveResult = moveProcessor.playMove(player, roundState, turnState);
			roundState.getScoreCard().addPoints(player, -moveResult.tilesDrawn() * 5);
			if (moveResult.blocked()) {
				roundState.getScoreCard().addPoints(player, -10);
			}
			moveResult.maybePlacement()
					.ifPresent(placement -> roundState.getScoreCard().addPoints(player, scoreCalculator.getScore(roundState.getBoard(), placement)));
		}

		return new TurnResult(determineRoundWinner(roundState, turnState));
	}

	private Player determineRoundWinner(RoundState roundState, TurnState turnState) {
		return roundState.getPlayersInMoveOrder().stream()
				.filter(player -> gameRules.winsRound(player, roundState.getTrays(), turnState))
				.findFirst().orElse(null);
	}
}

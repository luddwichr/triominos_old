package com.github.luddwichr.triominos.move;

import com.github.luddwichr.triominos.board.PlacementValidator;
import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.round.RoundState;
import com.github.luddwichr.triominos.score.ScoreCalculator;
import com.github.luddwichr.triominos.turn.TurnState;

public class MoveProcessor {

	private final PlacementValidator placementValidator;
	private final ScoreCalculator scoreCalculator;

	public MoveProcessor(PlacementValidator placementValidator, ScoreCalculator scoreCalculator) {
		this.placementValidator = placementValidator;
		this.scoreCalculator = scoreCalculator;
	}

	public MoveResult playMove(Player player, RoundState roundState, TurnState turnState) {
		return null;
	}
}

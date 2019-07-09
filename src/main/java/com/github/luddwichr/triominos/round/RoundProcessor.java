package com.github.luddwichr.triominos.round;

import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.turn.TurnProcessor;
import com.github.luddwichr.triominos.turn.TurnResult;
import com.github.luddwichr.triominos.turn.TurnState;
import com.github.luddwichr.triominos.turn.TurnState.TurnStateFactory;

public class RoundProcessor {

	private final TurnStateFactory turnStateFactory;
	private final TurnProcessor turnProcessor;

	public RoundProcessor(TurnStateFactory turnStateFactory, TurnProcessor turnProcessor) {
		this.turnStateFactory = turnStateFactory;
		this.turnProcessor = turnProcessor;
	}

	public RoundResult playRound(RoundState roundState) {
		Player roundWinner;
		do {
			TurnState turnState = turnStateFactory.createTurnState();
			TurnResult turnResult = turnProcessor.playTurn(roundState, turnState);
			roundWinner = turnResult.getRoundWinner();
		} while (roundWinner == null);

		return new RoundResult(roundWinner, roundState.getScoreCard());
	}

}

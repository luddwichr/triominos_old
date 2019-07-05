package com.github.luddwichr.triominos.game;

import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.round.RoundState;

public class GameRuleChecker {

	private static final int MIN_PLAYER_COUNT = 2;
	private static final int MAX_PLAYER_COUNT = 4;
	private static final int WINNING_SCORE = 400;

	public boolean winsGame(Player player, RoundState roundState) {
		boolean playerHasWinningScore = hasWinningScore(player, roundState);
		boolean roundWinnerHasWinningScore = hasWinningScore(roundState.getRoundWinner(), roundState);
		boolean otherPlayerIsRoundWinner = roundState.getRoundWinner() != player;
		return playerHasWinningScore && !(roundWinnerHasWinningScore && otherPlayerIsRoundWinner);
	}

	private boolean hasWinningScore(Player player, RoundState roundState) {
		return roundState.getScoreCard().getScore(player) >= WINNING_SCORE;
	}

	public boolean isNumberOfPlayersAllowed(int numberOfPlayers) {
		return numberOfPlayers >= MIN_PLAYER_COUNT && numberOfPlayers <= MAX_PLAYER_COUNT;
	}

}

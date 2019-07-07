package com.github.luddwichr.triominos.game;

import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.round.RoundResult;

public class GameRuleChecker {

	private static final int MIN_PLAYER_COUNT = 2;
	private static final int MAX_PLAYER_COUNT = 4;
	private static final int WINNING_SCORE = 400;

	public boolean winsGame(Player player, RoundResult roundResult) {
		boolean playerHasWinningScore = hasWinningScore(player, roundResult);
		boolean roundWinnerHasWinningScore = hasWinningScore(roundResult.getRoundWinner(), roundResult);
		boolean otherPlayerIsRoundWinner = roundResult.getRoundWinner() != player;
		return playerHasWinningScore && !(roundWinnerHasWinningScore && otherPlayerIsRoundWinner);
	}

	private boolean hasWinningScore(Player player, RoundResult roundResult) {
		return roundResult.getScoreCard().getScore(player) >= WINNING_SCORE;
	}

	public boolean isNumberOfPlayersAllowed(int numberOfPlayers) {
		return numberOfPlayers >= MIN_PLAYER_COUNT && numberOfPlayers <= MAX_PLAYER_COUNT;
	}

}

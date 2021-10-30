package com.github.luddwichr.triominos.game;

import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.round.RoundResult;
import com.github.luddwichr.triominos.tile.Tile;
import com.github.luddwichr.triominos.tray.Tray;
import com.github.luddwichr.triominos.turn.TurnState;

import java.util.Map;

import static java.util.Comparator.comparingInt;

public class GameRules {

	private static final int MIN_PLAYER_COUNT = 2;
	private static final int MAX_PLAYER_COUNT = 4;
	private static final int WINNING_SCORE = 400;

	public boolean winsGame(Player player, RoundResult roundResult) {
		boolean playerHasWinningScore = hasWinningScore(player, roundResult);
        boolean roundWinnerHasWinningScore = hasWinningScore(roundResult.roundWinner(), roundResult);
        boolean otherPlayerIsRoundWinner = roundResult.roundWinner() != player;
		return playerHasWinningScore && !(roundWinnerHasWinningScore && otherPlayerIsRoundWinner);
	}

	private boolean hasWinningScore(Player player, RoundResult roundResult) {
		return roundResult.scoreCard().getScore(player) >= WINNING_SCORE;
	}

	public boolean isNumberOfPlayersAllowed(int numberOfPlayers) {
		return numberOfPlayers >= MIN_PLAYER_COUNT && numberOfPlayers <= MAX_PLAYER_COUNT;
	}

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

	public boolean winsRound(Player player, Map<Player, Tray> trays, TurnState turnState) {
		boolean hasEmptyTray = trays.get(player).getTiles().isEmpty();

		boolean isPlayerWithFewestPointsInTray = trays.entrySet().stream()
				.min(comparingInt(entry -> entry.getValue().pointsInTray()))
				.map(Map.Entry::getKey)
				.filter(playerWithFewestPoints -> playerWithFewestPoints == player)
				.isPresent();

		return hasEmptyTray || (turnState.isAllPlayersBlocked() && isPlayerWithFewestPointsInTray);
	}

}

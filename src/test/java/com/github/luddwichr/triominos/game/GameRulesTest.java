package com.github.luddwichr.triominos.game;

import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.round.RoundResult;
import com.github.luddwichr.triominos.score.ScoreCard;
import com.github.luddwichr.triominos.tile.Tile;
import com.github.luddwichr.triominos.tray.Tray;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameRulesTest {

	private final GameRules gameRules = new GameRules();

	@Nested
	class GameWinnerCheck {
		private final Player playerA = mock(Player.class);
		private final Player playerB = mock(Player.class);
		private final ScoreCard scoreCard = mock(ScoreCard.class);

		@Test
		void winsGame_hasWinningScore_isWinnerOfRound() {
			when(scoreCard.getScore(playerA)).thenReturn(400);
			when(scoreCard.getScore(playerB)).thenReturn(0);
			RoundResult roundResult = new RoundResult(playerA, scoreCard);
			assertThat(gameRules.winsGame(playerA, roundResult)).isTrue();
		}

		@Test
		void winsGame_hasWinningScore_winnerOfRoundHasNotWinningScore() {
			when(scoreCard.getScore(playerA)).thenReturn(400);
			when(scoreCard.getScore(playerB)).thenReturn(0);
			RoundResult roundResult = new RoundResult(playerB, scoreCard);
			assertThat(gameRules.winsGame(playerA, roundResult)).isTrue();
		}

		@Test
		void winsGame_hasWinningScore_winnerOfRoundHasWinningScore() {
			when(scoreCard.getScore(playerA)).thenReturn(400);
			when(scoreCard.getScore(playerB)).thenReturn(400);
			RoundResult roundResult = new RoundResult(playerB, scoreCard);
			assertThat(gameRules.winsGame(playerA, roundResult)).isFalse();
		}

		@Test
		void winsGame_hasNotWinningScore_isWinnerOfRound() {
			when(scoreCard.getScore(playerA)).thenReturn(399);
			when(scoreCard.getScore(playerB)).thenReturn(0);
			RoundResult roundResult = new RoundResult(playerA, scoreCard);
			assertThat(gameRules.winsGame(playerA, roundResult)).isFalse();
		}

		@Test
		void winsGame_hasNotWinningScore_isNotWinnerOfRound() {
			when(scoreCard.getScore(playerA)).thenReturn(399);
			when(scoreCard.getScore(playerB)).thenReturn(0);
			RoundResult roundResult = new RoundResult(playerB, scoreCard);
			assertThat(gameRules.winsGame(playerA, roundResult)).isFalse();
		}

	}

	@ParameterizedTest
	@ValueSource(ints = {2, 3, 4})
	void isNumberOfPlayersAllowed(int numberOfPlayers) {
		assertThat(gameRules.isNumberOfPlayersAllowed(numberOfPlayers)).isTrue();
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 5, 6})
	void isNumberOfPlayersAllowedWithInvalidNumberOfPlayers(int numberOfPlayers) {
		assertThat(gameRules.isNumberOfPlayersAllowed(numberOfPlayers)).isFalse();
	}

	@CsvSource({"2,9", "3,7", "4,7"})
	@ParameterizedTest
	void getNumberOfTilesToDrawForInitialTray(int numberOfPlayers, int expectedTraySize) {
		assertThat(gameRules.getNumberOfTilesToDrawForInitialTray(numberOfPlayers)).isEqualTo(expectedTraySize);
	}

	@Test
	void determineFirstPlayerByTileWithMostPointsInTray() {
		Player playerA = mock(Player.class);
		Player playerB = mock(Player.class);
		Player playerC = mock(Player.class);
		Tray trayPlayerA = createTray(new Tile(0, 0, 0), new Tile(5, 5, 5),new Tile(3, 3, 3));
		Tray trayPlayerB = createTray(new Tile(1, 1, 1));
		Tray trayPlayerC = createTray(new Tile(4, 4, 4));
		Map<Player, Tray> trays = Map.of(playerB, trayPlayerB, playerA, trayPlayerA, playerC, trayPlayerC);

		assertThat(gameRules.determineFirstPlayer(trays)).isEqualTo(playerA);
	}

	@Test
	void determineFirstPlayerShouldThrowIfNoTraysProvided() {
		assertThatThrownBy(() -> gameRules.determineFirstPlayer(Map.of())).isInstanceOf(IllegalArgumentException.class);
	}

	private Tray createTray(Tile ...tiles) {
		Tray tray = new Tray();
		for (Tile tile : tiles) {
			tray.addTile(tile);
		}
		return tray;
	}
}

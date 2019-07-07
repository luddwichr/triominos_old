package com.github.luddwichr.triominos.game;

import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.round.RoundResult;
import com.github.luddwichr.triominos.score.ScoreCard;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GameRuleCheckerTest {

	private final GameRuleChecker gameRuleChecker = new GameRuleChecker();

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
			assertThat(gameRuleChecker.winsGame(playerA, roundResult)).isTrue();
		}

		@Test
		void winsGame_hasWinningScore_winnerOfRoundHasNotWinningScore() {
			when(scoreCard.getScore(playerA)).thenReturn(400);
			when(scoreCard.getScore(playerB)).thenReturn(0);
			RoundResult roundResult = new RoundResult(playerB, scoreCard);
			assertThat(gameRuleChecker.winsGame(playerA, roundResult)).isTrue();
		}

		@Test
		void winsGame_hasWinningScore_winnerOfRoundHasWinningScore() {
			when(scoreCard.getScore(playerA)).thenReturn(400);
			when(scoreCard.getScore(playerB)).thenReturn(400);
			RoundResult roundResult = new RoundResult(playerB, scoreCard);
			assertThat(gameRuleChecker.winsGame(playerA, roundResult)).isFalse();
		}

		@Test
		void winsGame_hasNotWinningScore_isWinnerOfRound() {
			when(scoreCard.getScore(playerA)).thenReturn(399);
			when(scoreCard.getScore(playerB)).thenReturn(0);
			RoundResult roundResult = new RoundResult(playerA, scoreCard);
			assertThat(gameRuleChecker.winsGame(playerA, roundResult)).isFalse();
		}

		@Test
		void winsGame_hasNotWinningScore_isNotWinnerOfRound() {
			when(scoreCard.getScore(playerA)).thenReturn(399);
			when(scoreCard.getScore(playerB)).thenReturn(0);
			RoundResult roundResult = new RoundResult(playerB, scoreCard);
			assertThat(gameRuleChecker.winsGame(playerA, roundResult)).isFalse();
		}

	}

	@ParameterizedTest
	@ValueSource(ints = {2, 3, 4})
	void isNumberOfPlayersAllowed(int numberOfPlayers) {
		assertThat(gameRuleChecker.isNumberOfPlayersAllowed(numberOfPlayers)).isTrue();
	}

	@ParameterizedTest
	@ValueSource(ints = {0, 1, 5, 6})
	void isNumberOfPlayersAllowedWithInvalidNumberOfPlayers(int numberOfPlayers) {
		assertThat(gameRuleChecker.isNumberOfPlayersAllowed(numberOfPlayers)).isFalse();
	}
}

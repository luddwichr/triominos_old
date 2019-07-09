package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.game.GameRules;
import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.round.RoundProcessor;
import com.github.luddwichr.triominos.round.RoundResult;
import com.github.luddwichr.triominos.round.RoundState;
import com.github.luddwichr.triominos.round.RoundState.RoundStateFactory;
import com.github.luddwichr.triominos.score.ScoreCard;
import com.github.luddwichr.triominos.score.ScoreCard.ScoreCardFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameTest {

	@InjectMocks
	private Game game;
	@Mock
	private GameRules gameRules;
	@Mock
	private ScoreCardFactory scoreCardFactory;
	@Mock
	private RoundStateFactory roundStateFactory;
	@Mock
	private RoundProcessor roundProcessor;

	@Test
	void throwsIfNumberOfPlayersNotAllowed() {
		List<Player> players = List.of();
		when(gameRules.isNumberOfPlayersAllowed(players.size())).thenReturn(false);
		assertThatThrownBy(() -> game.play(players))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Number of players not allowed");
	}

	@Test
	void roundsArePlayedSequentiallyUntilPlayerWinsGame() {
		Player playerA = mock(Player.class);
		Player playerB = mock(Player.class);
		List<Player> players = List.of(playerA, playerB);
		ScoreCard scoreCard = mock(ScoreCard.class);
		RoundState firstRound = mock(RoundState.class);
		RoundState secondRound = mock(RoundState.class);
		RoundResult firstRoundResult = mock(RoundResult.class);
		RoundResult secondRoundResult = mock(RoundResult.class);
		when(gameRules.isNumberOfPlayersAllowed(players.size())).thenReturn(true);
		when(scoreCardFactory.createScoreCard(players)).thenReturn(scoreCard);
		when(roundStateFactory.createRoundState(players, scoreCard)).thenReturn(firstRound).thenReturn(secondRound);
		when(roundProcessor.playRound(firstRound)).thenReturn(firstRoundResult);
		when(roundProcessor.playRound(secondRound)).thenReturn(secondRoundResult);
		when(gameRules.winsGame(any(Player.class), eq(firstRoundResult))).thenReturn(false);
		when(gameRules.winsGame(eq(playerA), eq(secondRoundResult))).thenReturn(true);

		game.play(players);

		InOrder inOrder = inOrder(roundProcessor, roundProcessor);
		inOrder.verify(roundProcessor).playRound(firstRound);
		inOrder.verify(roundProcessor).playRound(secondRound);
	}

}

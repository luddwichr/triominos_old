package com.github.luddwichr.triominos.round;

import com.github.luddwichr.triominos.board.Board;
import com.github.luddwichr.triominos.board.Board.BoardFactory;
import com.github.luddwichr.triominos.game.Participants;
import com.github.luddwichr.triominos.pile.Pile;
import com.github.luddwichr.triominos.pile.PileFactory;
import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.round.RoundState.RoundStateFactory;
import com.github.luddwichr.triominos.score.ScoreCard;
import com.github.luddwichr.triominos.tray.Tray;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoundStateTest {

	@Mock
	private BoardFactory boardFactory;
	@Mock
	private PileFactory pileFactory;
	@InjectMocks
	private RoundStateFactory roundStateFactory;

	private final Player playerA = mock(Player.class);
	private final Player playerB = mock(Player.class);
	private final ScoreCard scoreCard = mock(ScoreCard.class);
	private final Board board = mock(Board.class);
	private final Pile pile = mock(Pile.class);
	private final Participants participants = createParticipantsMock(List.of(playerA, playerB));

	private Participants createParticipantsMock(List<Player> players) {
		Participants participants = mock(Participants.class);
		when(participants.getAllPlayers()).thenReturn(players);
		return participants;
	}

	@Nested
	class RoundStateFactoryTest {

		@Test
		void initializesRoundStateWithCorrectSetup() {
			RoundState roundState = createRoundState();

			assertThat(roundState.getBoard()).isSameAs(board);
			assertThat(roundState.getPile()).isSameAs(pile);
			assertThat(roundState.getScoreCard()).isSameAs(scoreCard);
			assertThat(roundState.getParticipants()).isSameAs(participants);
			assertThat(roundState.getTrays()).containsOnlyKeys(playerA, playerB);
			assertThat(roundState.getTrays().get(playerA).getTiles()).isEmpty();
			assertThat(roundState.getTrays().get(playerB).getTiles()).isEmpty();
		}
	}

	@Test
	void getTraysShouldYieldUnmodifiableCollection() {
		RoundState roundState = createRoundState();
		Player somePlayer = mock(Player.class);
		assertThatThrownBy(() -> roundState.getTrays().put(somePlayer, new Tray()))
				.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> roundState.getTrays().remove(somePlayer))
				.isInstanceOf(UnsupportedOperationException.class);
	}

	private RoundState createRoundState() {
		when(boardFactory.emptyBoard()).thenReturn(board);
		when(pileFactory.classicGamePile()).thenReturn(pile);
		return roundStateFactory.createRoundState(participants, scoreCard);
	}


}

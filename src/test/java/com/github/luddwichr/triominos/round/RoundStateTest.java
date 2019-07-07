package com.github.luddwichr.triominos.round;

import com.github.luddwichr.triominos.board.Board;
import com.github.luddwichr.triominos.board.Board.BoardFactory;
import com.github.luddwichr.triominos.game.Participants;
import com.github.luddwichr.triominos.pile.Pile;
import com.github.luddwichr.triominos.pile.PileFactory;
import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.round.RoundState.RoundStateFactory;
import com.github.luddwichr.triominos.score.ScoreCard;
import com.github.luddwichr.triominos.tile.Tile;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoundStateTest {

	@Mock
	private RoundRules roundRules;
	@Mock
	private BoardFactory boardFactory;
	@Mock
	private PileFactory pileFactory;
	@InjectMocks
	private RoundStateFactory roundStateFactory;

	private final ScoreCard scoreCard = mock(ScoreCard.class);
	private final Board board = mock(Board.class);
	private final Pile pile = mock(Pile.class);

	@Nested
	class RoundStateFactoryTest {

		@Test
		void createsNewBoard() {
			when(boardFactory.emptyBoard()).thenReturn(board);
			RoundState roundState = createSomeRoundState();
			assertThat(roundState.getBoard()).isSameAs(board);
		}

		@Test
		void createsNewPile() {
			when(pileFactory.classicGamePile()).thenReturn(pile);
			RoundState roundState = createSomeRoundState();
			assertThat(roundState.getPile()).isSameAs(pile);
		}

		@Test
		void usesGivenScoreCard() {
			RoundState roundState = roundStateFactory.createRoundState(createParticipants(List.of()), scoreCard);
			assertThat(roundState.getScoreCard()).isSameAs(scoreCard);
		}

		@Test
		void fillsInitialTrays() {
			when(pileFactory.classicGamePile()).thenReturn(pile);
			when(roundRules.getNumberOfTilesToDrawForInitialTray(2)).thenReturn(2);
			Tile tileA = mock(Tile.class);
			Tile tileB = mock(Tile.class);
			Tile tileC = mock(Tile.class);
			Tile tileD = mock(Tile.class);
			when(pile.drawRandomTile()).thenReturn(tileA).thenReturn(tileB).thenReturn(tileC).thenReturn(tileD);

			Player playerA = mock(Player.class);
			Player playerB = mock(Player.class);
			Participants participants = createParticipants(List.of(playerA, playerB));

			RoundState roundState = roundStateFactory.createRoundState(participants, scoreCard);

			assertThat(roundState.getTrays()).containsOnlyKeys(playerA, playerB);
			assertThat(roundState.getTrays().get(playerA).getTiles()).containsExactly(tileA, tileB);
			assertThat(roundState.getTrays().get(playerB).getTiles()).containsExactly(tileC, tileD);

		}

		@Test
		void playerOrderRotatedSoThatFirstPlayerIsAtBeginning() {
			Player playerA = mock(Player.class);
			Player playerB = mock(Player.class);
			Player playerC = mock(Player.class);
			Participants participants = createParticipants(List.of(playerA, playerB, playerC));

			when(roundRules.determineFirstPlayer(any())).thenReturn(playerC);
			RoundState roundState = roundStateFactory.createRoundState(participants, scoreCard);

			assertThat(roundState.getPlayersInMoveOrder()).containsExactly(playerC, playerA, playerB);
		}

	}

	@Test
	void getTraysShouldYieldUnmodifiableCollection() {
		RoundState roundState = createSomeRoundState();
		Player somePlayer = mock(Player.class);
		assertThatThrownBy(() -> roundState.getTrays().put(somePlayer, new Tray()))
				.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> roundState.getTrays().remove(somePlayer))
				.isInstanceOf(UnsupportedOperationException.class);
	}

	@Test
	void getPlayersInMoveOrderShouldYieldUnmodifiableCollection() {
		RoundState roundState = createSomeRoundState();
		Player somePlayer = mock(Player.class);
		assertThatThrownBy(() -> roundState.getPlayersInMoveOrder().add(somePlayer))
				.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> roundState.getPlayersInMoveOrder().remove(somePlayer))
				.isInstanceOf(UnsupportedOperationException.class);
	}


	private RoundState createSomeRoundState() {
		return roundStateFactory.createRoundState(createParticipants(List.of(mock(Player.class))), mock(ScoreCard.class));
	}

	private Participants createParticipants(List<Player> players) {
		Participants participants = mock(Participants.class);
		when(participants.getAllPlayers()).thenReturn(players);
		return participants;
	}


}

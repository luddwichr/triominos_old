package com.github.luddwichr.triominos.game;

import com.github.luddwichr.triominos.game.Participants.ParticipantsFactory;
import com.github.luddwichr.triominos.player.Player;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ParticipantsTest {

	private final GameRuleChecker gameRuleChecker = mock(GameRuleChecker.class);
	private final ParticipantsFactory participantsFactory = new ParticipantsFactory(gameRuleChecker);
	private final List<Player> players = createFakePlayers();

	@Nested
	class ParticipantsFactoryTest {

		@Test
		void throwsIfNumberOfPlayersNotAllowed() {
			when(gameRuleChecker.isNumberOfPlayersAllowed(players.size())).thenReturn(false);
			assertThatThrownBy(() -> participantsFactory.createParticipants(players))
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessage("Number of players not allowed");
		}

		@Test
		void initializesParticipantsWithGivenPlayersAsUnmodifiableList() {
			Participants participants = createParticipants();
			assertThat(participants.getAllPlayers()).isEqualTo(players);
		}

	}

	@Test
	void getAllPlayersShouldYieldUnmodifiableCollection() {
		Participants participants = createParticipants();
		Player somePlayer = mock(Player.class);
		assertThatThrownBy(() -> participants.getAllPlayers().add(somePlayer))
				.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> participants.getAllPlayers().remove(somePlayer))
				.isInstanceOf(UnsupportedOperationException.class);
	}

	private Participants createParticipants() {
		when(gameRuleChecker.isNumberOfPlayersAllowed(players.size())).thenReturn(true);
		return participantsFactory.createParticipants(players);
	}

	private List<Player> createFakePlayers() {
		return Stream.generate(() -> mock(Player.class)).limit(2).collect(toList());
	}

}

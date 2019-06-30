package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.player.Player;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class GameTest {

	private final Game game = new Game();

	@ParameterizedTest
	@ValueSource(ints = {1, 5})
	void gameCanOnlyBePlayedFrom2To4Players(int numberOfPlayers) {
		assertThatThrownBy(() -> game.playGame(createFakePlayers(numberOfPlayers)))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Only 2 to 4 players allowed");
	}

	private List<Player> createFakePlayers(int numberOfPlayers) {
		return Stream.generate(() -> mock(Player.class)).limit(numberOfPlayers).collect(Collectors.toList());
	}

}

package com.github.luddwichr.triominos.round;

import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.tile.Tile;
import com.github.luddwichr.triominos.tray.Tray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class RoundRulesTest {

	private final RoundRules roundRules = new RoundRules();

	@CsvSource({"2,9", "3,7", "4,7"})
	@ParameterizedTest
	void getNumberOfTilesToDrawForInitialTray(int numberOfPlayers, int expectedTraySize) {
		assertThat(roundRules.getNumberOfTilesToDrawForInitialTray(numberOfPlayers)).isEqualTo(expectedTraySize);
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

		assertThat(roundRules.determineFirstPlayer(trays)).isEqualTo(playerA);
	}

	@Test
	void determineFirstPlayerShouldThrowIfNoTraysProvided() {
		assertThatThrownBy(() -> roundRules.determineFirstPlayer(Map.of())).isInstanceOf(IllegalArgumentException.class);
	}

	private Tray createTray(Tile ...tiles) {
		Tray tray = new Tray();
		for (Tile tile : tiles) {
			tray.addTile(tile);
		}
		return tray;
	}
}

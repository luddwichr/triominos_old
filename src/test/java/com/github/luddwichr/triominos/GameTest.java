package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.pile.Pile;
import com.github.luddwichr.triominos.pile.TileSet;
import com.github.luddwichr.triominos.player.Player;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class GameTest {

    @ParameterizedTest
    @CsvSource({"2,9", "3,7", "4,7"})
    void gameIsCorrectlyInitialized(int numberOfPlayers, int expectedTraySize) {
        Game game = new Game(createFakePlayers(numberOfPlayers));
        List<Game.PlayerInfo> players = game.getPlayers();
        Pile pile = game.getPile();

        assertThat(players).hasSize(numberOfPlayers);
        players.forEach(player -> assertThat(player.playerState.getNumberOfTilesInTray()).isEqualTo(expectedTraySize));
        assertThat(pile.remainingTiles()).isEqualTo(TileSet.getClassicSet().size() - numberOfPlayers * expectedTraySize);
    }

    private List<Player> createFakePlayers(int numberOfPlayers) {
        return Stream.generate(() -> mock(Player.class)).limit(numberOfPlayers).collect(Collectors.toList());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5})
    void gameCanOnlyBePlayedFrom2To4Players(int numberOfPlayers) {
        assertThatThrownBy(() -> new Game(createFakePlayers(numberOfPlayers)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Only 2 to 4 players allowed");
    }

}

package com.github.luddwichr.triominos;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameTest {

    @ParameterizedTest
    @CsvSource({"2,9", "3,7", "4,7"})
    void gameIsCorrectlyInitialized(int numberOfPlayers, int numberOfStonesInTray) {
        Game game = new Game(numberOfPlayers);
        List<Player> players = game.getPlayers();
        Pile pile = game.getPile();

        assertThat(players).hasSize(numberOfPlayers);
        players.forEach(player -> assertThat(player.numberOfStonesInTray()).isEqualTo(numberOfStonesInTray));
        assertThat(pile.remainingStones()).isEqualTo(StoneSet.CLASSIC.size() - numberOfPlayers * numberOfStonesInTray);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5})
    void gameCanOnlyBePlayedFrom2To4Players(int numberOfPlayers) {
        assertThatThrownBy(() -> new Game(numberOfPlayers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Only 2 to 4 players allowed");
    }

}
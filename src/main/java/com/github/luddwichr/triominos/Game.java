package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.player.Player;

import java.util.List;

public class Game {

    public void playGame(List<Player> players) {
        validateNumberOfPlayersAllowed(players.size());
    }

    private void validateNumberOfPlayersAllowed(int numberOfPlayers) {
        if (numberOfPlayers < 2 || numberOfPlayers > 4) {
            throw new IllegalArgumentException("Only 2 to 4 players allowed");
        }
    }
}

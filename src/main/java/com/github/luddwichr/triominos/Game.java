package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.pile.Pile;
import com.github.luddwichr.triominos.tile.Tile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Game {

    private List<Player> players;
    private final Pile pile = new Pile();
    private boolean ended = false;

    public Game(int numberOfPlayers) {
        validateNumberOfPlayersAllowed(numberOfPlayers);
        initializePlayers(numberOfPlayers);
    }

    private void validateNumberOfPlayersAllowed(int numberOfPlayers) {
        if (numberOfPlayers < 2 || numberOfPlayers > 4) {
            throw new IllegalArgumentException("Only 2 to 4 players allowed");
        }
    }

    private void initializePlayers(int numberOfPlayers) {
        int numberOfTilesToDraw = numberOfPlayers == 2 ? 9 : 7;
        players = Stream.generate(() -> new Player(drawInitialTrayForPlayer(numberOfTilesToDraw)))
                .limit(numberOfPlayers)
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }

    private List<Tile> drawInitialTrayForPlayer(int tilesInTray) {
        return Stream.generate(pile::drawRandomTile).limit(tilesInTray).collect(toList());
    }

    public void run() {
        while (!ended) {
            for (Player player : players) {
                player.play();
                if (player.getScore() > 400 || player.getNumberOfTilesInTray() == 0) {
                    ended = true;
                }
                if (ended) {
                    break;
                }
            }
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Pile getPile() {
        return pile;
    }
}

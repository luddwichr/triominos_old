package com.github.luddwichr.triominos;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class Game {

    private List<Player> players;
    private Pile pile = new Pile();
    private Mesh mesh = new Mesh();
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
        int stonesToDraw = numberOfPlayers == 2 ? 9 : 7;
        players = Stream.generate(() -> new Player(drawInitialStonesForPlayer(stonesToDraw)))
                .limit(numberOfPlayers)
                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }

    private List<Stone> drawInitialStonesForPlayer(int numberOfStonesToDraw) {
        return Stream.generate(pile::drawStone).limit(numberOfStonesToDraw).collect(toList());
    }

    public void run() {
        while (!ended) {
            for (Player player : players) {
                player.play();
                if (player.numberOfStonesInTray() == 0) {
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

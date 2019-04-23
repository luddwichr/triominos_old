package com.github.luddwichr.trionimos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Game {

    private List<Player> players;
    private Pile pile = new Pile();
    private List<Triangle> mesh;
    private boolean ended = false;

    public Game(int playerCount) {
        initializePlayers(playerCount);
        initializeMesh();
    }

    private void initializeMesh() {
        mesh = new ArrayList<>();
    }

    private void initializePlayers(int playerCount) {
        players = IntStream
                .range(0, playerCount)
                .mapToObj(i -> new Player())
                .collect(toList());
    }

    public void run() {
        while (!ended) {
            for (Player player : players) {
                player.play();
                if (!player.hasStones()) {
                    ended = true;
                }
                if (ended) {
                    break;
                }
            }
        }
    }
}

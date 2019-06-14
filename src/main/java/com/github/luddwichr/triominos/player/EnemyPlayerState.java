package com.github.luddwichr.triominos.player;

public class EnemyPlayerState {

    private final int score;
    private final int tilesInTray;

    public EnemyPlayerState(int tilesInTray, int score) {
        this.score = score;
        this.tilesInTray = tilesInTray;
    }

    public int getScore() {
        return score;
    }

    public int getNumberOfTilesInTray() {
        return tilesInTray;
    }
}

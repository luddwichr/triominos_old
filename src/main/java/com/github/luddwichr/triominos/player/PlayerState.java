package com.github.luddwichr.triominos.player;

import com.github.luddwichr.triominos.tile.Tile;

import java.util.List;

public class PlayerState {

    private final List<Tile> tray;
    private int score;

    public PlayerState(List<Tile> tray, int score) {
        this.tray = tray;
        this.score = score;
    }

    public int getNumberOfTilesInTray() {
        return tray.size();
    }

    public int getTotalPointsInTray() {
        return tray.stream().map(Tile::points).reduce(0, Integer::sum);
    }

    public int getScore() {
        return score;
    }

    public EnemyPlayerState toEnemyState() {
        return new EnemyPlayerState(tray.size(), score);
    }
}

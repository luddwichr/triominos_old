package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.board.Board;
import com.github.luddwichr.triominos.board.PlacementValidator;
import com.github.luddwichr.triominos.pile.Pile;
import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.player.PlayerState;
import com.github.luddwichr.triominos.tile.Tile;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Game {

    public static class PlayerInfo {
        public final Player player;
        public final PlayerState playerState;

        private PlayerInfo(Player player, PlayerState playerState) {
            this.player = player;
            this.playerState = playerState;
        }
    }

    private List<PlayerInfo> players;
    private final Pile pile = new Pile();
    private final Board board = new Board();
    private final PlacementValidator placementValidator = new PlacementValidator(board);
    private boolean ended = false;

    public Game(List<Player> players) {
        validateNumberOfPlayersAllowed(players.size());
        initializePlayers(players);
    }

    private void validateNumberOfPlayersAllowed(int numberOfPlayers) {
        if (numberOfPlayers < 2 || numberOfPlayers > 4) {
            throw new IllegalArgumentException("Only 2 to 4 players allowed");
        }
    }

    private void initializePlayers(List<Player> players) {
        int numberOfTilesToDraw = players.size() == 2 ? 9 : 7;
        this.players = players.stream()
                .map(player -> new PlayerInfo(player, new PlayerState(drawInitialTrayForPlayer(numberOfTilesToDraw), 0)))
                .collect(toList());
    }

    private List<Tile> drawInitialTrayForPlayer(int tilesInTray) {
        return Stream.generate(pile::drawRandomTile).limit(tilesInTray).collect(toList());
    }

    public void run() {
        while (!ended) {
            for (PlayerInfo playerInfo : players) {
                playerInfo.player.play(board.getPlacements(), pile.remainingTiles());
                if (playerInfo.playerState.getScore() > 400 || playerInfo.playerState.getNumberOfTilesInTray() == 0) {
                    ended = true;
                }
                if (ended) {
                    break;
                }
            }
        }
    }

    public List<PlayerInfo> getPlayers() {
        return players;
    }

    public Pile getPile() {
        return pile;
    }
}

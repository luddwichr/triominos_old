package com.github.luddwichr.triominos.score;

import com.github.luddwichr.triominos.board.Board;
import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.round.RoundWinReason;
import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Neighbor;
import com.github.luddwichr.triominos.tile.Placement;
import com.github.luddwichr.triominos.tray.Tray;

import java.util.EnumSet;
import java.util.Map;

public class ScoreCalculator {

	private static final int BRIDGE_SCORE = 40;
	private static final Map<Integer, Integer> HEXAGON_SCORES = Map.of(1, 50, 2, 60, 3, 70);
	private static final EnumSet<Neighbor> MIDDLE_CORNER_NEIGHBORS =
			EnumSet.of(Neighbor.RIGHT, Neighbor.LEFT, Neighbor.OPPOSITE, Neighbor.LEFT_TO_OPPOSITE, Neighbor.RIGHT_TO_OPPOSITE);
	private static final EnumSet<Neighbor> RIGHT_CORNER_NEIGHBORS =
			EnumSet.of(Neighbor.RIGHT, Neighbor.FAR_RIGHT, Neighbor.MIDDLE, Neighbor.RIGHT_TO_MIDDLE, Neighbor.FAR_RIGHT_TO_MIDDLE);
	private static final EnumSet<Neighbor> LEFT_CORNER_NEIGHBORS =
			EnumSet.of(Neighbor.LEFT, Neighbor.FAR_LEFT, Neighbor.MIDDLE, Neighbor.LEFT_TO_MIDDLE, Neighbor.FAR_LEFT_TO_MIDDLE);
	private static final EnumSet<Neighbor> MIDDLE_CORNER_NON_ADJACENT_NEIGHBORS =
			EnumSet.of(Neighbor.OPPOSITE, Neighbor.LEFT_TO_OPPOSITE, Neighbor.RIGHT_TO_OPPOSITE);
	private static final EnumSet<Neighbor> RIGHT_CORNER_NON_ADJACENT_NEIGHBORS =
			EnumSet.of(Neighbor.FAR_RIGHT, Neighbor.RIGHT_TO_MIDDLE, Neighbor.FAR_RIGHT_TO_MIDDLE);
	private static final EnumSet<Neighbor> LEFT_CORNER_NON_ADJACENT_NEIGHBORS =
			EnumSet.of(Neighbor.FAR_LEFT, Neighbor.LEFT_TO_MIDDLE, Neighbor.FAR_LEFT_TO_MIDDLE);
	private static final int ROUND_WON_BY_PLAYING_ALL_TILES_BASE_SCORE = 25;

	private final ThreadLocal<Board> threadSafeBoard = new ThreadLocal<>();

	public int getScoreForRoundWin(RoundWinReason roundWinReason, Player winner, Map<Player, Tray> trays) {
		switch (roundWinReason) {
			case ALL_TILES_PLACED:
				return 	getScoreForWinningRoundByPlayingAllTiles(winner, trays);
			case LEAST_POINTS_IN_TRAY:
				return getScoreForWinningRoundByHavingFewestPointsInTray(winner, trays);
			default:
				throw new IllegalStateException("Unexpected value: " + roundWinReason);
		}
	}

	private int getScoreForWinningRoundByHavingFewestPointsInTray(Player winner, Map<Player, Tray> trays) {
		return getTotalPointsInTraysOfOtherPlayers(winner, trays) - trays.get(winner).pointsInTray();
	}

	private int getScoreForWinningRoundByPlayingAllTiles(Player winner, Map<Player, Tray> trays) {
		return ROUND_WON_BY_PLAYING_ALL_TILES_BASE_SCORE + getTotalPointsInTraysOfOtherPlayers(winner, trays);
	}

	private int getTotalPointsInTraysOfOtherPlayers(Player winner, Map<Player, Tray> trays) {
		return trays.entrySet().stream()
				.filter(entry -> entry.getKey() == winner)
				.mapToInt(entry -> entry.getValue().pointsInTray())
				.sum();
	}

	public int getScore(Board board, Placement placement) {
		threadSafeBoard.set(board);
		try {

			int score = placement.getTile().points();
			int completedHexagons = completedHexagons(placement.getLocation());
			if (completedHexagons > 0) {
				score += HEXAGON_SCORES.get(completedHexagons);
			} else if (isCompletingBridge(placement.getLocation())) {
				score += BRIDGE_SCORE;
			}

			return score;
		}
		finally {
			threadSafeBoard.remove();
		}
	}

	private int completedHexagons(Location location) {
		int completedHexagons = 0;
		if (hasFiveNeighborsAtMiddleCorner(location)) {
			completedHexagons++;
		}
		if (hasFiveNeighborsAtRightCorner(location)) {
			completedHexagons++;
		}
		if (hasFiveNeighborsAtLeftCorner(location)) {
			completedHexagons++;
		}
		return completedHexagons;
	}

	private boolean hasFiveNeighborsAtMiddleCorner(Location location) {
		return hasAllNeighbors(location, MIDDLE_CORNER_NEIGHBORS);
	}

	private boolean hasFiveNeighborsAtRightCorner(Location location) {
		return hasAllNeighbors(location, RIGHT_CORNER_NEIGHBORS);
	}

	private boolean hasFiveNeighborsAtLeftCorner(Location location) {
		return hasAllNeighbors(location, LEFT_CORNER_NEIGHBORS);
	}

	private boolean hasAllNeighbors(Location location, EnumSet<Neighbor> neighbors) {
		return neighbors.stream().allMatch(neighbor -> hasNeighbor(location, neighbor));
	}

	private boolean isCompletingBridge(Location location) {
		return isCompletingBridgeAtRightCorner(location)
				|| isCompletingBridgeAtLeftCorner(location)
				|| isCompletingBridgeAtMiddleCorner(location);
	}

	private boolean isCompletingBridgeAtMiddleCorner(Location location) {
		return hasNeighbor(location, Neighbor.MIDDLE) && hasAnyNeighbor(location, MIDDLE_CORNER_NON_ADJACENT_NEIGHBORS);
	}

	private boolean isCompletingBridgeAtLeftCorner(Location location) {
		return hasNeighbor(location, Neighbor.RIGHT) && hasAnyNeighbor(location, LEFT_CORNER_NON_ADJACENT_NEIGHBORS);
	}

	private boolean isCompletingBridgeAtRightCorner(Location location) {
		return hasNeighbor(location, Neighbor.LEFT) && hasAnyNeighbor(location, RIGHT_CORNER_NON_ADJACENT_NEIGHBORS);
	}

	private boolean hasAnyNeighbor(Location location, EnumSet<Neighbor> neighbors) {
		return neighbors.stream().anyMatch(neighbor -> hasNeighbor(location, neighbor));
	}

	private boolean hasNeighbor(Location location, Neighbor neighbor) {
		return getBoard().getPlacement(neighbor.relativeTo(location)).isPresent();
	}

	private Board getBoard() {
		return threadSafeBoard.get();
	}

}

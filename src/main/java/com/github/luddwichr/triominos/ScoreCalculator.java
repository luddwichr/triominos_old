package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.board.PlacementAccessor;
import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Placement;

public class ScoreCalculator {

	private static final int HEXAGON_SCORE = 50;
	private static final int BRIDGE_SCORE = 40;

	private final ThreadLocal<PlacementAccessor> placementAccessor = new ThreadLocal<>();

	public ScoreCalculator(PlacementAccessor placementAccessor) {
		this.placementAccessor.set(placementAccessor);
	}

	public int getScore(Placement placement) {
		int score = placement.getTile().points();
		if (isCompletingHexagon(placement.getLocation())) {
			score += HEXAGON_SCORE;
		} else if (isCompletingBridge(placement.getLocation())) {
			score += BRIDGE_SCORE;
		}
		return score;
	}

	private boolean isCompletingHexagon(Location location) {
		return hasFiveNeighborsAtMiddleCorner(location)
				|| hasFiveNeighborsAtRightCorner(location)
				|| hasFiveNeighborsAtLeftCorner(location);
	}

	private boolean hasFiveNeighborsAtMiddleCorner(Location location) {
		return hasRightNeighbor(location) && hasLeftNeighbor(location) && hasOppositeNeighbor(location)
				&& hasLeftToOppositeNeighbor(location) && hasRightToOppositeNeighbor(location);
	}

	private boolean hasFiveNeighborsAtRightCorner(Location location) {
		return hasRightNeighbor(location) && hasFarRightNeighbor(location) && hasMiddleNeighbor(location)
				&& hasRightToMiddleNeighbor(location) && hasFarRightToMiddleNeighbor(location);
	}

	private boolean hasFiveNeighborsAtLeftCorner(Location location) {
		return hasLeftNeighbor(location) && hasFarLeftNeighbor(location) && hasMiddleNeighbor(location)
				&& hasLeftToMiddleNeighbor(location) && hasFarLeftToMiddleNeighbor(location);
	}

	private boolean isCompletingBridge(Location placement) {
		return isCompletingBridgeAtLeftCorner(placement)
				|| isCompletingBridgeAtRightCorner(placement)
				|| isCompletingBridgeAtMiddleCorner(placement);
	}

	private boolean isCompletingBridgeAtLeftCorner(Location location) {
		return hasFarLeftNeighbor(location) && hasRightNeighbor(location);
	}

	private boolean isCompletingBridgeAtRightCorner(Location location) {
		return hasFarRightNeighbor(location) && hasLeftNeighbor(location);
	}

	private boolean isCompletingBridgeAtMiddleCorner(Location location) {
		return hasMiddleNeighbor(location) && hasOppositeNeighbor(location);
	}

	private boolean hasOppositeNeighbor(Location location) {
		return isPlacementExisting(location.getOppositeNeighbor());
	}

	private boolean hasRightToOppositeNeighbor(Location location) {
		return isPlacementExisting(location.getOppositeNeighbor().getRightNeighbor());
	}

	private boolean hasLeftToOppositeNeighbor(Location location) {
		return isPlacementExisting(location.getOppositeNeighbor().getLeftNeighbor());
	}

	private boolean hasMiddleNeighbor(Location location) {
		return isPlacementExisting(location.getMiddleNeighbor());
	}

	private boolean hasLeftNeighbor(Location location) {
		return isPlacementExisting(location.getLeftNeighbor());
	}

	private boolean hasRightNeighbor(Location location) {
		return isPlacementExisting(location.getRightNeighbor());
	}

	private boolean hasFarRightNeighbor(Location location) {
		return isPlacementExisting(location.getFarRightNeighbor());
	}

	private boolean hasRightToMiddleNeighbor(Location location) {
		return isPlacementExisting(location.getMiddleNeighbor().getRightNeighbor());
	}

	private boolean hasFarRightToMiddleNeighbor(Location location) {
		return isPlacementExisting(location.getMiddleNeighbor().getFarRightNeighbor());
	}

	private boolean hasFarLeftToMiddleNeighbor(Location location) {
		return isPlacementExisting(location.getMiddleNeighbor().getFarLeftNeighbor());
	}

	private boolean hasLeftToMiddleNeighbor(Location location) {
		return isPlacementExisting(location.getMiddleNeighbor().getLeftNeighbor());
	}

	private boolean hasFarLeftNeighbor(Location location) {
		return isPlacementExisting(location.getFarLeftNeighbor());
	}

	private boolean isPlacementExisting(Location location) {
		return placementAccessor.get().getPlacement(location).isPresent();
	}

}

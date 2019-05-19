package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.board.PlacementAccessor;
import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Placement;

public class ScoreCalculator {

	private static final int HEXAGON_SCORE = 50;
	private static final int BRIDGE_SCORE = 40;

	private final PlacementAccessor placementAccessor;

	public ScoreCalculator(PlacementAccessor placementAccessor) {
		this.placementAccessor = placementAccessor;
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
		return placementAccessor.getPlacement(oppositeLocation(location)).isPresent();
	}

	private boolean hasRightToOppositeNeighbor(Location location) {
		return placementAccessor.getPlacement(oppositeLocation(location).getRightNeighbor()).isPresent();
	}

	private boolean hasLeftToOppositeNeighbor(Location location) {
		return placementAccessor.getPlacement(oppositeLocation(location).getLeftNeighbor()).isPresent();
	}

	private Location oppositeLocation(Location location) {
		return location.getLeftNeighbor().getMiddleNeighbor().getRightNeighbor();
	}

	private boolean hasMiddleNeighbor(Location location) {
		return placementAccessor.getPlacement(location.getMiddleNeighbor()).isPresent();
	}

	private boolean hasLeftNeighbor(Location location) {
		return placementAccessor.getPlacement(location.getLeftNeighbor()).isPresent();
	}

	private boolean hasRightNeighbor(Location location) {
		return placementAccessor.getPlacement(location.getRightNeighbor()).isPresent();
	}

	private boolean hasFarRightNeighbor(Location location) {
		return placementAccessor.getPlacement(location.getRightNeighbor().getRightNeighbor()).isPresent();
	}

	private boolean hasRightToMiddleNeighbor(Location location) {
		return placementAccessor.getPlacement(location.getMiddleNeighbor().getRightNeighbor()).isPresent();
	}

	private boolean hasFarRightToMiddleNeighbor(Location location) {
		return placementAccessor.getPlacement(location.getMiddleNeighbor().getRightNeighbor().getRightNeighbor()).isPresent();
	}

	private boolean hasFarLeftToMiddleNeighbor(Location location) {
		return placementAccessor.getPlacement(location.getMiddleNeighbor().getLeftNeighbor().getLeftNeighbor()).isPresent();
	}

	private boolean hasLeftToMiddleNeighbor(Location location) {
		return placementAccessor.getPlacement(location.getMiddleNeighbor().getLeftNeighbor()).isPresent();
	}

	private boolean hasFarLeftNeighbor(Location location) {
		return placementAccessor.getPlacement(location.getLeftNeighbor().getLeftNeighbor()).isPresent();
	}

}

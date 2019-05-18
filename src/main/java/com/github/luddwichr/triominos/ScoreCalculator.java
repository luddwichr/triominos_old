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
		if (isCompletingHexagon(placement)) {
			score += HEXAGON_SCORE;
		} else if (isCompletingBridge(placement)) {
			score += BRIDGE_SCORE;
		}
		return score;
	}

	private boolean isCompletingHexagon(Placement placement) {
		return hasFiveNeighborsAtMiddleCorner(placement)
				|| hasFiveNeighborsAtRightCorner(placement)
				|| hasFiveNeighborsAtLeftCorner(placement);
	}

	private boolean hasFiveNeighborsAtMiddleCorner(Placement placement) {
		boolean hasRightNeighbor = placementAccessor.getPlacement(placement.getLocation().getRightNeighbor()).isPresent();
		boolean hasLeftNeighbor = placementAccessor.getPlacement(placement.getLocation().getLeftNeighbor()).isPresent();
		Location oppositeLocation = placement.getLocation().getLeftNeighbor().getMiddleNeighbor().getRightNeighbor();
		boolean hasOppositeNeighbor = placementAccessor.getPlacement(oppositeLocation).isPresent();
		boolean hasLeftToOppositeNeighbor = placementAccessor.getPlacement(oppositeLocation.getLeftNeighbor()).isPresent();
		boolean hasRightToOppositeNeighbor = placementAccessor.getPlacement(oppositeLocation.getRightNeighbor()).isPresent();
		return hasRightNeighbor && hasLeftNeighbor && hasOppositeNeighbor && hasLeftToOppositeNeighbor && hasRightToOppositeNeighbor;
	}

	private boolean hasFiveNeighborsAtRightCorner(Placement placement) {
		boolean hasRightNeighbor = placementAccessor.getPlacement(placement.getLocation().getRightNeighbor()).isPresent();
		boolean hasFarRightNeighbor = placementAccessor.getPlacement(placement.getLocation().getRightNeighbor().getRightNeighbor()).isPresent();
		boolean hasMiddleNeighbor = placementAccessor.getPlacement(placement.getLocation().getMiddleNeighbor()).isPresent();
		boolean hasRightToMiddleNeighbor = placementAccessor.getPlacement(placement.getLocation().getMiddleNeighbor().getRightNeighbor()).isPresent();
		boolean hasFarRightToMiddleNeighbor = placementAccessor.getPlacement(placement.getLocation().getMiddleNeighbor().getRightNeighbor().getRightNeighbor()).isPresent();
		return hasRightNeighbor && hasFarRightNeighbor && hasMiddleNeighbor && hasRightToMiddleNeighbor && hasFarRightToMiddleNeighbor;
	}

	private boolean hasFiveNeighborsAtLeftCorner(Placement placement) {
		boolean hasLeftNeighbor = placementAccessor.getPlacement(placement.getLocation().getLeftNeighbor()).isPresent();
		boolean hasFarLeftNeighbor = placementAccessor.getPlacement(placement.getLocation().getLeftNeighbor().getLeftNeighbor()).isPresent();
		boolean hasMiddleNeighbor = placementAccessor.getPlacement(placement.getLocation().getMiddleNeighbor()).isPresent();
		boolean hasLeftToMiddleNeighbor = placementAccessor.getPlacement(placement.getLocation().getMiddleNeighbor().getLeftNeighbor()).isPresent();
		boolean hasFarLeftToMiddleNeighbor = placementAccessor.getPlacement(placement.getLocation().getMiddleNeighbor().getLeftNeighbor().getLeftNeighbor()).isPresent();
		return hasLeftNeighbor && hasFarLeftNeighbor && hasMiddleNeighbor && hasLeftToMiddleNeighbor && hasFarLeftToMiddleNeighbor;
	}

	private boolean isCompletingBridge(Placement placement) {
		return isCompletingBridgeAtLeftCorner(placement)
				|| isCompletingBridgeAtRightCorner(placement)
				|| isCompletingBridgeAtMiddleCorner(placement);
	}

	private boolean isCompletingBridgeAtLeftCorner(Placement placement) {
		boolean hasFarLeftNeighbor = placementAccessor.getPlacement(placement.getLocation().getLeftNeighbor().getLeftNeighbor()).isPresent();
		boolean hasLeftToMiddleNeighbor = placementAccessor.getPlacement(placement.getLocation().getMiddleNeighbor().getLeftNeighbor()).isPresent();
		boolean hasFarLeftToMiddleNeighbor = placementAccessor.getPlacement(placement.getLocation().getMiddleNeighbor().getLeftNeighbor().getLeftNeighbor()).isPresent();
		return hasFarLeftNeighbor || hasLeftToMiddleNeighbor || hasFarLeftToMiddleNeighbor;
	}

	private boolean isCompletingBridgeAtRightCorner(Placement placement) {
		boolean hasFarRightNeighbor = placementAccessor.getPlacement(placement.getLocation().getRightNeighbor().getRightNeighbor()).isPresent();
		boolean hasRightToMiddleNeighbor = placementAccessor.getPlacement(placement.getLocation().getMiddleNeighbor().getRightNeighbor()).isPresent();
		boolean hasFarRightToMiddleNeighbor = placementAccessor.getPlacement(placement.getLocation().getMiddleNeighbor().getRightNeighbor().getRightNeighbor()).isPresent();

		return hasFarRightNeighbor || hasRightToMiddleNeighbor|| hasFarRightToMiddleNeighbor;
	}

	private boolean isCompletingBridgeAtMiddleCorner(Placement placement) {
		Location oppositeLocation = placement.getLocation().getLeftNeighbor().getMiddleNeighbor().getRightNeighbor();
		boolean hasOppositeNeighbor = placementAccessor.getPlacement(oppositeLocation).isPresent();
		boolean hasLeftToOppositeNeighbor = placementAccessor.getPlacement(oppositeLocation.getLeftNeighbor()).isPresent();
		boolean hasRightToOppositeNeighbor = placementAccessor.getPlacement(oppositeLocation.getRightNeighbor()).isPresent();
		return hasOppositeNeighbor || hasLeftToOppositeNeighbor || hasRightToOppositeNeighbor;
	}

}

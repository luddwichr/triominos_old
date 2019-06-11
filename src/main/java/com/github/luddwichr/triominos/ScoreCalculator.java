package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.board.PlacementAccessor;
import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Neighbor;
import com.github.luddwichr.triominos.tile.Placement;

import java.util.EnumSet;

public class ScoreCalculator {

	private static final int HEXAGON_SCORE = 50;
	private static final int BRIDGE_SCORE = 40;
	private static final EnumSet<Neighbor> MIDDLE_CORNER_NEIGHBORS =
			EnumSet.of(Neighbor.RIGHT, Neighbor.LEFT, Neighbor.OPPOSITE, Neighbor.LEFT_TO_OPPOSITE, Neighbor.RIGHT_TO_OPPOSITE);
	private static final EnumSet<Neighbor> RIGHT_CORNER_NEIGHBORS =
			EnumSet.of(Neighbor.RIGHT, Neighbor.FAR_RIGHT, Neighbor.MIDDLE, Neighbor.RIGHT_TO_MIDDLE, Neighbor.FAR_RIGHT_TO_MIDDLE);
	private static final EnumSet<Neighbor> LEFT_CORNER_NEIGHBORS =
			EnumSet.of(Neighbor.LEFT, Neighbor.FAR_LEFT, Neighbor.MIDDLE, Neighbor.LEFT_TO_MIDDLE, Neighbor.FAR_LEFT_TO_MIDDLE);

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
		return hasAllNeighbors(location, MIDDLE_CORNER_NEIGHBORS);
	}

	private boolean hasFiveNeighborsAtRightCorner(Location location) {
		return hasAllNeighbors(location, RIGHT_CORNER_NEIGHBORS);
	}

	private boolean hasFiveNeighborsAtLeftCorner(Location location) {
		return hasAllNeighbors(location, LEFT_CORNER_NEIGHBORS);
	}

	private boolean hasAllNeighbors(Location location, EnumSet<Neighbor> neighbors) {
		return neighbors.stream().allMatch(neighbor -> hasNeighbor(neighbor, location));
	}

	private boolean isCompletingBridge(Location location) {
		int directNeighbors = numberOfDirectNeighbors(location);

		return directNeighbors < 3 && hasAnyNeighborAtMiddleCorner(location) && hasAnyNeighborAtRightCorner(location) && hasAnyNeighborAtLeftCorner(location);
	}

	private boolean hasAnyNeighborAtMiddleCorner(Location location) {
		return hasAnyNeighbor(location, MIDDLE_CORNER_NEIGHBORS);
	}

	private boolean hasAnyNeighborAtRightCorner(Location location) {
		return hasAnyNeighbor(location, RIGHT_CORNER_NEIGHBORS);
	}

	private boolean hasAnyNeighborAtLeftCorner(Location location) {
		return hasAnyNeighbor(location, LEFT_CORNER_NEIGHBORS);
	}

	private int numberOfDirectNeighbors(Location location) {
		int directNeighbors = 0;
		if (hasNeighbor(Neighbor.LEFT, location)) {
			directNeighbors++;
		}
		if (hasNeighbor(Neighbor.RIGHT, location)) {
			directNeighbors++;
		}
		if (hasNeighbor(Neighbor.MIDDLE, location)) {
			directNeighbors++;
		}
		return directNeighbors;
	}

	private boolean hasAnyNeighbor(Location location, EnumSet<Neighbor> neighbors) {
		return neighbors.stream().anyMatch(neighbor -> hasNeighbor(neighbor, location));
	}

	private boolean hasNeighbor(Neighbor neighbor, Location location) {
		return placementAccessor.get().getPlacement(neighbor.relativeTo(location)).isPresent();
	}

}

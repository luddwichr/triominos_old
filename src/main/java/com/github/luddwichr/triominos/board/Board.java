package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Placement;

import java.util.*;

public class Board {

	private static final Location UP_CENTER_TILE_LOCATION = new Location(0, 0);
	private static final Location DOWN_CENTER_TILE_LOCATION = new Location(0, 1);
	private final Map<Location, Placement> placements = new HashMap<>();

	public void placeTile(Placement placement) {
		if (!isValidPlacement(placement)) {
			throw new IllegalPlacementException();
		}
		placements.put(placement.getLocation(), placement);
	}

	public boolean isValidPlacement(Placement placement) {
		if (placements.isEmpty()) {
			return isFirstTileLocationCentered(placement);
		}
		if (placements.get(placement.getLocation()) != null) {
			return false;
		}
		return hasMatchingLeftNeighbor(placement) || hasMatchingRightNeighbor(placement) || hasMatchingMiddleNeighbor(placement);
	}

	private boolean hasMatchingLeftNeighbor(Placement placement) {
		Location leftNeighbor = new Location(placement.getLocation().getRow(), placement.getLocation().getColumn() - 1);
		return Optional.ofNullable(placements.get(leftNeighbor))
				.map(left -> left.getRightFace().matches(placement.getLeftFace()))
				.orElse(false);
	}

	private boolean hasMatchingRightNeighbor(Placement placement) {
		Location rightNeighbor = new Location(placement.getLocation().getRow(), placement.getLocation().getColumn() + 1);
		return Optional.ofNullable(placements.get(rightNeighbor))
				.map(right -> right.getLeftFace().matches(placement.getRightFace()))
				.orElse(false);

	}

	private boolean hasMatchingMiddleNeighbor(Placement placement) {
		Location middleNeighbor = new Location(placement.getLocation().getRow() + (placement.getLocation().isFacingUp() ? -1 : 1), placement.getLocation().getColumn());
		return Optional.ofNullable(placements.get(middleNeighbor))
				.map(middle -> middle.getMiddleFace().matches(placement.getMiddleFace()))
				.orElse(false);
	}

	private boolean isFirstTileLocationCentered(Placement placement) {
		return placement.getLocation().equals(UP_CENTER_TILE_LOCATION) || placement.getLocation().equals(DOWN_CENTER_TILE_LOCATION);
	}

	public Collection<Placement> getTilePlacements() {
		return placements.values();
	}
}

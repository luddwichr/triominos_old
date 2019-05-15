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
		if (isAlreadyTaken(placement)) {
			return false;
		}
		if (isNotAdjacentToExistingPlacement(placement)) {
			return false;
		}
		return leftNeighborFits(placement) && rightNeighborFits(placement) && middleNeighborFits(placement);
	}

	private boolean isNotAdjacentToExistingPlacement(Placement placement) {
		return !placements.containsKey(placement.getLocation().getLeftNeighbor())
				&& !placements.containsKey(placement.getLocation().getRightNeighbor())
				&& !placements.containsKey(placement.getLocation().getMiddleNeighbor());
	}

	private boolean isAlreadyTaken(Placement placement) {
		return placements.get(placement.getLocation()) != null;
	}

	private boolean leftNeighborFits(Placement placement) {
		return Optional.ofNullable(placements.get(placement.getLocation().getLeftNeighbor()))
				.map(leftNeighbor -> leftNeighbor.getRightFace().matches(placement.getLeftFace()))
				.orElse(true);
	}

	private boolean rightNeighborFits(Placement placement) {
		return Optional.ofNullable(placements.get(placement.getLocation().getRightNeighbor()))
				.map(rightNeighbor -> rightNeighbor.getLeftFace().matches(placement.getRightFace()))
				.orElse(true);

	}

	private boolean middleNeighborFits(Placement placement) {
		return Optional.ofNullable(placements.get(placement.getLocation().getMiddleNeighbor()))
				.map(middleNeighbor -> middleNeighbor.getMiddleFace().matches(placement.getMiddleFace()))
				.orElse(true);
	}

	private boolean isFirstTileLocationCentered(Placement placement) {
		return placement.getLocation().equals(UP_CENTER_TILE_LOCATION) || placement.getLocation().equals(DOWN_CENTER_TILE_LOCATION);
	}

	public Collection<Placement> getTilePlacements() {
		return placements.values();
	}
}

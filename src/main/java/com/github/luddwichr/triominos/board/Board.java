package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.RotatedCorner;
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
		return leftCornerFits(placement) && rightCornerFits(placement) && middleCornerFits(placement);
	}

	private boolean isFirstTileLocationCentered(Placement placement) {
		return placement.getLocation().equals(UP_CENTER_TILE_LOCATION) || placement.getLocation().equals(DOWN_CENTER_TILE_LOCATION);
	}

	private boolean isAlreadyTaken(Placement placement) {
		return placements.get(placement.getLocation()) != null;
	}

	private boolean isNotAdjacentToExistingPlacement(Placement placement) {
		return !placements.containsKey(placement.getLocation().getLeftNeighbor())
				&& !placements.containsKey(placement.getLocation().getRightNeighbor())
				&& !placements.containsKey(placement.getLocation().getMiddleNeighbor());
	}

	private boolean rightCornerFits(Placement placement) {
		int cornerValue = placement.getValueOfRotatedCorner(RotatedCorner.RIGHT);
		Location location = placement.getLocation();
		if (isNotMatchingCorner(location.getRightNeighbor(), RotatedCorner.MIDDLE, cornerValue)) {
			return false;
		}
		if (isNotMatchingCorner(location.getRightNeighbor().getRightNeighbor(), RotatedCorner.LEFT, cornerValue)) {
			return false;
		}
		if (isNotMatchingCorner(location.getMiddleNeighbor(), RotatedCorner.RIGHT, cornerValue)) {
			return false;
		}
		if (isNotMatchingCorner(location.getMiddleNeighbor().getRightNeighbor(), RotatedCorner.MIDDLE, cornerValue)) {
			return false;
		}
		return !isNotMatchingCorner(location.getMiddleNeighbor().getRightNeighbor().getRightNeighbor(), RotatedCorner.LEFT, cornerValue);
	}

	private boolean leftCornerFits(Placement placement) {
		int cornerValue = placement.getValueOfRotatedCorner(RotatedCorner.LEFT);
		Location location = placement.getLocation();
		if (isNotMatchingCorner(location.getLeftNeighbor(), RotatedCorner.MIDDLE, cornerValue)) {
			return false;
		}
		if (isNotMatchingCorner(location.getLeftNeighbor().getLeftNeighbor(), RotatedCorner.RIGHT, cornerValue)) {
			return false;
		}
		if (isNotMatchingCorner(location.getMiddleNeighbor(), RotatedCorner.LEFT, cornerValue)) {
			return false;
		}
		if (isNotMatchingCorner(location.getMiddleNeighbor().getLeftNeighbor(), RotatedCorner.MIDDLE, cornerValue)) {
			return false;
		}
		return !isNotMatchingCorner(location.getMiddleNeighbor().getLeftNeighbor().getLeftNeighbor(), RotatedCorner.RIGHT, cornerValue);
	}

	private boolean middleCornerFits(Placement placement) {
		int cornerValue = placement.getValueOfRotatedCorner(RotatedCorner.MIDDLE);
		Location location = placement.getLocation();

		if (isNotMatchingCorner(location.getLeftNeighbor(), RotatedCorner.RIGHT, cornerValue)) {
			return false;
		}
		if (isNotMatchingCorner(location.getRightNeighbor(), RotatedCorner.LEFT, cornerValue)) {
			return false;
		}
		if (isNotMatchingCorner(location.getLeftNeighbor().getMiddleNeighbor().getRightNeighbor(), RotatedCorner.MIDDLE, cornerValue)) {
			return false;
		}
		if (isNotMatchingCorner(location.getLeftNeighbor().getMiddleNeighbor(), RotatedCorner.RIGHT, cornerValue)) {
			return false;
		}
		return !isNotMatchingCorner(location.getRightNeighbor().getMiddleNeighbor(), RotatedCorner.LEFT, cornerValue);
	}

	private boolean isNotMatchingCorner(Location location, RotatedCorner rotatedCorner, int value) {
		Placement neighbor = placements.get(location);
		return neighbor != null && neighbor.getValueOfRotatedCorner(rotatedCorner) != value;
	}

	public Collection<Placement> getTilePlacements() {
		return placements.values();
	}
}

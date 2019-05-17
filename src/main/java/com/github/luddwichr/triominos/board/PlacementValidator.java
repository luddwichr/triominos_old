package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Placement;
import com.github.luddwichr.triominos.tile.RotatedCorner;

import java.util.Optional;

public class PlacementValidator {

	private ThreadLocal<PlacementAccessor> placementAccessor = new ThreadLocal<>();

	public boolean isValidPlacement(PlacementAccessor placementAccessor, Placement placement) {
		this.placementAccessor.set(placementAccessor);
		if (isAlreadyTaken(placement)) {
			return false;
		}
		if (isNotAdjacentToExistingPlacement(placement)) {
			return false;
		}
		return leftCornerFits(placement) && rightCornerFits(placement) && middleCornerFits(placement);
	}

	private boolean isAlreadyTaken(Placement placement) {
		return getPlacement(placement.getLocation()).isPresent();
	}

	private boolean isNotAdjacentToExistingPlacement(Placement placement) {
		return getPlacement(placement.getLocation().getLeftNeighbor()).isEmpty()
				&& getPlacement(placement.getLocation().getRightNeighbor()).isEmpty()
				&& getPlacement(placement.getLocation().getMiddleNeighbor()).isEmpty();
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
		return getPlacement(location)
				.map(neighbor -> neighbor.getValueOfRotatedCorner(rotatedCorner) != value)
				.orElse(false);
	}

	private Optional<Placement> getPlacement(Location location) {
		return placementAccessor.get().getPlacement(location);
	}

}

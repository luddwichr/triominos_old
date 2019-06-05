package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.Corner;
import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Placement;

import java.util.Optional;

public class PlacementValidator {

	private final ThreadLocal<PlacementAccessor> placementAccessor = new ThreadLocal<>();

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
		int cornerNumber = placement.getRotatedNumber(Corner.RIGHT);
		Location location = placement.getLocation();
		if (isNotMatchingCorner(location.getRightNeighbor(), Corner.MIDDLE, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(location.getRightNeighbor().getRightNeighbor(), Corner.LEFT, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(location.getMiddleNeighbor(), Corner.RIGHT, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(location.getMiddleNeighbor().getRightNeighbor(), Corner.MIDDLE, cornerNumber)) {
			return false;
		}
		return !isNotMatchingCorner(location.getMiddleNeighbor().getRightNeighbor().getRightNeighbor(), Corner.LEFT, cornerNumber);
	}

	private boolean leftCornerFits(Placement placement) {
		int cornerNumber = placement.getRotatedNumber(Corner.LEFT);
		Location location = placement.getLocation();
		if (isNotMatchingCorner(location.getLeftNeighbor(), Corner.MIDDLE, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(location.getLeftNeighbor().getLeftNeighbor(), Corner.RIGHT, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(location.getMiddleNeighbor(), Corner.LEFT, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(location.getMiddleNeighbor().getLeftNeighbor(), Corner.MIDDLE, cornerNumber)) {
			return false;
		}
		return !isNotMatchingCorner(location.getMiddleNeighbor().getLeftNeighbor().getLeftNeighbor(), Corner.RIGHT, cornerNumber);
	}

	private boolean middleCornerFits(Placement placement) {
		int cornerNumber = placement.getRotatedNumber(Corner.MIDDLE);
		Location location = placement.getLocation();

		if (isNotMatchingCorner(location.getLeftNeighbor(), Corner.RIGHT, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(location.getRightNeighbor(), Corner.LEFT, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(location.getLeftNeighbor().getMiddleNeighbor().getRightNeighbor(), Corner.MIDDLE, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(location.getLeftNeighbor().getMiddleNeighbor(), Corner.RIGHT, cornerNumber)) {
			return false;
		}
		return !isNotMatchingCorner(location.getRightNeighbor().getMiddleNeighbor(), Corner.LEFT, cornerNumber);
	}

	private boolean isNotMatchingCorner(Location location, Corner corner, int cornerNumber) {
		return getPlacement(location)
				.map(neighbor -> neighbor.getRotatedNumber(corner) != cornerNumber)
				.orElse(false);
	}

	private Optional<Placement> getPlacement(Location location) {
		return placementAccessor.get().getPlacement(location);
	}

}

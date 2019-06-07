package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.Corner;
import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Neighbor;
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
		Location location = placement.getLocation();
		return isNotPlacementExisting(Neighbor.LEFT.relativeTo(location)) &&
				isNotPlacementExisting(Neighbor.RIGHT.relativeTo(location)) &&
				isNotPlacementExisting(Neighbor.MIDDLE.relativeTo(location));
	}

	private boolean rightCornerFits(Placement placement) {
		int cornerNumber = placement.getRotatedNumber(Corner.RIGHT);
		Location location = placement.getLocation();
		if (isNotMatchingCorner(Neighbor.RIGHT.relativeTo(location), Corner.MIDDLE, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(Neighbor.FAR_RIGHT.relativeTo(location), Corner.LEFT, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(Neighbor.MIDDLE.relativeTo(location), Corner.RIGHT, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(Neighbor.RIGHT_TO_MIDDLE.relativeTo(location), Corner.MIDDLE, cornerNumber)) {
			return false;
		}
		return !isNotMatchingCorner(Neighbor.FAR_RIGHT_TO_MIDDLE.relativeTo(location), Corner.LEFT, cornerNumber);
	}

	private boolean leftCornerFits(Placement placement) {
		int cornerNumber = placement.getRotatedNumber(Corner.LEFT);
		Location location = placement.getLocation();
		if (isNotMatchingCorner(Neighbor.LEFT.relativeTo(location), Corner.MIDDLE, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(Neighbor.FAR_LEFT.relativeTo(location), Corner.RIGHT, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(Neighbor.MIDDLE.relativeTo(location), Corner.LEFT, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(Neighbor.LEFT_TO_MIDDLE.relativeTo(location), Corner.MIDDLE, cornerNumber)) {
			return false;
		}
		return !isNotMatchingCorner(Neighbor.FAR_LEFT_TO_MIDDLE.relativeTo(location), Corner.RIGHT, cornerNumber);
	}

	private boolean middleCornerFits(Placement placement) {
		int cornerNumber = placement.getRotatedNumber(Corner.MIDDLE);
		Location location = placement.getLocation();

		if (isNotMatchingCorner(Neighbor.LEFT.relativeTo(location), Corner.RIGHT, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(Neighbor.RIGHT.relativeTo(location), Corner.LEFT, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(Neighbor.OPPOSITE.relativeTo(location), Corner.MIDDLE, cornerNumber)) {
			return false;
		}
		if (isNotMatchingCorner(Neighbor.LEFT_TO_OPPOSITE.relativeTo(location), Corner.RIGHT, cornerNumber)) {
			return false;
		}
		return !isNotMatchingCorner(Neighbor.RIGHT_TO_OPPOSITE.relativeTo(location), Corner.LEFT, cornerNumber);
	}

	private boolean isNotMatchingCorner(Location location, Corner corner, int cornerNumber) {
		return getPlacement(location)
				.map(neighbor -> neighbor.getRotatedNumber(corner) != cornerNumber)
				.orElse(false);
	}

	private boolean isNotPlacementExisting(Location location) {
		return getPlacement(location).isEmpty();
	}

	private Optional<Placement> getPlacement(Location location) {
		return placementAccessor.get().getPlacement(location);
	}

}

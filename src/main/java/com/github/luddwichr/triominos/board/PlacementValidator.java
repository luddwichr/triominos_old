package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.Corner;
import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Neighbor;
import com.github.luddwichr.triominos.tile.Placement;

import java.util.Map;
import java.util.Optional;

public class PlacementValidator {

	private final ThreadLocal<PlacementAccessor> placementAccessor = new ThreadLocal<>();
	private static final Map<Neighbor, Corner> CORNER_TO_MATCH_PER_MIDLE_CORNER_NEIGHBOR = Map.of(
			Neighbor.LEFT, Corner.RIGHT,
			Neighbor.RIGHT, Corner.LEFT,
			Neighbor.OPPOSITE, Corner.MIDDLE,
			Neighbor.LEFT_TO_OPPOSITE, Corner.RIGHT,
			Neighbor.RIGHT_TO_OPPOSITE, Corner.LEFT);
	private static final Map<Neighbor, Corner> CORNER_TO_MATCH_PER_LEFT_CORNER_NEIGHBOR = Map.of(
			Neighbor.LEFT, Corner.MIDDLE,
			Neighbor.FAR_LEFT, Corner.RIGHT,
			Neighbor.MIDDLE, Corner.LEFT,
			Neighbor.LEFT_TO_MIDDLE, Corner.MIDDLE,
			Neighbor.FAR_LEFT_TO_MIDDLE, Corner.RIGHT);
	private static final Map<Neighbor, Corner> CORNER_TO_MATCH_PER_RIGHT_CORNER_NEIGHBOR = Map.of(
			Neighbor.RIGHT, Corner.MIDDLE,
			Neighbor.FAR_RIGHT, Corner.LEFT,
			Neighbor.MIDDLE, Corner.RIGHT,
			Neighbor.RIGHT_TO_MIDDLE, Corner.MIDDLE,
			Neighbor.FAR_RIGHT_TO_MIDDLE, Corner.LEFT);

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
		return isAllCornersMatching(placement.getLocation(), placement.getRotatedNumber(Corner.RIGHT), CORNER_TO_MATCH_PER_RIGHT_CORNER_NEIGHBOR);
	}

	private boolean leftCornerFits(Placement placement) {
		return isAllCornersMatching(placement.getLocation(), placement.getRotatedNumber(Corner.LEFT), CORNER_TO_MATCH_PER_LEFT_CORNER_NEIGHBOR);
	}

	private boolean middleCornerFits(Placement placement) {
		return isAllCornersMatching(placement.getLocation(), placement.getRotatedNumber(Corner.MIDDLE), CORNER_TO_MATCH_PER_MIDLE_CORNER_NEIGHBOR);
	}

	private boolean isAllCornersMatching(Location location, int cornerNumber, Map<Neighbor, Corner> cornerToMatchPerNeighbor) {
		return cornerToMatchPerNeighbor.entrySet().stream()
				.allMatch(cornerMatchRule -> isMatchingCorner(cornerMatchRule.getKey().relativeTo(location), cornerMatchRule.getValue(), cornerNumber)
		);
	}

	private boolean isMatchingCorner(Location location, Corner corner, int cornerNumber) {
		return getPlacement(location)
				.map(neighbor -> neighbor.getRotatedNumber(corner) == cornerNumber)
				.orElse(true);
	}

	private boolean isNotPlacementExisting(Location location) {
		return getPlacement(location).isEmpty();
	}

	private Optional<Placement> getPlacement(Location location) {
		return placementAccessor.get().getPlacement(location);
	}

}

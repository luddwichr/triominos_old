package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.Corner;
import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Neighbor;
import com.github.luddwichr.triominos.tile.Placement;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PlacementValidator {

	private static final Location UP_CENTER_TILE_LOCATION = Location.at(0, 0);
	private static final Location DOWN_CENTER_TILE_LOCATION = Location.at(1, 0);

	private static class CornerMatchRule {
		final Neighbor neighbor;
		final Corner corner;

		private CornerMatchRule(Neighbor neighbor, Corner corner) {
			this.neighbor = neighbor;
			this.corner = corner;
		}
	}

	private static final List<CornerMatchRule> CORNER_TO_MATCH_PER_MIDDLE_CORNER_NEIGHBOR = Arrays.asList(
			new CornerMatchRule(Neighbor.LEFT, Corner.RIGHT),
			new CornerMatchRule(Neighbor.RIGHT, Corner.LEFT),
			new CornerMatchRule(Neighbor.OPPOSITE, Corner.MIDDLE),
			new CornerMatchRule(Neighbor.LEFT_TO_OPPOSITE, Corner.RIGHT),
			new CornerMatchRule(Neighbor.RIGHT_TO_OPPOSITE, Corner.LEFT)
	);
	private static final List<CornerMatchRule> CORNER_TO_MATCH_PER_LEFT_CORNER_NEIGHBOR = Arrays.asList(
			new CornerMatchRule(Neighbor.LEFT, Corner.MIDDLE),
			new CornerMatchRule(Neighbor.FAR_LEFT, Corner.RIGHT),
			new CornerMatchRule(Neighbor.MIDDLE, Corner.LEFT),
			new CornerMatchRule(Neighbor.LEFT_TO_MIDDLE, Corner.MIDDLE),
			new CornerMatchRule(Neighbor.FAR_LEFT_TO_MIDDLE, Corner.RIGHT)
	);
	private static final List<CornerMatchRule> CORNER_TO_MATCH_PER_RIGHT_CORNER_NEIGHBOR = Arrays.asList(
			new CornerMatchRule(Neighbor.RIGHT, Corner.MIDDLE),
			new CornerMatchRule(Neighbor.FAR_RIGHT, Corner.LEFT),
			new CornerMatchRule(Neighbor.MIDDLE, Corner.RIGHT),
			new CornerMatchRule(Neighbor.RIGHT_TO_MIDDLE, Corner.MIDDLE),
			new CornerMatchRule(Neighbor.FAR_RIGHT_TO_MIDDLE, Corner.LEFT)
	);
	private static final Map<Corner, List<CornerMatchRule>> CORNER_MATCH_RULES = Map.of(
			Corner.MIDDLE, CORNER_TO_MATCH_PER_MIDDLE_CORNER_NEIGHBOR,
			Corner.LEFT, CORNER_TO_MATCH_PER_LEFT_CORNER_NEIGHBOR,
			Corner.RIGHT, CORNER_TO_MATCH_PER_RIGHT_CORNER_NEIGHBOR
	);

	private final ThreadLocal<Board> threadSafeBoard = new ThreadLocal<>();

	public boolean isValidPlacement(Board board, Placement placement) {
		this.threadSafeBoard.set(board);
		if (board.isEmpty()) {
			return isFirstTileLocationCentered(placement);
		}
		if (isTileAlreadyPlaced(placement)) {
			return false;
		}
		if (isAlreadyTaken(placement)) {
			return false;
		}
		if (!isAdjacentToExistingPlacement(placement)) {
			return false;
		}
		return areAllCornersFitting(placement);
	}

	private boolean areAllCornersFitting(Placement placement) {
		return isLeftCornerFitting(placement) && isRightCornerFitting(placement) && isMiddleCornerFitting(placement);
	}

	private boolean isTileAlreadyPlaced(Placement placement) {
		return getBoard().getPlacements().stream().anyMatch(existingPlacement -> existingPlacement.getTile().equals(placement.getTile()));
	}

	private boolean isFirstTileLocationCentered(Placement placement) {
		return placement.getLocation().equals(UP_CENTER_TILE_LOCATION) || placement.getLocation().equals(DOWN_CENTER_TILE_LOCATION);
	}

	private boolean isAlreadyTaken(Placement placement) {
		return isPlacementExisting(placement.getLocation());
	}

	private boolean isAdjacentToExistingPlacement(Placement placement) {
		Location location = placement.getLocation();
		return isPlacementExisting(Neighbor.LEFT.relativeTo(location))
				|| isPlacementExisting(Neighbor.RIGHT.relativeTo(location))
				|| isPlacementExisting(Neighbor.MIDDLE.relativeTo(location));
	}

	private boolean isRightCornerFitting(Placement placement) {
		return areAllCornersMatching(placement, Corner.RIGHT);
	}

	private boolean isLeftCornerFitting(Placement placement) {
		return areAllCornersMatching(placement, Corner.LEFT);
	}

	private boolean isMiddleCornerFitting(Placement placement) {
		return areAllCornersMatching(placement, Corner.MIDDLE);
	}

	private boolean areAllCornersMatching(Placement placement, Corner corner) {
		int cornerNumber = placement.getRotatedNumber(corner);
		Location location = placement.getLocation();
		return CORNER_MATCH_RULES.get(corner).stream()
				.allMatch(cornerMatchRule -> isMatchingCorner(location, cornerMatchRule, cornerNumber));
	}

	private boolean isMatchingCorner(Location location, CornerMatchRule cornerMatchRule, int cornerNumber) {
		return getBoard().getPlacement(cornerMatchRule.neighbor.relativeTo(location))
				.map(neighborPlacement -> neighborPlacement.getRotatedNumber(cornerMatchRule.corner) == cornerNumber)
				.orElse(true);
	}

	private boolean isPlacementExisting(Location location) {
		return getBoard().getPlacement(location).isPresent();
	}

	private Board getBoard() {
		return threadSafeBoard.get();
	}
}

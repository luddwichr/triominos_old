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
		if (!(leftNeighborFits(placement) && rightNeighborFits(placement) && middleNeighborFits(placement))) {
			return false;
		}
		return rightCornerFits(placement) && leftCornerFits(placement) && middleCornerFits(placement);
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

	private boolean rightCornerFits(Placement placement) {
		// TODO: write test for all edge cases, refactor
		int rightCorner = placement.getRightCorner();
		Placement twoToTheRight = placements.get(placement.getLocation().getRightNeighbor().getRightNeighbor());
		if (twoToTheRight != null && twoToTheRight.getLeftCorner() != rightCorner) {
			return false;
		}
		Placement twoToTheRightOfMiddle = placements.get(placement.getLocation().getMiddleNeighbor().getRightNeighbor().getRightNeighbor());
		if (twoToTheRightOfMiddle != null && twoToTheRightOfMiddle.getLeftCorner() != rightCorner) {
			return false;
		}
		Placement oneToTheRightOfMiddle = placements.get(placement.getLocation().getMiddleNeighbor().getRightNeighbor());
		return oneToTheRightOfMiddle == null || oneToTheRightOfMiddle.getMiddleCorner() == rightCorner;
	}

	private boolean leftCornerFits(Placement placement) {
		// TODO: write test for all edge cases, refactor
		int leftCorner = placement.getLeftCorner();
		Placement twoToTheLeft = placements.get(placement.getLocation().getLeftNeighbor().getLeftNeighbor());
		if (twoToTheLeft != null && twoToTheLeft.getRightCorner() != leftCorner) {
			return false;
		}
		Placement twoToTheLeftOfMiddle = placements.get(placement.getLocation().getMiddleNeighbor().getLeftNeighbor().getLeftNeighbor());
		if (twoToTheLeftOfMiddle != null && twoToTheLeftOfMiddle.getRightCorner() != leftCorner) {
			return false;
		}
		Placement oneToTheLeftOfMiddle = placements.get(placement.getLocation().getMiddleNeighbor().getLeftNeighbor());
		return oneToTheLeftOfMiddle == null || oneToTheLeftOfMiddle.getMiddleCorner() == leftCorner;
	}

	private boolean middleCornerFits(Placement placement) {
		// TODO: write tests, refactor
		int middleCorner = placement.getMiddleCorner();
		Placement leftToOpposite = placements.get(placement.getLocation().getLeftNeighbor().getMiddleNeighbor());
		if (leftToOpposite != null && leftToOpposite.getRightCorner() != middleCorner) {
			return false;
		}
		Placement rightToOpposite = placements.get(placement.getLocation().getRightNeighbor().getMiddleNeighbor());
		if (rightToOpposite != null && rightToOpposite.getLeftCorner() != middleCorner) {
			return false;
		}
		Placement opposite = placements.get(placement.getLocation().getLeftNeighbor().getMiddleNeighbor().getRightNeighbor());
		return opposite == null || opposite.getMiddleCorner() == middleCorner;
	}

	public Collection<Placement> getTilePlacements() {
		return placements.values();
	}
}

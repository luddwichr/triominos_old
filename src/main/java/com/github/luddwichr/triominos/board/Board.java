package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Placement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Board {

	private static final Location UP_CENTER_TILE_LOCATION = new Location(0, 0);
	private static final Location DOWN_CENTER_TILE_LOCATION = new Location(0, 1);
	private final List<Placement> placements = new ArrayList<>();

	public void placeTile(Placement placement) {
		if (placements.isEmpty()) {
			verifyFirstTileLocationIsCentered(placement);
		} else {
			verifyPlacementNotOccupied(placement);
			verifyValidNeighbor(placement);
		}
		placements.add(placement);
	}

	private void verifyValidNeighbor(Placement placement) {
		Optional<Placement> leftNeighbor = findLeftNeighbor(placement);
		Optional<Placement> rightNeighbor = findRightNeighbor(placement);
		Optional<Placement> middleNeighbor = findMiddleNeighbor(placement);
		leftNeighbor.ifPresent(left -> {
			if (!left.getRightFace().matches(placement.getLeftFace())) {
				throw new IllegalPlacementException("Placement does not match edges of adjacent placement!");
			}
		});
		rightNeighbor.ifPresent(right -> {
			if (!right.getLeftFace().matches(placement.getRightFace())) {
				throw new IllegalPlacementException("Placement does not match edges of adjacent placement!");
			}
		});
		middleNeighbor.ifPresent(middle -> {
			if (!middle.getMiddleFace().matches(placement.getMiddleFace())) {
				throw new IllegalPlacementException("Placement does not match edges of adjacent placement!");
			}
		});
		if (leftNeighbor.isEmpty() && rightNeighbor.isEmpty() && middleNeighbor.isEmpty()) {
			throw new IllegalPlacementException("Placement is not adjacent to any existing placement!");
		}
	}

	private Optional<Placement> findLeftNeighbor(Placement placement) {
		Location left = new Location(placement.getLocation().getRow(), placement.getLocation().getColumn() - 1);
		return placements.stream().filter(existingPlacement -> existingPlacement.getLocation().equals(left)).findFirst();
	}

	private Optional<Placement> findRightNeighbor(Placement placement) {
		Location right = new Location(placement.getLocation().getRow(), placement.getLocation().getColumn() + 1);
		return placements.stream().filter(existingPlacement -> existingPlacement.getLocation().equals(right)).findFirst();
	}

	private Optional<Placement> findMiddleNeighbor(Placement placement) {
		Location middle = new Location(placement.getLocation().getRow() + (placement.getLocation().isFacingUp() ? -1 : 1), placement.getLocation().getColumn());
		return placements.stream().filter(existingPlacement -> existingPlacement.getLocation().equals(middle)).findFirst();
	}

	private void verifyFirstTileLocationIsCentered(Placement placement) {
		if (!placement.getLocation().equals(UP_CENTER_TILE_LOCATION) &&
				!placement.getLocation().equals(DOWN_CENTER_TILE_LOCATION)) {
			throw new IllegalPlacementException("First placement must be located at 0/0 when facing up or 0/1 when facing down!");
		}

	}

	private void verifyPlacementNotOccupied(Placement placement) {
		if (placements.stream().anyMatch(existingPlacement -> existingPlacement.getLocation().equals(placement.getLocation()))) {
			throw new IllegalPlacementException("Placement is already occupied!");
		}
	}

	public List<Placement> getTilePlacements() {
		return Collections.unmodifiableList(placements);
	}
}

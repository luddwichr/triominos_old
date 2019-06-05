package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Placement;

import java.util.*;

public class Board {

	private static final Location UP_CENTER_TILE_LOCATION = new Location(0, 0);
	private static final Location DOWN_CENTER_TILE_LOCATION = new Location(0, 1);
	private final Map<Location, Placement> placements = new HashMap<>();
	private final PlacementValidator placementValidator;

	public Board(PlacementValidator placementValidator) {
		this.placementValidator = placementValidator;
	}

	public boolean isEmpty() {
		return placements.isEmpty();
	}

	public Optional<Placement> getPlacement(Location location) {
		return Optional.ofNullable(placements.get(location));
	}

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
		if (isTileAlreadyPlaced(placement)) {
			return false;
		}
		return placementValidator.isValidPlacement(this::getPlacement, placement);
	}

	private boolean isTileAlreadyPlaced(Placement placement) {
		return placements.values().stream().anyMatch(existingPlacement -> existingPlacement.getTile().equals(placement.getTile()));
	}

	private boolean isFirstTileLocationCentered(Placement placement) {
		return placement.getLocation().equals(UP_CENTER_TILE_LOCATION)
				|| placement.getLocation().equals(DOWN_CENTER_TILE_LOCATION);
	}

	public Collection<Placement> getPlacements() {
		return Collections.unmodifiableCollection(placements.values());
	}

}

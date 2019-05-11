package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Placement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {

	private static final Location UP_CENTER_TILE_LOCATION = new Location(0, 0);
	private static final Location DOWN_CENTER_TILE_LOCATION = new Location(0, 1);
	private final List<Placement> placements = new ArrayList<>();

	public Placement placeTile(Placement placement) {
		if (placements.isEmpty()) {
			return placeFirstTile(placement);
		}
		placements.add(placement);
		return placement;
	}

	private Placement placeFirstTile(Placement placement) {
		Placement centeredPlacement = ensureCenterLocation(placement);
		placements.add(centeredPlacement);
		return centeredPlacement;
	}

	private Placement ensureCenterLocation(Placement placement) {
		if (placement.getOrientation().isFacingUp()) {
			return new Placement(placement.getTile(), placement.getOrientation(), UP_CENTER_TILE_LOCATION);
		}
		return new Placement(placement.getTile(), placement.getOrientation(), DOWN_CENTER_TILE_LOCATION);
	}

	public List<Placement> getTilePlacements() {
		return Collections.unmodifiableList(placements);
	}
}

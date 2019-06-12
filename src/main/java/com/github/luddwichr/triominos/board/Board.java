package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Placement;

import java.util.*;

public class Board {

	private final Map<Location, Placement> placements = new HashMap<>();

	public boolean isEmpty() {
		return placements.isEmpty();
	}

	public Optional<Placement> getPlacement(Location location) {
		return Optional.ofNullable(placements.get(location));
	}

	public void placeTile(Placement placement) {
		placements.put(placement.getLocation(), placement);
	}

	public Collection<Placement> getPlacements() {
		return Collections.unmodifiableCollection(placements.values());
	}

}

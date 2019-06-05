package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Placement;

import java.util.Optional;

@FunctionalInterface
public interface PlacementAccessor {
	Optional<Placement> getPlacement(Location location);
}

package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Placement;

import java.util.Optional;

public interface PlacementAccessor {
	Optional<Placement> getPlacement(Location location);
}

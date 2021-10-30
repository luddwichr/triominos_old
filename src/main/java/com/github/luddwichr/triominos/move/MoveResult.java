package com.github.luddwichr.triominos.move;

import com.github.luddwichr.triominos.tile.Placement;

import java.util.Optional;

public record MoveResult(int tilesDrawn, boolean blocked, Placement placement) {

	public Optional<Placement> maybePlacement() {
		return Optional.ofNullable(placement);
	}
}

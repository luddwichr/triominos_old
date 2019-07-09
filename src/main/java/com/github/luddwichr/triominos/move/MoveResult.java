package com.github.luddwichr.triominos.move;

import com.github.luddwichr.triominos.tile.Placement;

import java.util.Optional;

public class MoveResult {

	private final int tilesDrawn;
	private final boolean blocked;
	private final Placement placement;

	public MoveResult(int tilesDrawn, boolean blocked, Placement placement) {
		this.tilesDrawn = tilesDrawn;
		this.blocked = blocked;
		this.placement = placement;
	}

	public int getTilesDrawn() {
		return tilesDrawn;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public Optional<Placement> getPlacement() {
		return Optional.ofNullable(placement);
	}
}

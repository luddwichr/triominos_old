package com.github.luddwichr.triominos.player;

import com.github.luddwichr.triominos.tile.Placement;

import java.util.Collection;

public interface Player {

	void play(Collection<Placement> existingPlacements, int remainingTilesInPile);

}

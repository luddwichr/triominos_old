package com.github.luddwichr.triominos.tile;

public class Placement {

	private final Tile tile;
	private final Orientation orientation;
	private final Location location;

	public Placement(Tile tile, Orientation orientation, Location location) {
		this.tile = tile;
		this.orientation = orientation;
		this.location = location;
	}

	public Tile getTile() {
		return tile;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public Location getLocation() {
		return location;
	}
}

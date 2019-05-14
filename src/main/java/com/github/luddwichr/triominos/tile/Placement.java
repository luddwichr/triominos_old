package com.github.luddwichr.triominos.tile;

public class Placement {

	private final Tile tile;
	private final Orientation orientation;
	private final Location location;

	public Placement(Tile tile, Orientation orientation, Location location) {
		verifyMatchingOrientationAndLocation(orientation, location);
		this.tile = tile;
		this.orientation = orientation;
		this.location = location;
	}

	private void verifyMatchingOrientationAndLocation(Orientation orientation, Location location) {
		if (orientation.isFacingUp() != location.isFacingUp()) {
			throw new IllegalArgumentException("Orientation and location of placement do not face in the same direction!");
		}
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

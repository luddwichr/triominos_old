package com.github.luddwichr.triominos.tile;

public class Placement {

	private final Tile tile;
	private final Orientation orientation;
	private final Location location;

	public Placement(Tile tile, Orientation orientation, Location location) {
		verifyFacingInSameDirection(orientation, location);
		this.tile = tile;
		this.orientation = orientation;
		this.location = location;
	}

	private void verifyFacingInSameDirection(Orientation orientation, Location location) {
		if (orientation.isFacingUp() != location.isFacingUp()) {
			throw new IllegalArgumentException("Orientation and location of placement do not face in the same direction!");
		}
	}

	public Location getLocation() {
		return location;
	}

	public int getRotatedNumber(Corner corner) {
		return tile.getNumber(orientation.getRotatedCorner(corner));
	}

	public Tile getTile() {
		return tile;
	}
}

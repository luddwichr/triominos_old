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

	public Location getLocation() {
		return location;
	}

	public int getValueOfRotatedCorner(RotatedCorner rotatedCorner) {
		switch (rotatedCorner) {
			case LEFT:
				return tile.getValue(orientation.getLeft());
			case RIGHT:
				return tile.getValue(orientation.getRight());
			case MIDDLE:
				return tile.getValue(orientation.getMiddle());
			default:
				throw new IllegalStateException("Unexpected value: " + rotatedCorner);
		}
	}
}

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

	public Face getLeftFace() {
		return orientation.isFacingUp() ? new Face(getLeftCorner(), getMiddleCorner()) : new Face(getMiddleCorner(), getLeftCorner());
	}

	public Face getRightFace() {
		return orientation.isFacingUp() ? new Face(getMiddleCorner(), getRightCorner()) : new Face(getRightCorner(), getMiddleCorner());
	}

	public Face getMiddleFace() {
		return new Face(getLeftCorner(), getRightCorner());
	}

	private int getLeftCorner() {
		return tile.getValue(orientation.getLeft());
	}

	private int getMiddleCorner() {
		return tile.getValue(orientation.getMiddle());
	}

	private int getRightCorner() {
		return tile.getValue(orientation.getRight());
	}

}

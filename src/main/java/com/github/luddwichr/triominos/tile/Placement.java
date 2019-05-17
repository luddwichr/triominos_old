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

	public Face getLeftFace() {
		return orientation.isFacingUp() ? new Face(getLeftCorner(), getMiddleCorner()) : new Face(getMiddleCorner(), getLeftCorner());
	}

	public Face getRightFace() {
		return orientation.isFacingUp() ? new Face(getMiddleCorner(), getRightCorner()) : new Face(getRightCorner(), getMiddleCorner());
	}

	public Face getMiddleFace() {
		return orientation.isFacingUp() ? new Face(getRightCorner(), getLeftCorner()) : new Face(getLeftCorner(), getRightCorner());
	}

	public int getLeftCorner() {
		return tile.getValue(orientation.getLeft());
	}

	public int getMiddleCorner() {
		return tile.getValue(orientation.getMiddle());
	}

	public int getRightCorner() {
		return tile.getValue(orientation.getRight());
	}

}

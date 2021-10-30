package com.github.luddwichr.triominos.tile;

public record Placement(Tile tile, Orientation orientation, Location location) {

	public Placement {
		verifyFacingInSameDirection(orientation, location);
	}

	private void verifyFacingInSameDirection(Orientation orientation, Location location) {
		if (orientation.isFacingUp() != location.isFacingUp()) {
			throw new IllegalArgumentException("Orientation and location of placement do not face in the same direction!");
		}
	}

	public int rotatedNumber(Corner corner) {
		return tile.getNumber(orientation.getRotatedCorner(corner));
	}

}

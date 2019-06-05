package com.github.luddwichr.triominos.tile;

import java.util.EnumMap;

public enum Orientation {

	ABC(Corner.LEFT, Corner.MIDDLE, Corner.RIGHT, true),
	ACB(Corner.LEFT, Corner.RIGHT, Corner.MIDDLE, false),
	CAB(Corner.RIGHT, Corner.LEFT, Corner.MIDDLE, true),
	CBA(Corner.RIGHT, Corner.MIDDLE, Corner.LEFT, false),
	BCA(Corner.MIDDLE, Corner.RIGHT, Corner.LEFT, true),
	BAC(Corner.MIDDLE, Corner.LEFT, Corner.RIGHT, false);

	private final EnumMap<Corner, Corner> corners = new EnumMap<>(Corner.class);
	private final boolean isFacingUp;

	Orientation(Corner left, Corner middle, Corner right, boolean isFacingUp) {
		corners.put(Corner.LEFT, left);
		corners.put(Corner.MIDDLE, middle);
		corners.put(Corner.RIGHT, right);
		this.isFacingUp = isFacingUp;
	}

	public boolean isFacingUp() {
		return isFacingUp;
	}

	public Corner getRotatedCorner(Corner corner) {
		return corners.get(corner);
	}
}

package com.github.luddwichr.triominos.tile;

import java.util.EnumMap;

public enum Orientation {

	ABC(Corner.A, Corner.B, Corner.C, true),
	ACB(Corner.A, Corner.C, Corner.B, false),
	CAB(Corner.C, Corner.A, Corner.B, true),
	CBA(Corner.C, Corner.B, Corner.A, false),
	BCA(Corner.B, Corner.C, Corner.A, true),
	BAC(Corner.B, Corner.A, Corner.C, false);

	private final EnumMap<RotatedCorner, Corner> corners = new EnumMap<>(RotatedCorner.class);
	private final boolean isFacingUp;

	Orientation(Corner left, Corner middle, Corner right, boolean isFacingUp) {
		corners.put(RotatedCorner.LEFT, left);
		corners.put(RotatedCorner.MIDDLE, middle);
		corners.put(RotatedCorner.RIGHT, right);
		this.isFacingUp = isFacingUp;
	}

	public boolean isFacingUp() {
		return isFacingUp;
	}

	public Corner getRotatedCorner(RotatedCorner rotatedCorner) {
		return corners.get(rotatedCorner);
	}
}

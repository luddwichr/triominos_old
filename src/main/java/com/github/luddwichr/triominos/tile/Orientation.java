package com.github.luddwichr.triominos.tile;

public enum Orientation {

	ABC(Corner.A, Corner.B, Corner.C, true),
	ACB(Corner.A, Corner.C, Corner.B, false),
	CAB(Corner.C, Corner.A, Corner.B, true),
	CBA(Corner.C, Corner.B, Corner.A, false),
	BCA(Corner.B, Corner.C, Corner.A, true),
	BAC(Corner.B, Corner.A, Corner.C, false);
	private final Corner left;
	private final Corner middle;
	private final Corner right;
	private final boolean singleCornerOnTop;

	Orientation(Corner left, Corner middle, Corner right, boolean singleCornerOnTop) {
		this.left = left;
		this.middle = middle;
		this.right = right;
		this.singleCornerOnTop = singleCornerOnTop;
	}

	public boolean opositeDirectionTo(Orientation otherOrientation) {
		return singleCornerOnTop != otherOrientation.singleCornerOnTop;
	}

	public boolean isFacingUp() {
		return singleCornerOnTop;
	}
}

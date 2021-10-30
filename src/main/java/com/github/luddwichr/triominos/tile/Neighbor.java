package com.github.luddwichr.triominos.tile;

public enum Neighbor {

	LEFT(-1, 0, false),
	RIGHT(1, 0, false),
	MIDDLE(0, 1, true),
	FAR_LEFT(-2, 0, false),
	FAR_RIGHT(2, 0, false),
	LEFT_TO_MIDDLE(-1, 1, true),
	FAR_LEFT_TO_MIDDLE(-2, 1, true),
	RIGHT_TO_MIDDLE(1, 1, true),
	FAR_RIGHT_TO_MIDDLE(2, 1, true),
	OPPOSITE(0, -1, true),
	LEFT_TO_OPPOSITE(-1, -1, true),
	RIGHT_TO_OPPOSITE(1, -1, true);

	private final int rowOffset;
	private final int columnOffset;
	private final boolean flipRowOffsetIfDownFacing;

	Neighbor(int columnOffset, int rowOffset, boolean flipRowOffsetIfDownFacing) {
		this.rowOffset = rowOffset;
		this.columnOffset = columnOffset;
		this.flipRowOffsetIfDownFacing = flipRowOffsetIfDownFacing;
	}

	public Location relativeTo(Location location) {
		int actualRowOffset = flipRowOffsetIfDownFacing && !location.isFacingUp() ? -rowOffset : rowOffset;
		return Location.at(location.column() + columnOffset, location.row() + actualRowOffset);
	}
}

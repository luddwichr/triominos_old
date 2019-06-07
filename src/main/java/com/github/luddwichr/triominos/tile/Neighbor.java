package com.github.luddwichr.triominos.tile;

public enum Neighbor {

	LEFT(0, -1, false),
	RIGHT(0, 1, false),
	MIDDLE(-1, 0, true),
	FAR_LEFT(0, -2, false),
	FAR_RIGHT(0, 2, false),
	LEFT_TO_MIDDLE(-1, -1, true),
	FAR_LEFT_TO_MIDDLE(-1, -2, true),
	RIGHT_TO_MIDDLE(-1, 1, true),
	FAR_RIGHT_TO_MIDDLE(-1, 2, true),
	OPPOSITE(1, 0, true),
	LEFT_TO_OPPOSITE(1, -1, true),
	RIGHT_TO_OPPOSITE(1, 1, true);

	private final int rowOffset;
	private final int columnOffset;
	private final boolean flipRowOffsetIfDownFacing;

	Neighbor(int rowOffset, int columnOffset, boolean flipRowOffsetIfDownFacing) {
		this.rowOffset = rowOffset;
		this.columnOffset = columnOffset;
		this.flipRowOffsetIfDownFacing = flipRowOffsetIfDownFacing;
	}

	public Location relativeTo(Location location) {
		int actualRowOffset = flipRowOffsetIfDownFacing && !location.isFacingUp() ? -rowOffset : rowOffset;
		return Location.at(location.getRow() + actualRowOffset, location.getColumn() + columnOffset);
	}
}

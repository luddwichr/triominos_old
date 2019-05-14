package com.github.luddwichr.triominos.tile;

import java.util.Objects;

public class Location {
	private final int row;
	private final int column;

	public Location(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Location)) return false;
		Location location = (Location) o;
		return row == location.row && column == location.column;
	}

	@Override
	public final int hashCode() {
		return Objects.hash(row, column);
	}

	public boolean isFacingUp() {
		return (row + column) % 2 == 0;
	}

	public boolean isEdgeShared(Location otherLocation) {
		if (isLeftTo(otherLocation) || isRightTo(otherLocation)) {
			return true;
		}
		if (isFacingUp() && isAbove(otherLocation)) {
			return true;
		}

		return !isFacingUp() && isBelow(otherLocation);
	}

	private boolean isLeftTo(Location otherLocation) {
		return row == otherLocation.row && column + 1 == otherLocation.column;
	}
	private boolean isRightTo(Location otherLocation) {
		return row == otherLocation.row && column - 1 == otherLocation.column;
	}

	private boolean isAbove(Location otherLocation) {
		return column == otherLocation.column && row - 1 == otherLocation.row;
	}

	private boolean isBelow(Location otherLocation) {
		return column == otherLocation.column && row + 1 == otherLocation.row;
	}

}

package com.github.luddwichr.triominos.tile;

import java.util.Objects;

public class Location {
	private final int row;
	private final int column;

	public static Location at(int row, int column) {
		return new Location(row, column);
	}

	private Location(int row, int column) {
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

}

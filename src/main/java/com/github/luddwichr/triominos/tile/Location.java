package com.github.luddwichr.triominos.tile;

public record Location(int column, int row) {
	public static Location at(int column, int row) {
		return new Location(column, row);
	}

	public boolean isFacingUp() {
		return (row + column) % 2 == 0;
	}
}

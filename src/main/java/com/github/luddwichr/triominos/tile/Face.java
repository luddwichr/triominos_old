package com.github.luddwichr.triominos.tile;

import java.util.Objects;

public class Face {
	private final int left;
	private final int right;

	public Face(int left, int right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public final boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Face)) return false;
		Face face = (Face) o;
		return left == face.left && right == face.right;
	}

	@Override
	public final int hashCode() {
		return Objects.hash(left, right);
	}
}

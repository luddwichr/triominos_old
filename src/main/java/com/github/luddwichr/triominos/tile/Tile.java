package com.github.luddwichr.triominos.tile;

import java.util.EnumMap;

/*
 *      b
 *     / \
 *    /   \
 *   /     \
 *  a-------c
 *
 */
public class Tile {

	private final EnumMap<Corner, Integer> values = new EnumMap<>(Corner.class);

	public Tile(int a, int b, int c) {
		values.put(Corner.A, a);
		values.put(Corner.B, b);
		values.put(Corner.C, c);
	}

	public int getValue(Corner corner) {
		return values.get(corner);
	}

	public int points() {
		return values.get(Corner.A) + values.get(Corner.B) + values.get(Corner.C);
	}

}

package com.github.luddwichr.triominos.tile;

import java.util.EnumMap;

/*
 *        middle
 *         / \
 *        /   \
 *       /     \
 *  left ------- right
 *
 */
public class Tile {

	private final EnumMap<Corner, Integer> cornerNumbers = new EnumMap<>(Corner.class);

	public Tile(int leftCornerNumber, int middleCornerNumber, int rightCornerNumber) {
		cornerNumbers.put(Corner.LEFT, leftCornerNumber);
		cornerNumbers.put(Corner.MIDDLE, middleCornerNumber);
		cornerNumbers.put(Corner.RIGHT, rightCornerNumber);
	}

	public int getNumber(Corner corner) {
		return cornerNumbers.get(corner);
	}

	public int points() {
		return cornerNumbers.get(Corner.LEFT) + cornerNumbers.get(Corner.MIDDLE) + cornerNumbers.get(Corner.RIGHT);
	}

}

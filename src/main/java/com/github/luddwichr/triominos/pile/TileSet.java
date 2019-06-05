package com.github.luddwichr.triominos.pile;

import com.github.luddwichr.triominos.tile.Tile;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TileSet {

	private static final Set<Tile> CLASSIC = createClassicSet();

	public static Set<Tile> getClassicSet() {
		return CLASSIC;
	}

	private static Set<Tile> createClassicSet() {
		Set<Tile> set = new HashSet<>(56);
		for (int a = 0; a <= 5; a++) {
			for (int b = a; b <= 5; b++) {
				for (int c = b; c <= 5; c++) {
					set.add(new Tile(a, b, c));
				}
			}
		}
		return Collections.unmodifiableSet(set);
	}

	private TileSet() {
		// only static methods, hence no constructor should be exposed
	}
}

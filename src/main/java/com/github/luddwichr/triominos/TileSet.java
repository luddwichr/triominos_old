package com.github.luddwichr.triominos;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TileSet {

	public static final Set<Tile> CLASSIC = createClassicSet();

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
}

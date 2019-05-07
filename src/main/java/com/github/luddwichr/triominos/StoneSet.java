package com.github.luddwichr.triominos;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class StoneSet {

	public static final Set<Stone> CLASSIC = createClassicSet();

	private static Set<Stone> createClassicSet() {
		Set<Stone> set = new HashSet<>(56);
		for (int first = 0; first <= 5; first++) {
			for (int second = first; second <= 5; second++) {
				for (int third = second; third <= 5; third++) {
					set.add(new Stone(first, second, third));
				}
			}
		}
		return Collections.unmodifiableSet(set);
	}
}

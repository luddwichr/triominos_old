package com.github.luddwichr.triominos.tile;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class FaceTest {

	@Test
	void ensureEqualsHashCodeContract() {
		EqualsVerifier.forClass(Face.class).verify();
	}
}

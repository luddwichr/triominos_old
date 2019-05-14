package com.github.luddwichr.triominos.tile;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FaceTest {

	@Test
	void ensureEqualsHashCodeContract() {
		EqualsVerifier.forClass(Face.class).verify();
	}

	@Test
	void matches() {
		Face faceA = new Face(1, 2);
		Face faceB = new Face(2, 1);
		Face faceC = new Face(1, 3);
		Face faceD = new Face(3, 2);
		Face faceE = new Face(3, 3);

		assertThat(faceA.matches(faceB)).isTrue();
		assertThat(faceB.matches(faceA)).isTrue();
		assertThat(faceA.matches(faceC)).isFalse();
		assertThat(faceA.matches(faceD)).isFalse();
		assertThat(faceA.matches(faceE)).isFalse();
	}
}

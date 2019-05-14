package com.github.luddwichr.triominos.tile;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrientationTest {

	@Test
	void abc() {
		assertThat(Orientation.ABC.isFacingUp()).isTrue();
		assertThat(Orientation.ABC.getLeft()).isEqualTo(Corner.A);
		assertThat(Orientation.ABC.getMiddle()).isEqualTo(Corner.B);
		assertThat(Orientation.ABC.getRight()).isEqualTo(Corner.C);
	}

	@Test
	void acb() {
		assertThat(Orientation.ACB.isFacingUp()).isFalse();
		assertThat(Orientation.ACB.getLeft()).isEqualTo(Corner.A);
		assertThat(Orientation.ACB.getMiddle()).isEqualTo(Corner.C);
		assertThat(Orientation.ACB.getRight()).isEqualTo(Corner.B);
	}

	@Test
	void cab() {
		assertThat(Orientation.CAB.isFacingUp()).isTrue();
		assertThat(Orientation.CAB.getLeft()).isEqualTo(Corner.C);
		assertThat(Orientation.CAB.getMiddle()).isEqualTo(Corner.A);
		assertThat(Orientation.CAB.getRight()).isEqualTo(Corner.B);
	}

	@Test
	void cba() {
		assertThat(Orientation.CBA.isFacingUp()).isFalse();
		assertThat(Orientation.CBA.getLeft()).isEqualTo(Corner.C);
		assertThat(Orientation.CBA.getMiddle()).isEqualTo(Corner.B);
		assertThat(Orientation.CBA.getRight()).isEqualTo(Corner.A);
	}

	@Test
	void bca() {
		assertThat(Orientation.BCA.isFacingUp()).isTrue();
		assertThat(Orientation.BCA.getLeft()).isEqualTo(Corner.B);
		assertThat(Orientation.BCA.getMiddle()).isEqualTo(Corner.C);
		assertThat(Orientation.BCA.getRight()).isEqualTo(Corner.A);
	}

	@Test
	void bac() {
		assertThat(Orientation.BAC.isFacingUp()).isFalse();
		assertThat(Orientation.BAC.getLeft()).isEqualTo(Corner.B);
		assertThat(Orientation.BAC.getMiddle()).isEqualTo(Corner.A);
		assertThat(Orientation.BAC.getRight()).isEqualTo(Corner.C);
	}
}

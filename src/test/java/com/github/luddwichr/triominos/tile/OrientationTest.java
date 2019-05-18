package com.github.luddwichr.triominos.tile;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrientationTest {

	@Test
	void isFacingUp() {
		assertThat(Orientation.ABC.isFacingUp()).isTrue();
		assertThat(Orientation.ACB.isFacingUp()).isFalse();
		assertThat(Orientation.CAB.isFacingUp()).isTrue();
		assertThat(Orientation.CBA.isFacingUp()).isFalse();
		assertThat(Orientation.BCA.isFacingUp()).isTrue();
		assertThat(Orientation.BAC.isFacingUp()).isFalse();
	}

	@Test
	void getLeft() {
		assertThat(Orientation.ABC.getLeft()).isEqualTo(Corner.A);
		assertThat(Orientation.ACB.getLeft()).isEqualTo(Corner.A);
		assertThat(Orientation.CAB.getLeft()).isEqualTo(Corner.C);
		assertThat(Orientation.CBA.getLeft()).isEqualTo(Corner.C);
		assertThat(Orientation.BCA.getLeft()).isEqualTo(Corner.B);
		assertThat(Orientation.BAC.getLeft()).isEqualTo(Corner.B);
	}

	@Test
	void getRight() {
		assertThat(Orientation.ABC.getRight()).isEqualTo(Corner.C);
		assertThat(Orientation.ACB.getRight()).isEqualTo(Corner.B);
		assertThat(Orientation.CAB.getRight()).isEqualTo(Corner.B);
		assertThat(Orientation.CBA.getRight()).isEqualTo(Corner.A);
		assertThat(Orientation.BCA.getRight()).isEqualTo(Corner.A);
		assertThat(Orientation.BAC.getRight()).isEqualTo(Corner.C);
	}

	@Test
	void getMiddle() {
		assertThat(Orientation.ABC.getMiddle()).isEqualTo(Corner.B);
		assertThat(Orientation.ACB.getMiddle()).isEqualTo(Corner.C);
		assertThat(Orientation.CAB.getMiddle()).isEqualTo(Corner.A);
		assertThat(Orientation.CBA.getMiddle()).isEqualTo(Corner.B);
		assertThat(Orientation.BCA.getMiddle()).isEqualTo(Corner.C);
		assertThat(Orientation.BAC.getMiddle()).isEqualTo(Corner.A);
	}

}

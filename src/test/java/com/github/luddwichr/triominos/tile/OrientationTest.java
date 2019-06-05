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
		assertThat(Orientation.ABC.getRotatedCorner(RotatedCorner.LEFT)).isEqualTo(Corner.A);
		assertThat(Orientation.ACB.getRotatedCorner(RotatedCorner.LEFT)).isEqualTo(Corner.A);
		assertThat(Orientation.CAB.getRotatedCorner(RotatedCorner.LEFT)).isEqualTo(Corner.C);
		assertThat(Orientation.CBA.getRotatedCorner(RotatedCorner.LEFT)).isEqualTo(Corner.C);
		assertThat(Orientation.BCA.getRotatedCorner(RotatedCorner.LEFT)).isEqualTo(Corner.B);
		assertThat(Orientation.BAC.getRotatedCorner(RotatedCorner.LEFT)).isEqualTo(Corner.B);
	}

	@Test
	void getRight() {
		assertThat(Orientation.ABC.getRotatedCorner(RotatedCorner.RIGHT)).isEqualTo(Corner.C);
		assertThat(Orientation.ACB.getRotatedCorner(RotatedCorner.RIGHT)).isEqualTo(Corner.B);
		assertThat(Orientation.CAB.getRotatedCorner(RotatedCorner.RIGHT)).isEqualTo(Corner.B);
		assertThat(Orientation.CBA.getRotatedCorner(RotatedCorner.RIGHT)).isEqualTo(Corner.A);
		assertThat(Orientation.BCA.getRotatedCorner(RotatedCorner.RIGHT)).isEqualTo(Corner.A);
		assertThat(Orientation.BAC.getRotatedCorner(RotatedCorner.RIGHT)).isEqualTo(Corner.C);
	}

	@Test
	void getMiddle() {
		assertThat(Orientation.ABC.getRotatedCorner(RotatedCorner.MIDDLE)).isEqualTo(Corner.B);
		assertThat(Orientation.ACB.getRotatedCorner(RotatedCorner.MIDDLE)).isEqualTo(Corner.C);
		assertThat(Orientation.CAB.getRotatedCorner(RotatedCorner.MIDDLE)).isEqualTo(Corner.A);
		assertThat(Orientation.CBA.getRotatedCorner(RotatedCorner.MIDDLE)).isEqualTo(Corner.B);
		assertThat(Orientation.BCA.getRotatedCorner(RotatedCorner.MIDDLE)).isEqualTo(Corner.C);
		assertThat(Orientation.BAC.getRotatedCorner(RotatedCorner.MIDDLE)).isEqualTo(Corner.A);
	}

}

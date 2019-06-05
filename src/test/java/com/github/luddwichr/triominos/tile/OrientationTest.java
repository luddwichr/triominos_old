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
		assertThat(Orientation.ABC.getRotatedCorner(Corner.LEFT)).isEqualTo(Corner.LEFT);
		assertThat(Orientation.ACB.getRotatedCorner(Corner.LEFT)).isEqualTo(Corner.LEFT);
		assertThat(Orientation.CAB.getRotatedCorner(Corner.LEFT)).isEqualTo(Corner.RIGHT);
		assertThat(Orientation.CBA.getRotatedCorner(Corner.LEFT)).isEqualTo(Corner.RIGHT);
		assertThat(Orientation.BCA.getRotatedCorner(Corner.LEFT)).isEqualTo(Corner.MIDDLE);
		assertThat(Orientation.BAC.getRotatedCorner(Corner.LEFT)).isEqualTo(Corner.MIDDLE);
	}

	@Test
	void getRight() {
		assertThat(Orientation.ABC.getRotatedCorner(Corner.RIGHT)).isEqualTo(Corner.RIGHT);
		assertThat(Orientation.ACB.getRotatedCorner(Corner.RIGHT)).isEqualTo(Corner.MIDDLE);
		assertThat(Orientation.CAB.getRotatedCorner(Corner.RIGHT)).isEqualTo(Corner.MIDDLE);
		assertThat(Orientation.CBA.getRotatedCorner(Corner.RIGHT)).isEqualTo(Corner.LEFT);
		assertThat(Orientation.BCA.getRotatedCorner(Corner.RIGHT)).isEqualTo(Corner.LEFT);
		assertThat(Orientation.BAC.getRotatedCorner(Corner.RIGHT)).isEqualTo(Corner.RIGHT);
	}

	@Test
	void getMiddle() {
		assertThat(Orientation.ABC.getRotatedCorner(Corner.MIDDLE)).isEqualTo(Corner.MIDDLE);
		assertThat(Orientation.ACB.getRotatedCorner(Corner.MIDDLE)).isEqualTo(Corner.RIGHT);
		assertThat(Orientation.CAB.getRotatedCorner(Corner.MIDDLE)).isEqualTo(Corner.LEFT);
		assertThat(Orientation.CBA.getRotatedCorner(Corner.MIDDLE)).isEqualTo(Corner.MIDDLE);
		assertThat(Orientation.BCA.getRotatedCorner(Corner.MIDDLE)).isEqualTo(Corner.RIGHT);
		assertThat(Orientation.BAC.getRotatedCorner(Corner.MIDDLE)).isEqualTo(Corner.LEFT);
	}

}

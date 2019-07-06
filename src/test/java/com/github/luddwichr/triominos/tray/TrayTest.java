
package com.github.luddwichr.triominos.tray;

import com.github.luddwichr.triominos.tile.Tile;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class TrayTest {

	private final Tray tray = new Tray();

	@Test
	void getTilesShouldYieldUnmodifiableSet() {
		Tile someTile = mock(Tile.class);
		assertThatThrownBy(() -> tray.getTiles().add(someTile))
				.isInstanceOf(UnsupportedOperationException.class);
		assertThatThrownBy(() -> tray.getTiles().remove(someTile))
				.isInstanceOf(UnsupportedOperationException.class);
	}

	@Test
	void getTilesShouldInitiallyYieldEmptySet() {
		assertThat(tray.getTiles()).isEmpty();
	}

	@Test
	void addTile() {
		Tile someTile = mock(Tile.class);
		tray.addTile(someTile);
		assertThat(tray.getTiles()).containsExactly(someTile);
	}

	@Test
	void removeTile() {
		Tile someTile = mock(Tile.class);
		tray.addTile(someTile);

		tray.removeTile(someTile);

		assertThat(tray.getTiles()).isEmpty();
	}
}

package com.github.luddwichr.triominos.player;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerFactoryTest {

	private final PlayerFactory playerFactory = new PlayerFactory();

	@Test
	void simplePlayer() {
		assertThat(playerFactory.simplePlayer()).isInstanceOf(SimplePlayer.class);
	}

}

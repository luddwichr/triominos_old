package com.github.luddwichr.triominos.round;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class RoundRulesTest {

	private final RoundRules roundRules = new RoundRules();

	@CsvSource({"2,9", "3,7", "4,7"})
	@ParameterizedTest
	void getNumberOfTilesToDrawForInitialTray(int numberOfPlayers, int expectedTraySize) {
		assertThat(roundRules.getNumberOfTilesToDrawForInitialTray(numberOfPlayers)).isEqualTo(expectedTraySize);
	}
}

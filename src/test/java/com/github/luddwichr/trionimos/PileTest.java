package com.github.luddwichr.trionimos;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class PileTest {

	private final Pile pile = new Pile();

	@Test
	void shouldHaveAsManyRemainingStonesAsClassicStoneSetContains() {
		assertThat(pile.remainingStones()).isEqualTo(StoneSet.CLASSIC.size());
	}

	@Test
	void isEmptyIsFalseInitially() {
		assertThat(pile.isEmpty()).isFalse();
	}

	@Test
	void shouldBeEmptyAfterAllStonesHaveBeenDrawn() {
		while (pile.remainingStones() > 0) {
			pile.drawStone();
		}
		assertThat(pile.isEmpty()).isTrue();
	}

	@Test
	void drawStoneShouldEventuallyReturnAllStonesFromStoneSet() {
		Set<Stone> stonesFromPile = IntStream.range(0, StoneSet.CLASSIC.size())
				.mapToObj(i -> pile.drawStone()).collect(Collectors.toSet());
		assertThat(stonesFromPile).containsExactlyInAnyOrderElementsOf(StoneSet.CLASSIC);
	}
}

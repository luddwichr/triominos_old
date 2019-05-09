package com.github.luddwichr.triominos;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PileTest {

	private final Pile pile = new Pile();

	@Test
	void pileHasAsManyRemainingStonesAsClassicStoneSetContains() {
		assertThat(pile.remainingStones()).isEqualTo(StoneSet.CLASSIC.size());
	}

	@Test
	void isEmptyIsFalseForInitialPile() {
		assertThat(pile.isEmpty()).isFalse();
	}

	@Test
	void isEmptyAfterAllStonesHaveBeenDrawn() {
		while (pile.remainingStones() > 0) {
			pile.drawStone();
		}
		assertThat(pile.isEmpty()).isTrue();
	}

	@Test
	void drawStoneEventuallyReturnsAllStonesFromStoneSet() {
		Set<Stone> stonesFromPile = StoneSet.CLASSIC.stream().map(stone -> pile.drawStone()).collect(toSet());
		assertThat(stonesFromPile).containsExactlyInAnyOrderElementsOf(StoneSet.CLASSIC);
	}

	@Test
	void drawStoneFromEmptyPileThrows() {
		StoneSet.CLASSIC.forEach(stone -> pile.drawStone());
		assertThatThrownBy(pile::drawStone).isInstanceOf(Pile.EmptyPileException.class);
	}
}

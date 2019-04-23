package com.github.luddwichr.trionimos;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PileTest {

    private final Pile pile = new Pile();

    @Test
    void isEmptyShouldBeFalseAfterCreation() {
        assertThat(pile.isEmpty()).isFalse();
    }

    @Test
    void isEmptyShouldBeAfterAllStonesAreDrawn() {
        while (pile.remainingStones() > 0) {
            pile.drawStone();
        }
        assertThat(pile.isEmpty()).isTrue();
    }

    @Test
    void drawStoneShouldReturnRandomStone() {
        assertThat(pile.drawStone()).isNotNull();
    }

    @Test
    void drawStoneShouldEventuallyReturnAllStones() {

    }
}
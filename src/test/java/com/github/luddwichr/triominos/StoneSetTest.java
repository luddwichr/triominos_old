package com.github.luddwichr.triominos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class StoneSetTest {

    @ParameterizedTest
    @CsvSource({
            "0,0,0",
            "0,0,1",
            "0,0,2",
            "0,0,3",
            "0,0,4",
            "0,0,5",
            "0,1,1",
            "0,1,2",
            "0,1,3",
            "0,1,4",
            "0,1,5",
            "0,2,2",
            "0,2,3",
            "0,2,4",
            "0,2,5",
            "0,3,3",
            "0,3,4",
            "0,3,5",
            "0,4,4",
            "0,4,5",
            "0,5,5",
            "1,1,1",
            "1,1,2",
            "1,1,3",
            "1,1,4",
            "1,1,5",
            "1,2,2",
            "1,2,3",
            "1,2,4",
            "1,2,5",
            "1,3,3",
            "1,3,4",
            "1,3,5",
            "1,4,4",
            "1,4,5",
            "1,5,5",
            "2,2,2",
            "2,2,3",
            "2,2,4",
            "2,2,5",
            "2,3,3",
            "2,3,4",
            "2,3,5",
            "2,4,4",
            "2,4,5",
            "2,5,5",
            "3,3,3",
            "3,3,4",
            "3,3,5",
            "3,4,4",
            "3,4,5",
            "3,5,5",
            "4,4,4",
            "4,4,5",
            "4,5,5",
            "5,5,5"})
    void classicStoneSetContainsStonesAsDefinedByRules(int a, int b, int c) {
        assertThat(StoneSet.CLASSIC).usingFieldByFieldElementComparator().contains(new Stone(a, b, c));
    }

    @Test
    void classicStoneSetContains65Stones() {
        assertThat(StoneSet.CLASSIC).hasSize(56);
    }

}
package com.github.luddwichr.trionimos;

import java.util.*;

public class Pile {

    private final LinkedList<Stone> pile;

    public Pile() {
        pile = new LinkedList<>();
        for (int first = 0; first <= 5; first++) {
            for (int second = first; second <= 5; second++) {
                for (int third = second; third <= 5; third++) {
                    pile.add(new Stone(first, second, third));
                }
            }
        }
        Collections.shuffle(pile);
    }

    public Stone drawStone() {
        return pile.removeFirst();
    }
}

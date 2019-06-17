package com.github.luddwichr.triominos.pile;

public class PileFactory {

	public Pile classicGamePile() {
		return new Pile(TileSet.getClassicSet());
	}
	
}

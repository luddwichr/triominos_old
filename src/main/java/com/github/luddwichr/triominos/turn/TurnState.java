package com.github.luddwichr.triominos.turn;

public class TurnState {

	public static class TurnStateFactory {
		public TurnState createTurnState() {
			return new TurnState();
		}
	}

	private TurnState() {
		// private to hide from user, use factory instead
	}

	public boolean isAllPlayersBlocked() {
		return false;
	}

}

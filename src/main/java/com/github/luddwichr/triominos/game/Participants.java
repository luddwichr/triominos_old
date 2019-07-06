package com.github.luddwichr.triominos.game;

import com.github.luddwichr.triominos.player.Player;

import java.util.Collections;
import java.util.List;

public class Participants {

	private final List<Player> players;

	public static class ParticipantsFactory {

		private final GameRuleChecker gameRuleChecker;

		public ParticipantsFactory(GameRuleChecker gameRuleChecker) {
			this.gameRuleChecker = gameRuleChecker;
		}

		public Participants createParticipants(List<Player> players) {
			verifyNumberOfPlayersAllowed(players.size());
			return new Participants(players);
		}

		private void verifyNumberOfPlayersAllowed(int numberOfPlayers) {
			if (!gameRuleChecker.isNumberOfPlayersAllowed(numberOfPlayers)) {
				throw new IllegalArgumentException("Number of players not allowed");
			}
		}

	}

	private Participants(List<Player> players) {
		this.players = Collections.unmodifiableList(players);
	}

	public List<Player> getAllPlayers() {
		return players;
	}

}

package com.github.luddwichr.triominos.score;

import com.github.luddwichr.triominos.player.Player;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreCard {

	public static class ScoreCardFactory {
		public ScoreCard createScoreCard(List<Player> players) {
			return new ScoreCard(players);
		}
	}

	private final Map<Player, Integer> scores;

	private ScoreCard(List<Player> players) {
		scores = players.stream().collect(Collectors.toMap(player -> player, player -> 0));
	}

	public int getScore(Player player) {
		if (!scores.containsKey(player)) {
			throw new IllegalArgumentException("Unknown player!");
		}
		return scores.get(player);
	}

	public int addPoints(Player player, int points) {
		int newScore = getScore(player) + points;
		scores.put(player, newScore);
		return newScore;
	}

}

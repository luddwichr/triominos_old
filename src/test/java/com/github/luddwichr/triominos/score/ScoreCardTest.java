package com.github.luddwichr.triominos.score;

import com.github.luddwichr.triominos.player.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class ScoreCardTest {

	private final Player unknownPlayer = mock(Player.class);
	private final Player playerA = mock(Player.class);
	private final Player playerB = mock(Player.class);
	private final List<Player> players = List.of(playerA, playerB);
	private final ScoreCard scoreCard = new ScoreCard.ScoreCardFactory().createScoreCard(players);

	@Test
	void getScoreShouldBeInitiallyZeroForAllPlayers() {
		assertThat(scoreCard.getScore(playerA)).isZero();
		assertThat(scoreCard.getScore(playerB)).isZero();
	}

	@Test
	void getScoreForNonExistentPlayerShouldFail() {
		assertThatThrownBy(() -> scoreCard.getScore(unknownPlayer))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Unknown player!");
	}

	@Test
	void addPointsShouldUpdateScoreForPlayerOnly() {
		scoreCard.addPoints(playerA, 5);
		assertThat(scoreCard.getScore(playerA)).isEqualTo(5);
		assertThat(scoreCard.getScore(playerB)).isZero();
	}

	@Test
	void addPointsShouldReturnNewScoreOfPlayer() {
		assertThat(scoreCard.addPoints(playerA, 5)).isEqualTo(5);
		assertThat(scoreCard.addPoints(playerA, -10)).isEqualTo(-5);
	}

	@Test
	void addPointsForNonExistentPlayerShouldFail() {
		assertThatThrownBy(() -> scoreCard.addPoints(unknownPlayer, 5))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Unknown player!");
	}
}

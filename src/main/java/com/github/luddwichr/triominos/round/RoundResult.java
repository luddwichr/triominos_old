package com.github.luddwichr.triominos.round;

import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.score.ScoreCard;

public record RoundResult(Player roundWinner, ScoreCard scoreCard) {
}

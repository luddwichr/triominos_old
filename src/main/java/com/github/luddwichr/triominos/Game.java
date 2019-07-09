package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.game.GameRules;
import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.round.RoundProcessor;
import com.github.luddwichr.triominos.round.RoundResult;
import com.github.luddwichr.triominos.round.RoundState;
import com.github.luddwichr.triominos.round.RoundState.RoundStateFactory;
import com.github.luddwichr.triominos.score.ScoreCard;
import com.github.luddwichr.triominos.score.ScoreCard.ScoreCardFactory;

import java.util.List;

public class Game {

    private final GameRules gameRules;
    private final ScoreCardFactory scoreCardFactory;
    private final RoundStateFactory roundFactory;
    private final RoundProcessor roundProcessor;

    public Game(GameRules gameRules, ScoreCardFactory scoreCardFactory,
				RoundStateFactory roundStateFactory, RoundProcessor roundProcessor) {
        this.gameRules = gameRules;
        this.scoreCardFactory = scoreCardFactory;
        this.roundFactory = roundStateFactory;
        this.roundProcessor = roundProcessor;
    }

    public void play(List<Player> players) {
		verifyNumberOfPlayersAllowed(players.size());
        ScoreCard scoreCard = scoreCardFactory.createScoreCard(players);
        Player gameWinner;
        do {
			RoundResult roundResult = playRound(players, scoreCard);
			gameWinner = determineGameWinner(players, roundResult);
        } while (gameWinner == null);
    }

	private void verifyNumberOfPlayersAllowed(int numberOfPlayers) {
		if (!gameRules.isNumberOfPlayersAllowed(numberOfPlayers)) {
			throw new IllegalArgumentException("Number of players not allowed");
		}
	}

	private RoundResult playRound(List<Player> players, ScoreCard scoreCard) {
		RoundState roundState = roundFactory.createRoundState(players, scoreCard);
		return roundProcessor.playRound(roundState);
	}

	private Player determineGameWinner(List<Player> players, RoundResult roundResult) {
        return players.stream()
                .filter(player -> gameRules.winsGame(player, roundResult))
                .findFirst()
                .orElse(null);
    }



}

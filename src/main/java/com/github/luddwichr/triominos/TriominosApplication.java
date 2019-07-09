package com.github.luddwichr.triominos;

import com.github.luddwichr.triominos.board.Board;
import com.github.luddwichr.triominos.board.PlacementValidator;
import com.github.luddwichr.triominos.game.GameRules;
import com.github.luddwichr.triominos.move.MoveProcessor;
import com.github.luddwichr.triominos.pile.PileFactory;
import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.player.PlayerFactory;
import com.github.luddwichr.triominos.round.RoundProcessor;
import com.github.luddwichr.triominos.round.RoundState.RoundStateFactory;
import com.github.luddwichr.triominos.score.ScoreCalculator;
import com.github.luddwichr.triominos.score.ScoreCard.ScoreCardFactory;
import com.github.luddwichr.triominos.turn.TurnProcessor;
import com.github.luddwichr.triominos.turn.TurnState.TurnStateFactory;

import java.util.List;

public class TriominosApplication {

	public static void main(String[] args) {
		GameRules gameRules = new GameRules();
		ScoreCalculator scoreCalculator = new ScoreCalculator();
		MoveProcessor moveProcessor = new MoveProcessor(new PlacementValidator(), scoreCalculator);
		ScoreCardFactory scoreCardFactory = new ScoreCardFactory();
		TurnStateFactory turnStateFactory = new TurnStateFactory();
		TurnProcessor turnProcessor = new TurnProcessor(gameRules, moveProcessor, scoreCalculator);
		RoundStateFactory roundStateFactory = new RoundStateFactory(gameRules, new Board.BoardFactory(), new PileFactory());
		RoundProcessor roundProcessor = new RoundProcessor(turnStateFactory, turnProcessor);
		PlayerFactory playerFactory = new PlayerFactory();
		List<Player> players = List.of(playerFactory.simplePlayer(), playerFactory.simplePlayer());

		Game game = new Game(gameRules, scoreCardFactory, roundStateFactory, roundProcessor);
		game.play(players);
	}

}

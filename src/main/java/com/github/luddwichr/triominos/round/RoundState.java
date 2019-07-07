package com.github.luddwichr.triominos.round;

import com.github.luddwichr.triominos.board.Board;
import com.github.luddwichr.triominos.board.Board.BoardFactory;
import com.github.luddwichr.triominos.game.Participants;
import com.github.luddwichr.triominos.pile.Pile;
import com.github.luddwichr.triominos.pile.PileFactory;
import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.score.ScoreCard;
import com.github.luddwichr.triominos.tray.Tray;

import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toMap;

public class RoundState {

	public static class RoundStateFactory {

		private final BoardFactory boardFactory;
		private final PileFactory pileFactory;

		public RoundStateFactory(BoardFactory boardFactory, PileFactory pileFactory) {
			this.boardFactory = boardFactory;
			this.pileFactory = pileFactory;
		}

		public RoundState createRoundState(Participants participants, ScoreCard scoreCard) {
			return new RoundState(boardFactory.emptyBoard(), pileFactory.classicGamePile(), participants, scoreCard);
		}

	}

	private final Board board;
	private final Pile pile;
	private final ScoreCard scoreCard;
	private final Participants participants;
	private final Map<Player, Tray> trays;

	private RoundState(Board board, Pile pile, Participants participants, ScoreCard scoreCard) {
		this.board = board;
		this.pile = pile;
		this.scoreCard = scoreCard;
		this.participants = participants;
		this.trays = unmodifiableMap(participants.getAllPlayers().stream().collect(toMap(player -> player, player -> new Tray())));
	}

	public Board getBoard() {
		return board;
	}

	public Pile getPile() {
		return pile;
	}

	public ScoreCard getScoreCard() {
		return scoreCard;
	}

	public Participants getParticipants() {
		return participants;
	}

	public Map<Player, Tray> getTrays() {
		return trays;
	}

}

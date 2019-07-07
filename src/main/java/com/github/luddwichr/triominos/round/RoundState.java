package com.github.luddwichr.triominos.round;

import com.github.luddwichr.triominos.board.Board;
import com.github.luddwichr.triominos.board.Board.BoardFactory;
import com.github.luddwichr.triominos.game.Participants;
import com.github.luddwichr.triominos.pile.Pile;
import com.github.luddwichr.triominos.pile.PileFactory;
import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.score.ScoreCard;
import com.github.luddwichr.triominos.tray.Tray;

import java.util.*;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toMap;

public class RoundState {

	public static class RoundStateFactory {

		private final RoundRules roundRules;
		private final BoardFactory boardFactory;
		private final PileFactory pileFactory;

		public RoundStateFactory(RoundRules roundRules, BoardFactory boardFactory, PileFactory pileFactory) {
			this.roundRules = roundRules;
			this.boardFactory = boardFactory;
			this.pileFactory = pileFactory;
		}

		public RoundState createRoundState(Participants participants, ScoreCard scoreCard) {
			Board board = boardFactory.emptyBoard();
			Pile pile = pileFactory.classicGamePile();
			Map<Player, Tray> trays = unmodifiableMap(initializeTrays(participants, pile));
			List<Player> moveOrder = unmodifiableList(determineMoveOrder(participants, trays));
			return new RoundState(board, pile, moveOrder, scoreCard, trays);
		}

		private Map<Player, Tray> initializeTrays(Participants participants, Pile pile) {
			int numberOfTilesToDraw = roundRules.getNumberOfTilesToDrawForInitialTray(participants.getAllPlayers().size()) ;
			return participants.getAllPlayers().stream().collect(toMap(player -> player, player -> createTray(pile, numberOfTilesToDraw)));
		}

		private Tray createTray(Pile pile, int numberOfTilesToDraw) {
			Tray tray = new Tray();
			for (int i = 0; i < numberOfTilesToDraw; i++) {
				tray.addTile(pile.drawRandomTile());
			}
			return tray;
		}

		private List<Player> determineMoveOrder(Participants participants, Map<Player, Tray> trays) {
			Player firstPlayer = roundRules.determineFirstPlayer(trays);
			List<Player> playersInMoveOrder = new ArrayList<>(participants.getAllPlayers());
			Collections.rotate(playersInMoveOrder, -playersInMoveOrder.indexOf(firstPlayer));
			return playersInMoveOrder;
		}

	}

	private final Board board;
	private final Pile pile;
	private final ScoreCard scoreCard;
	private final List<Player> playersInMoveOrder;
	private final Map<Player, Tray> trays;

	private RoundState(Board board, Pile pile, List<Player> playersInMoveOrder, ScoreCard scoreCard, Map<Player, Tray> trays) {
		this.board = board;
		this.pile = pile;
		this.scoreCard = scoreCard;
		this.playersInMoveOrder = playersInMoveOrder;
		this.trays = trays;
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

	public List<Player> getPlayersInMoveOrder() {
		return playersInMoveOrder;
	}

	public Map<Player, Tray> getTrays() {
		return trays;
	}

}

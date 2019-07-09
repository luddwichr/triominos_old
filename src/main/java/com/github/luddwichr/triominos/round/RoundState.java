package com.github.luddwichr.triominos.round;

import com.github.luddwichr.triominos.board.Board;
import com.github.luddwichr.triominos.board.Board.BoardFactory;
import com.github.luddwichr.triominos.game.GameRules;
import com.github.luddwichr.triominos.pile.Pile;
import com.github.luddwichr.triominos.pile.PileFactory;
import com.github.luddwichr.triominos.player.Player;
import com.github.luddwichr.triominos.score.ScoreCard;
import com.github.luddwichr.triominos.tray.Tray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toMap;

public class RoundState {

	public static class RoundStateFactory {

		private final GameRules gameRules;
		private final BoardFactory boardFactory;
		private final PileFactory pileFactory;

		public RoundStateFactory(GameRules gameRules, BoardFactory boardFactory, PileFactory pileFactory) {
			this.gameRules = gameRules;
			this.boardFactory = boardFactory;
			this.pileFactory = pileFactory;
		}

		public RoundState createRoundState(List<Player> players, ScoreCard scoreCard) {
			Board board = boardFactory.emptyBoard();
			Pile pile = pileFactory.classicGamePile();
			Map<Player, Tray> trays = unmodifiableMap(initializeTrays(players, pile));
			List<Player> moveOrder = unmodifiableList(determineMoveOrder(players, trays));
			return new RoundState(board, pile, moveOrder, scoreCard, trays);
		}

		private Map<Player, Tray> initializeTrays(List<Player> players, Pile pile) {
			int numberOfTilesToDraw = gameRules.getNumberOfTilesToDrawForInitialTray(players.size()) ;
			return players.stream().collect(toMap(player -> player, player -> createTray(pile, numberOfTilesToDraw)));
		}

		private Tray createTray(Pile pile, int numberOfTilesToDraw) {
			Tray tray = new Tray();
			for (int i = 0; i < numberOfTilesToDraw; i++) {
				tray.addTile(pile.drawRandomTile());
			}
			return tray;
		}

		private List<Player> determineMoveOrder(List<Player> players, Map<Player, Tray> trays) {
			Player firstPlayer = gameRules.determineFirstPlayer(trays);
			List<Player> playersInMoveOrder = new ArrayList<>(players);
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

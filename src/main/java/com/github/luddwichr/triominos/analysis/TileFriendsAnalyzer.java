package com.github.luddwichr.triominos.analysis;

import com.github.luddwichr.triominos.tile.Corner;
import com.github.luddwichr.triominos.tile.Tile;

import java.util.Map;
import java.util.Set;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class TileFriendsAnalyzer {

	private static class Edge {
		final Corner firstCorner;
		final Corner secondCorner;

		private Edge(Corner firstCorner, Corner secondCorner) {
			this.firstCorner = firstCorner;
			this.secondCorner = secondCorner;
		}
	}

	private static class EdgePair {
		private final Edge edgeA;
		private final Edge edgeB;

		EdgePair(Edge edgeA, Edge edgeB) {
			this.edgeA = edgeA;
			this.edgeB = edgeB;
		}

		boolean isMatching(Tile tileA, Tile tileB) {
			return tileA.getNumber(edgeA.firstCorner) == tileB.getNumber(edgeB.firstCorner) &&
					tileA.getNumber(edgeA.secondCorner) == tileB.getNumber(edgeB.secondCorner);
		}
	}

	private static final Set<EdgePair> EDGE_PAIRS = Set.of(
			new EdgePair(new Edge(Corner.LEFT, Corner.MIDDLE), new Edge(Corner.MIDDLE, Corner.LEFT)), // a -> a
			new EdgePair(new Edge(Corner.LEFT, Corner.MIDDLE), new Edge(Corner.RIGHT, Corner.MIDDLE)), // a -> b
			new EdgePair(new Edge(Corner.LEFT, Corner.MIDDLE), new Edge(Corner.LEFT, Corner.RIGHT)), // a -> c
			new EdgePair(new Edge(Corner.MIDDLE, Corner.RIGHT), new Edge(Corner.MIDDLE, Corner.LEFT)), // b -> a
			new EdgePair(new Edge(Corner.MIDDLE, Corner.RIGHT), new Edge(Corner.RIGHT, Corner.MIDDLE)), // b -> b
			new EdgePair(new Edge(Corner.MIDDLE, Corner.RIGHT), new Edge(Corner.LEFT, Corner.RIGHT)), // b -> c
			new EdgePair(new Edge(Corner.LEFT, Corner.RIGHT), new Edge(Corner.LEFT, Corner.MIDDLE)), // c -> a
			new EdgePair(new Edge(Corner.LEFT, Corner.RIGHT), new Edge(Corner.MIDDLE, Corner.RIGHT)), // c -> b
			new EdgePair(new Edge(Corner.LEFT, Corner.RIGHT), new Edge(Corner.LEFT, Corner.RIGHT)) // c -> c
	);

	public Map<Tile, Set<Tile>> findMatchingTiles(Set<Tile> tileSet) {
		return tileSet.stream().collect(toMap(identity(), tile -> findMatchingTiles(tileSet, tile)));
	}

	private Set<Tile> findMatchingTiles(Set<Tile> tileSet, Tile tile) {
		return tileSet.stream()
				.filter(otherTile -> otherTile != tile)
				.filter(otherTile -> isAttachable(tile, otherTile))
				.collect(toSet());
	}

	private boolean isAttachable(Tile tile, Tile otherTile) {
		return EDGE_PAIRS.stream().anyMatch(edgePair -> edgePair.isMatching(tile, otherTile));
	}

	}

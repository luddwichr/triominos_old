package com.github.luddwichr.triominos.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardFactoryTest {

	@Test
	void emptyBoard() {
		BoardFactory boardFactory = new BoardFactory();
		Board board = boardFactory.emptyBoard();
		assertThat(board.isEmpty()).isTrue();
	}

}

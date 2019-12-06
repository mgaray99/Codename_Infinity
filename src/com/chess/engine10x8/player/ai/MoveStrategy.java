package com.chess.engine10x8.player.ai;

import com.chess.engine10x8.board.Board;
import com.chess.engine10x8.board.Move;

public interface MoveStrategy {
    Move execute(Board board);
}

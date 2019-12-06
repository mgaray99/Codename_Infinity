package com.chess.engine9x9.player.ai;

import com.chess.engine9x9.board.Board;
import com.chess.engine9x9.board.Move;

public interface MoveStrategy {
    Move execute(Board board);
}

package com.chess.engine8x8.player.ai;

import com.chess.engine8x8.board.Board;
import com.chess.engine8x8.board.Move;

public interface MoveStrategy {

    long getNumBoardsEvaluated();

    Move execute(Board board);
}

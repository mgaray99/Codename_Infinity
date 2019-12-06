package com.chess.engine9x9.player.ai;

import com.chess.engine9x9.board.Board;

public interface BoardEvaluator {

    int evaluate(Board board, int depth);
}

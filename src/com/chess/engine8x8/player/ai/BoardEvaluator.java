package com.chess.engine8x8.player.ai;

import com.chess.engine8x8.board.Board;

public interface BoardEvaluator {

    int evaluate(Board board, int depth);
}

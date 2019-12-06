package com.chess.engine8x8.board;

import com.chess.engine8x8.board.Move;

public enum MoveUtilities {

    INSTANCE;

    public static int exchangeScore(final Move move) {
        if(move == Move.MoveFactory.getNullMove()) {
            return 1;
        }
        return move.isAttack() ?
                5 * exchangeScore(move.getBoard().getTransitionMove()) :
                exchangeScore(move.getBoard().getTransitionMove());

    }

}
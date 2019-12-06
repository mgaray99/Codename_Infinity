package com.chess.engine10x8.player.ai;

import com.chess.engine10x8.board.Board;
import com.chess.engine10x8.pieces.Piece;
import com.chess.engine10x8.player.Player;

public final class StandardBoardEvaluator implements BoardEvaluator {

    private static final int CHECK_BONUS = 50; // tune this, currently 0.5*Pawn
    private static final int CHECKMATE_BONUS =  10000;
    private static final int DEPTH_BONUS = 100;
    private static final int CASTLE_BONUS = 60;


    @Override
    public int evaluate(final Board board, final int depth) {
        return scorePlayer(board, board.whitePlayer(), depth) -
                scorePlayer(board, board.blackPlayer(), depth);
    }

    private int scorePlayer(final Board board,
                            final Player player,
                            final int depth) {
        return pieceValue(player) +
                mobility(player) +
                check(player) +
                checkMate(player, depth) +
                castled(player);
        // + checkMate, check, castled, mobility...
    }

    private static int castled(Player player){
        return player.isCastled() ? CASTLE_BONUS : 0;
    }

    private static int checkMate(final Player player, final int depth){
        return player.getOpponent().isInCheckMate() ?
                CHECKMATE_BONUS * depthBonus(depth): 0;
    }

    private static int depthBonus(int depth){
        return depth == 0 ? 1 : DEPTH_BONUS * depth;
    }

    private static int check(final Player player){
        return player.getOpponent().isInCheck() ?
                CHECK_BONUS : 0;
    }

    private static int mobility(final Player player){
        return player.getLegalMoves().size();
    }

    private static int pieceValue(final Player player){
        int pieceValueScore = 0;
        for(final Piece piece : player.getActivePieces()){
            pieceValueScore += piece.getPieceValue();
        }
        return pieceValueScore;
    }
}

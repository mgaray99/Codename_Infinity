package com.chess.engine8x8;

import com.chess.engine8x8.board.BoardUtilities;
import com.chess.engine8x8.player.BlackPlayer;
import com.chess.engine8x8.player.Player;
import com.chess.engine8x8.player.WhitePlayer;

public enum Alliance {
    WHITE {
        @Override
        public int getDirection(){
            return -1;
        }

        @Override
        public int getOppositeDirection(){
            return 1;
        }

        @Override
        public boolean isWhite(){
            return true;
        }

        @Override
        public boolean isBlack(){
            return false;
        }

        @Override
        public boolean isPawnPromotionSquare(int position) {
            return BoardUtilities.INSTANCE.EIGHTH_RANK[position];
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer,
                                   final BlackPlayer blackPlayer){
            return whitePlayer;
        }

        @Override
        public int pawnBonus(final int position) {
            return WHITE_PAWN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int knightBonus(final int position) {
            return WHITE_KNIGHT_PREFERRED_COORDINATES[position];
        }

        @Override
        public int bishopBonus(final int position) {
            return WHITE_BISHOP_PREFERRED_COORDINATES[position];
        }

        @Override
        public int rookBonus(final int position) {
            return WHITE_ROOK_PREFERRED_COORDINATES[position];
        }

        @Override
        public int queenBonus(final int position) {
            return WHITE_QUEEN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int kingBonus(final int position) {
            return WHITE_KING_PREFERRED_COORDINATES[position];
        }
    },
    BLACK {
        @Override
        public int getDirection(){
            return 1;
        }

        @Override
        public int getOppositeDirection(){
            return -1;
        }

        @Override
        public boolean isWhite(){
            return false;
        }

        @Override
        public boolean isBlack(){
            return true;
        }

        @Override
        public boolean isPawnPromotionSquare(int position) {
            return BoardUtilities.INSTANCE.FIRST_RANK[position];
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer,
                                   final BlackPlayer blackPlayer){
            return blackPlayer;
        }

        @Override
        public int pawnBonus(final int position) {
            return BLACK_PAWN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int knightBonus(final int position) {
            return BLACK_KNIGHT_PREFERRED_COORDINATES[position];
        }

        @Override
        public int bishopBonus(final int position) {
            return BLACK_BISHOP_PREFERRED_COORDINATES[position];
        }

        @Override
        public int rookBonus(final int position) {
            return BLACK_ROOK_PREFERRED_COORDINATES[position];
        }

        @Override
        public int queenBonus(final int position) {
            return BLACK_QUEEN_PREFERRED_COORDINATES[position];
        }

        @Override
        public int kingBonus(final int position) {
            return BLACK_KING_PREFERRED_COORDINATES[position];
        }
    };

    public abstract int getDirection();
    public abstract int getOppositeDirection();

    public abstract int pawnBonus(int position);

    public abstract int knightBonus(int position);

    public abstract int bishopBonus(int position);

    public abstract int rookBonus(int position);

    public abstract int queenBonus(int position);

    public abstract int kingBonus(int position);

    public abstract boolean isWhite();
    public abstract boolean isBlack();

    public abstract boolean isPawnPromotionSquare(int position);

    private final static int[] WHITE_PAWN_PREFERRED_COORDINATES = {
            0,  0,  0,  0,  0,  0,  0,  0,
            75, 75, 75, 75, 75, 75, 75, 75,
            25, 25, 29, 29, 29, 29, 25, 25,
            5,  5, 10, 25, 25, 10,  5,  5,
            0,  0,  0, 20, 20,  0,  0,  0,
            5, -5,-10,  0,  0,-10, -5,  5,
            5, 10, 10,-20,-20, 10, 10,  5,
            0,  0,  0,  0,  0,  0,  0,  0
    };

    private final static int[] BLACK_PAWN_PREFERRED_COORDINATES = {
            0,  0,  0,  0,  0,  0,  0,  0,
            5, 10, 10,-20,-20, 10, 10,  5,
            5, -5,-10,  0,  0,-10, -5,  5,
            0,  0,  0, 20, 20,  0,  0,  0,
            5,  5, 10, 25, 25, 10,  5,  5,
            25, 25, 29, 29, 29, 29, 25, 25,
            75, 75, 75, 75, 75, 75, 75, 75,
            0,  0,  0,  0,  0,  0,  0,  0
    };

    private final static int[] WHITE_KNIGHT_PREFERRED_COORDINATES = {
            -50,-40,-30,-30,-30,-30,-40,-50,
            -40,-20,  0,  0,  0,  0,-20,-40,
            -30,  0, 10, 15, 15, 10,  0,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  0, 15, 20, 20, 15,  0,-30,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -40,-20,  0,  5,  5,  0,-20,-40,
            -50,-40,-30,-30,-30,-30,-40,-50
    };

    private final static int[] BLACK_KNIGHT_PREFERRED_COORDINATES = {
            -50,-40,-30,-30,-30,-30,-40,-50,
            -40,-20,  0,  5,  5,  0,-20,-40,
            -30,  5, 10, 15, 15, 10,  5,-30,
            -30,  0, 15, 20, 20, 15,  0,-30,
            -30,  5, 15, 20, 20, 15,  5,-30,
            -30,  0, 10, 15, 15, 10,  0,-30,
            -40,-20,  0,  0,  0,  0,-20,-40,
            -50,-40,-30,-30,-30,-30,-40,-50,
    };

    private final static int[] WHITE_BISHOP_PREFERRED_COORDINATES = {
            -20,-10,-10,-10,-10,-10,-10,-20,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -10,  0,  5, 10, 10,  5,  0,-10,
            -10,  5,  5, 10, 10,  5,  5,-10,
            -10,  0, 10, 10, 10, 10,  0,-10,
            -10, 10, 10, 10, 10, 10, 10,-10,
            -10,  5,  0,  0,  0,  0,  5,-10,
            -20,-10,-10,-10,-10,-10,-10,-20
    };

    private final static int[] BLACK_BISHOP_PREFERRED_COORDINATES = {
            -20,-10,-10,-10,-10,-10,-10,-20,
            -10,  5,  0,  0,  0,  0,  5,-10,
            -10, 10, 10, 10, 10, 10, 10,-10,
            -10,  0, 10, 10, 10, 10,  0,-10,
            -10,  5,  5, 10, 10,  5,  5,-10,
            -10,  0,  5, 10, 10,  5,  0,-10,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -20,-10,-10,-10,-10,-10,-10,-20,
    };

    private final static int[] WHITE_ROOK_PREFERRED_COORDINATES = {
            0,  0,  0,  0,  0,  0,  0,  0,
            5, 20, 20, 20, 20, 20, 20,  5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            0,  0,  0,  5,  5,  0,  0,  0
    };

    private final static int[] BLACK_ROOK_PREFERRED_COORDINATES = {
            0,  0,  0,  5,  5,  0,  0,  0,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            -5,  0,  0,  0,  0,  0,  0, -5,
            5, 20, 20, 20, 20, 20, 20,  5,
            0,  0,  0,  0,  0,  0,  0,  0,
    };

    private final static int[] WHITE_QUEEN_PREFERRED_COORDINATES = {
            -20,-10,-10, -5, -5,-10,-10,-20,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -10,  0,  5,  5,  5,  5,  0,-10,
            -5,  0,  5,  5,  5,  5,  0, -5,
            0,  0,  5,  5,  5,  5,  0, -5,
            -10,  5,  5,  5,  5,  5,  0,-10,
            -10,  0,  5,  0,  0,  0,  0,-10,
            -20,-10,-10, -5, -5,-10,-10,-20
    };

    private final static int[] BLACK_QUEEN_PREFERRED_COORDINATES = {
            -20,-10,-10, -5, -5,-10,-10,-20,
            -10,  0,  5,  0,  0,  0,  0,-10,
            -10,  5,  5,  5,  5,  5,  0,-10,
            0,  0,  5,  5,  5,  5,  0, -5,
            0,  0,  5,  5,  5,  5,  0, -5,
            -10,  0,  5,  5,  5,  5,  0,-10,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -20,-10,-10, -5, -5,-10,-10,-20
    };

    private final static int[] WHITE_KING_PREFERRED_COORDINATES = {
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -20,-30,-30,-40,-40,-30,-30,-20,
            -10,-20,-20,-20,-20,-20,-20,-10,
            20, 20,  0,  0,  0,  0, 20, 20,
            20, 30, 10,  0,  0, 10, 30, 20
    };

    private final static int[] BLACK_KING_PREFERRED_COORDINATES = {
            20, 30, 10,  0,  0, 10, 30, 20,
            20, 20,  0,  0,  0,  0, 20, 20,
            -10,-20,-20,-20,-20,-20,-20,-10,
            -20,-30,-30,-40,-40,-30,-30,-20,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30,
            -30,-40,-40,-50,-50,-40,-40,-30
    };

    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}

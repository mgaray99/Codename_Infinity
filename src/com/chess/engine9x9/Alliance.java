package com.chess.engine9x9;

import com.chess.engine9x9.board.BoardUtilities;
import com.chess.engine9x9.player.BlackPlayer;
import com.chess.engine9x9.player.Player;
import com.chess.engine9x9.player.WhitePlayer;

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
            return BoardUtilities.INSTANCE.NINTH_RANK[position];
        }

        @Override
        public Player choosePlayer(final WhitePlayer whitePlayer,
                                   final BlackPlayer blackPlayer){
            return whitePlayer;
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
    };

    public abstract int getDirection();
    public abstract int getOppositeDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();

    public abstract boolean isPawnPromotionSquare(int position);

    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}

package com.chess.pgn;

import com.chess.engine8x8.board.Board;
import com.chess.engine8x8.board.Move;
import com.chess.engine8x8.player.Player;

public interface PGNPersistence {

    void persistGame(Game game);

    Move getNextBestMove(Board board, Player player, String gameText);

}
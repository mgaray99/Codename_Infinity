package com.chess.engine9x9.pieces;

import com.chess.engine9x9.Alliance;
import com.chess.engine9x9.board.Board;
import com.chess.engine9x9.board.BoardUtilities;
import com.chess.engine9x9.board.Move;
import com.chess.engine9x9.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine9x9.board.Move.*;

/**
 * Knight explanation by /u/soaringturkeys:
 * Knights are indefensible pieces that can swamp and defeat
 * the queen if the situation presents itself. Every single chess
 * piece bar another knight cannot defend itself from a knight's
 * attack. A knight serves no real defensive capabilities. Their
 * role in chess is to seek and destroy other powerful pieces.
 *
 * White Knight Options:
 *      Hulk
 *      Thor
 *
 * Black Knight Options:
 *      Cull Obsidian
 *      Ronan the Accuser
 */

public class Knight extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES =
            {-19, -17, -11, -7, 7, 11, 17, 19};

    public Knight(final Alliance pieceAlliance, final int piecePosition){
        super(PieceType.KNIGHT, piecePosition, pieceAlliance, true);
    }

    public Knight(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove){
        super(PieceType.KNIGHT, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board){

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES){
            int candidateDestinationCoordinate;
            candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            if(BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)){

                if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                    isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                    isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                    isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)){
                    continue;
                }

                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

                if(!candidateDestinationTile.isTileOccupied()){
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate)); //non-attacking
                } else{

                    final Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                    if(this.pieceAlliance != pieceAlliance){
                        legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination)); //attacking
                    }
                }
            }
        }

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Knight movePiece(final Move move) {
        return new Knight(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return Piece.PieceType.KNIGHT.toString();
    }

    //Edge case methods
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){

        return BoardUtilities.INSTANCE.FIRST_COLUMN[currentPosition] &&
                ((candidateOffset == -19) ||
                (candidateOffset == -11) ||
                        (candidateOffset == 7) ||
                        (candidateOffset == 17));

    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.SECOND_COLUMN[currentPosition] &&
                ((candidateOffset == -11) ||
                (candidateOffset == 7));
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.EIGHTH_COLUMN[currentPosition] &&
                ((candidateOffset == -7) ||
                (candidateOffset == 11));
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final  int candidateOffset){
        return BoardUtilities.INSTANCE.NINTH_COLUMN[currentPosition] &&
                ((candidateOffset == -17) ||
                (candidateOffset == -7) ||
                        (candidateOffset == 11) ||
                        (candidateOffset == 19));
    }

}

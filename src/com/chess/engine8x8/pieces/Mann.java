package com.chess.engine8x8.pieces;

import com.chess.engine8x8.Alliance;
import com.chess.engine8x8.board.Board;
import com.chess.engine8x8.board.BoardUtilities;
import com.chess.engine8x8.board.Move;
import com.chess.engine8x8.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine8x8.board.Move.*;

/**
 * This piece is a version of the more commonly known "Mann"
 * A piece that moves like a king but is not royal (value = 300)
 *
 */

public class Mann extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};


    public Mann(final Alliance pieceAlliance, final int piecePosition){
        super(PieceType.MANN, piecePosition, pieceAlliance, true);
    }

    public Mann(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove){
        super(PieceType.MANN, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board){

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE){
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
                    isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)){
                continue;
            }

            if(BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)){
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
    public Mann movePiece(final Move move) {
        return new Mann(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }


    @Override
    public int locationBonus() {
        return this.pieceAlliance.kingBonus(this.piecePosition);
    }

    //TODO: create mannBonus and replace kingBonus with it

    @Override
    public String toString(){
        return Piece.PieceType.MANN.toString();
    }
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){

        return BoardUtilities.INSTANCE.FIRST_COLUMN[currentPosition] &&
                ((candidateOffset == -9) ||
                        (candidateOffset == -1) ||
                        (candidateOffset == 7));

    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.EIGHTH_COLUMN[currentPosition] &&
                ((candidateOffset == -7) ||
                        (candidateOffset == 1) ||
                        (candidateOffset == 9));
    }
}

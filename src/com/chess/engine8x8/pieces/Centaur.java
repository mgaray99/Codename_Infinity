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
 * This piece is a version of the more commonly known "Centaur"
 * A piece combining the moves of the knight and the king (value = 600)
 */

public class Centaur extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};

    private final static int[] CANDIDATE_MOVE_COORDINATES =
            {-17, -15, -10, -6, 6, 10, 15, 17};


    public Centaur(final Alliance pieceAlliance, final int piecePosition){
        super(PieceType.CENTAUR, piecePosition, pieceAlliance, true);
    }

    public Centaur(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove){
        super(PieceType.CENTAUR, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board){

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE){
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            if(isFirstColumnExclusionKing(this.piecePosition, currentCandidateOffset) ||
                    isEighthColumnExclusionKing(this.piecePosition, currentCandidateOffset)){
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
        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES){
            int candidateDestinationCoordinate;
            candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;

            if(BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)){

                if(isFirstColumnExclusionKnight(this.piecePosition, currentCandidateOffset) ||
                        isSecondColumnExclusionKnight(this.piecePosition, currentCandidateOffset) ||
                        isSeventhColumnExclusionKnight(this.piecePosition, currentCandidateOffset) ||
                        isEighthColumnExclusionKnight(this.piecePosition, currentCandidateOffset)){
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
    public Centaur movePiece(final Move move) {
        return new Centaur(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.knightBonus(this.piecePosition) +
                this.pieceAlliance.kingBonus(this.piecePosition);
    }

    //TODO: create mannBonus and replace the kingBonus with it

    @Override
    public String toString(){
        return Piece.PieceType.CENTAUR.toString();
    }

    private static boolean isFirstColumnExclusionKing(final int currentPosition, final int candidateOffset){

        return BoardUtilities.INSTANCE.FIRST_COLUMN[currentPosition] &&
                ((candidateOffset == -9) ||
                        (candidateOffset == -1) ||
                        (candidateOffset == 7));

    }

    private static boolean isEighthColumnExclusionKing(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.EIGHTH_COLUMN[currentPosition] &&
                ((candidateOffset == -7) ||
                        (candidateOffset == 1) ||
                        (candidateOffset == 9));
    }

    private static boolean isFirstColumnExclusionKnight(final int currentPosition, final int candidateOffset){

        return BoardUtilities.INSTANCE.FIRST_COLUMN[currentPosition] &&
                ((candidateOffset == -17) ||
                        (candidateOffset == -10) ||
                        (candidateOffset == 6) ||
                        (candidateOffset == 15));

    }

    private static boolean isSecondColumnExclusionKnight(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.SECOND_COLUMN[currentPosition] &&
                ((candidateOffset == -10) ||
                        (candidateOffset == 6));
    }

    private static boolean isSeventhColumnExclusionKnight(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.SEVENTH_COLUMN[currentPosition] &&
                ((candidateOffset == -6) ||
                        (candidateOffset == 10));
    }

    private static boolean isEighthColumnExclusionKnight(final int currentPosition, final  int candidateOffset){
        return BoardUtilities.INSTANCE.EIGHTH_COLUMN[currentPosition] &&
                ((candidateOffset == -15) ||
                        (candidateOffset == -6) ||
                        (candidateOffset == 10) ||
                        (candidateOffset == 17));
    }
}

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
 * This piece is a version of the more commonly known "Empress", "Chancellor" or "Marshal"
 * A piece combining the moves of the rook and the knight (value = 800)
 */

public class Chancellor extends Piece{

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES =
            {-8, -1, 1, 8};

    private final static int[] CANDIDATE_MOVE_COORDINATES =
            {-17, -15, -10, -6, 6, 10, 15, 17};

    public Chancellor(final Alliance pieceAlliance,
                      final int piecePosition){
        super(PieceType.CHANCELLOR, piecePosition, pieceAlliance, true);
    }

    public Chancellor(final Alliance pieceAlliance,
                      final int piecePosition,
                      final boolean isFirstMove){
        super(PieceType.CHANCELLOR, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board){

        final List<Move> legalMoves = new ArrayList<>();

        for(final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES){
            int candidateDestinationCoordinate = this.piecePosition;
            while(BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)){

                if((isFirstColumnExclusionRook(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isEighthColumnExclusionRook(candidateDestinationCoordinate, candidateCoordinateOffset))){
                    break;
                }

                candidateDestinationCoordinate += candidateCoordinateOffset;
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
                        break;
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
    public Chancellor movePiece(final Move move) {
        return new Chancellor(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.rookBonus(this.piecePosition) +
                this.pieceAlliance.knightBonus(this.piecePosition);
    }

    @Override
    public String toString(){
        return PieceType.CHANCELLOR.toString();
    }

    private static boolean isFirstColumnExclusionRook(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.FIRST_COLUMN[currentPosition] &&
                ((candidateOffset == -1));
    }

    private static boolean isEighthColumnExclusionRook(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.EIGHTH_COLUMN[currentPosition] &&
                ((candidateOffset == 1));
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

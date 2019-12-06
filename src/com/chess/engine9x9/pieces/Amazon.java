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
 * This piece is a version of the more commonly known "Amazon", "Giraffe" or "Maharajah"
 * A piece combining the moves of the queen and the knight (value = 1200)
 * Note that this piece is royal (cannot be captured) in Mad Titan's Game and Last Stand
 */

public class Amazon extends Piece {

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES =
            {-10, -9, -8, -1, 1, 8, 9, 10};

    private final static int[] CANDIDATE_MOVE_COORDINATES =
            {-19, -17, -11, -7, 7, 11, 17, 19};

    public Amazon(final Alliance pieceAlliance, final int piecePosition){
        super(PieceType.AMAZON, piecePosition, pieceAlliance, true);
    }

    public Amazon(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove){
        super(PieceType.AMAZON, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board){

        final List<Move> legalMoves = new ArrayList<>();

        for(final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES){
            int candidateDestinationCoordinate = this.piecePosition;
            while(BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)){

                if((isFirstColumnExclusionQueen(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isEighthColumnExclusionQueen(candidateDestinationCoordinate, candidateCoordinateOffset))){
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
    public Amazon movePiece(final Move move) {
        return new Amazon(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return Piece.PieceType.AMAZON.toString();
    }

    //Queen Calculations
    private static boolean isFirstColumnExclusionQueen(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.FIRST_COLUMN[currentPosition] &&
                ((candidateOffset == -10) ||
                        (candidateOffset == -1) ||
                        (candidateOffset == 8));
    }

    private static boolean isEighthColumnExclusionQueen(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.NINTH_COLUMN[currentPosition] &&
                ((candidateOffset == -8) ||
                        (candidateOffset == 1) ||
                        (candidateOffset == 10));
    }
    //Knight Calculations
    private static boolean isFirstColumnExclusionKnight(final int currentPosition, final int candidateOffset){

        return BoardUtilities.INSTANCE.FIRST_COLUMN[currentPosition] &&
                ((candidateOffset == -19) ||
                        (candidateOffset == -11) ||
                        (candidateOffset == 8) ||
                        (candidateOffset == 17));

    }

    private static boolean isSecondColumnExclusionKnight(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.SECOND_COLUMN[currentPosition] &&
                ((candidateOffset == -11) ||
                        (candidateOffset == 8));
    }

    private static boolean isSeventhColumnExclusionKnight(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.EIGHTH_COLUMN[currentPosition] &&
                ((candidateOffset == -8) ||
                        (candidateOffset == 11));
    }

    private static boolean isEighthColumnExclusionKnight(final int currentPosition, final  int candidateOffset){
        return BoardUtilities.INSTANCE.NINTH_COLUMN[currentPosition] &&
                ((candidateOffset == -17) ||
                        (candidateOffset == -8) ||
                        (candidateOffset == 11) ||
                        (candidateOffset == 19));
    }

}

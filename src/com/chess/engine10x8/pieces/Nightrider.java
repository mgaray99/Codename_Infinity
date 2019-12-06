package com.chess.engine10x8.pieces;

import com.chess.engine10x8.Alliance;
import com.chess.engine10x8.board.Board;
import com.chess.engine10x8.board.BoardUtilities;
import com.chess.engine10x8.board.Move;
import com.chess.engine10x8.board.Tile;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine10x8.board.Move.*;

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

public class Nightrider extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES =
            {-17, -15, -10, -6, 6, 10, 15, 17};

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES =
            {-17, -15, -10, -6, 6, 10, 15, 17};


    public Nightrider(final Alliance pieceAlliance, final int piecePosition){
        super(PieceType.NIGHTRIDER, piecePosition, pieceAlliance, true);
    }

    public Nightrider(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove){
        super(PieceType.NIGHTRIDER, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board){

        final List<Move> legalMoves = new ArrayList<>();

        // BISHOP CODE SKELETON
        for(final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES){
            int candidateDestinationCoordinate = this.piecePosition;
            while(BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)){

                if((isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isSecondColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isSeventhColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset))){
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

        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Nightrider movePiece(final Move move) {
        return new Nightrider(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return PieceType.NIGHTRIDER.toString();
    }

    //Edge case methods
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){

        return BoardUtilities.INSTANCE.FIRST_COLUMN[currentPosition] &&
                ((candidateOffset == -17) ||
                        (candidateOffset == -10) ||
                        (candidateOffset == 6) ||
                        (candidateOffset == 15));

    }

    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.SECOND_COLUMN[currentPosition] &&
                ((candidateOffset == -10) ||
                        (candidateOffset == 6));
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.SEVENTH_COLUMN[currentPosition] &&
                ((candidateOffset == -6) ||
                        (candidateOffset == 10));
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final  int candidateOffset){
        return BoardUtilities.INSTANCE.EIGHTH_COLUMN[currentPosition] &&
                ((candidateOffset == -15) ||
                        (candidateOffset == -6) ||
                        (candidateOffset == 10) ||
                        (candidateOffset == 17));
    }

}

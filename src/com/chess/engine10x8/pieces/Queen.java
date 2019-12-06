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
 * Queen explanation by /u/soaringturkeys:
 * The queen cannot beat a king without the help of at least
 * someone else. It can defeat any other piece on the board.
 *
 * White Queen Options:
 *      Captain Marvel
 *
 * Black Queen Options:
 *      Warrior Thanos
 */

public class Queen extends Piece {

    private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES =
            {-9, -8, -7, -1, 1, 7, 8, 9};

    public Queen(final Alliance pieceAlliance, final int piecePosition){
        super(PieceType.QUEEN, piecePosition, pieceAlliance, true);
    }

    public Queen(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove){
        super(PieceType.QUEEN, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board){

        final List<Move> legalMoves = new ArrayList<>();

        for(final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATES){
            int candidateDestinationCoordinate = this.piecePosition;
            while(BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)){

                if((isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset) ||
                        isEigthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset))){
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
    public Queen movePiece(final Move move) {
        return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString(){
        return Piece.PieceType.QUEEN.toString();
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.FIRST_COLUMN[currentPosition] &&
                ((candidateOffset == -9) ||
                        (candidateOffset == -1) ||
                        (candidateOffset == 7));
    }

    private static boolean isEigthColumnExclusion(final int currentPosition, final int candidateOffset){
        return BoardUtilities.INSTANCE.EIGHTH_COLUMN[currentPosition] &&
                ((candidateOffset == -7) ||
                        (candidateOffset == 1) ||
                        (candidateOffset == 9));
    }

}

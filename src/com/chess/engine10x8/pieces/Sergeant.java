package com.chess.engine10x8.pieces;

import com.chess.engine10x8.Alliance;
import com.chess.engine10x8.board.Board;
import com.chess.engine10x8.board.BoardUtilities;
import com.chess.engine10x8.board.Move;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chess.engine10x8.board.Move.*;

/**
 * White Pawn Options:
 *      Hawkeye
 *      Black Widow
 *
 * Black Pawn Options:
 *      Outrider
 *      Chitauri
 */

public class Sergeant extends Piece{

    private final static int[] CANDIDATE_MOVE_COORDINATE = {7, 8, 9, 16};
    protected final boolean canPromote;

    public Sergeant(final Alliance pieceAlliance, final int piecePosition, final boolean canPromote){
        super(PieceType.SERGEANT, piecePosition, pieceAlliance, true);
        this.canPromote = canPromote;
    }

    public Sergeant(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove, final boolean canPromote){
        super(PieceType.SERGEANT, piecePosition, pieceAlliance, isFirstMove);
        this.canPromote = canPromote;
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board){

        final List<Move> legalMoves = new ArrayList<>();

        for(final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE){
            final int candidateDestinationCoordinate = this.piecePosition +
                    (this.getPieceAlliance().getDirection() * currentCandidateOffset);

            if(!BoardUtilities.isValidTileCoordinate(candidateDestinationCoordinate)){
                continue;
            }

            if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){

                if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate) && canPromote()){
                    legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate)));
                } else{
                    legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
                }
            } else if(currentCandidateOffset == 8 && board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                    if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate) &&
                            canPromote()){
                        legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate)));
                    } else{
                        legalMoves.add(new PawnAttackMove(board,this, candidateDestinationCoordinate, pieceOnCandidate));
                    }
                }

            } else if(currentCandidateOffset == 16 && this.isFirstMove() &&
                    ((BoardUtilities.INSTANCE.NINTH_RANK[this.piecePosition] &&
                            this.getPieceAlliance().isBlack()) ||
                            (BoardUtilities.INSTANCE.SECOND_RANK[this.piecePosition] &&
                                    this.getPieceAlliance().isWhite()))){
                final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8);
                if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new SergeantJump(board, this, candidateDestinationCoordinate));
                }
            } else if(currentCandidateOffset == 7 &&
                    !((BoardUtilities.INSTANCE.EIGHTH_COLUMN[this.piecePosition] &&
                            this.pieceAlliance.isWhite()) ||
                            (BoardUtilities.INSTANCE.FIRST_COLUMN[this.piecePosition] &&
                                    this.pieceAlliance.isBlack())) ){
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){

                        if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate) && canPromote()){
                            legalMoves.add(new SergeantPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
                        } else{
                            legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }
                else{
                    if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate) && canPromote()){
                        legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate)));
                    } else{
                        legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
                    }
                }

            } else if(currentCandidateOffset == 9 &&
                    !((BoardUtilities.INSTANCE.FIRST_COLUMN[this.piecePosition] &&
                            this.pieceAlliance.isWhite() ) ||
                            (BoardUtilities.INSTANCE.EIGHTH_COLUMN[this.piecePosition] &&
                                    this.pieceAlliance.isBlack() ) ) ) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {

                        if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate) && canPromote()){
                            legalMoves.add(new SergeantPromotion(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
                        } else{
                            legalMoves.add(new PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }else{
                    if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate) && canPromote()){
                        legalMoves.add(new PawnPromotion(new PawnMove(board, this, candidateDestinationCoordinate)));
                    } else{
                        legalMoves.add(new PawnMove(board, this, candidateDestinationCoordinate));
                    }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public Sergeant movePiece(final Move move) {
        return new Sergeant(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate(), false, canPromote());
    }

    public boolean canPromote(){
        return this.canPromote;
    }

    @Override
    public String toString(){
        return PieceType.SERGEANT.toString();
    }

    public Piece getPromotionPiece(){
        return new Queen(this.pieceAlliance, this.piecePosition, false);
    }
}

package com.chess.engine10x8.player;

import com.chess.engine10x8.Alliance;
import com.chess.engine10x8.board.Board;
import com.chess.engine10x8.board.Move;
import com.chess.engine10x8.pieces.King;
import com.chess.engine10x8.pieces.Piece;
import com.chess.engine10x8.pieces.Amazon;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Amazon playerAmazon;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board,
           final Collection<Move> legalMoves,
           final Collection<Move> opponentMoves){

        this.board = board;
        this.playerAmazon = establishAmazon(); // set to null for White Player in a Mad Titan game
                                            // or for both players in a normal game

        this.playerKing = establishKing(); // set to null for the Black Player in a Mad Titan game

        if((this.playerAmazon == null && this.playerKing != null) ||
                (this.playerAmazon != null && this.playerKing != null)){ // Either King, no Titan or King and Titan
            this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves,
                    calculateKingCastles(legalMoves,
                            opponentMoves)));
            this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(),
                    opponentMoves).isEmpty();
        } else if (this.playerKing == null && this.playerAmazon != null){
            this.legalMoves = ImmutableList.copyOf(legalMoves);
            this.isInCheck = !Player.calculateAttacksOnTile(this.playerAmazon.getPiecePosition(),
                    opponentMoves).isEmpty();
        } else{
            this.legalMoves = ImmutableList.copyOf(legalMoves);
            this.isInCheck = false;
        }


    }

    public King getPlayerKing(){
        return this.playerKing;
    }
    public Amazon getPlayerAmazon(){
        return this.playerAmazon;
    }

    public Collection<Move> getLegalMoves(){
        return this.legalMoves;
    }

    protected static Collection<Move> calculateAttacksOnTile(int piecePosition,
                                                           Collection<Move> moves){
        final List<Move> attackMoves = new ArrayList<>();
        for(final Move move : moves){
            if(piecePosition == move.getDestinationCoordinate()){
                attackMoves.add(move);
            }
        }
        return ImmutableList.copyOf(attackMoves);
    }

    //private King establishKing(){
    //    for(final Piece piece : getActivePieces()){
    //        if (piece.getPieceType().isKing()) {
    //            return (King) piece;
    //        }
    //    }
    //    throw new RuntimeException("Should not reach here! Not a valid board!");
    //}

    private King establishKing(){
        for(final Piece piece : getActivePieces()){
            if (piece.getPieceType().isKing()) {
                if(piece.getPieceType() == Piece.PieceType.AMAZON){
                    continue;
                }
                return (King) piece;
            }
        }
        return null;
    }

    private Amazon establishAmazon(){
        for(final Piece piece : getActivePieces()){
            if (piece.getPieceType() == Piece.PieceType.AMAZON) {
                return (Amazon) piece;
            }
        }
        return null;
    }

    public boolean isMoveLegal(final Move move){
        return this.legalMoves.contains(move);
    }

    //TODO: implement these methods
    public boolean isInCheck(){
        return this.isInCheck;
    }

    public boolean isInCheckMate(){
        return this.isInCheck && !hasEscapeMoves();
    }

    public boolean isInStalemate(){
        return !this.isInCheck && !hasEscapeMoves();
    }

    public boolean isKingSideCastleCapable(){
        return this.playerKing.isKingSideCastleCapable();
    }

    public boolean isQueenSideCastleCapable(){
        return this.playerKing.isQueenSideCastleCapable();
    }

    protected boolean hasEscapeMoves(){
        for(final Move move: this.legalMoves){
            final MoveTransition transition = makeMove(move);
            if(transition.getMoveStatus().isDone()){
                return true;
            }
        }
        return false;
    }

    public boolean isCastled(){
        return false;
    }

    // Used to test whether or not a move can successfully be made by the royal piece
    public static MoveTransition testMove(Board board, Move move){
        final  Board transitionBoard = move.execute();

        Collection<Move> kingAttacks = ImmutableList.of();

        //Horde games - no King, no Titan
        if (transitionBoard.currentPlayer().getOpponent().getPlayerKing() == null && transitionBoard.currentPlayer().getOpponent().getPlayerAmazon() == null){
            kingAttacks = new ArrayList<>();
        }

        //Mad Titan's Game and Last Stand - Titan, no King
        else if (transitionBoard.currentPlayer().getOpponent().getPlayerKing() == null) {
            kingAttacks =
                    Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerAmazon().getPiecePosition(),
                            transitionBoard.currentPlayer().getLegalMoves());

        } else{ //All other games, containing either just a King or a King and a Titan
            kingAttacks =
                    Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                            transitionBoard.currentPlayer().getLegalMoves());
        }

        if(!kingAttacks.isEmpty()){
            return new MoveTransition(board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }


    public MoveTransition makeMove(final Move move){

        if(!isMoveLegal(move)){
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }

        final  Board transitionBoard = move.execute();

        Collection<Move> kingAttacks = ImmutableList.of();

        //Horde games - no King, no Titan
        if (transitionBoard.currentPlayer().getOpponent().getPlayerKing() == null && transitionBoard.currentPlayer().getOpponent().getPlayerAmazon() == null){
            kingAttacks = new ArrayList<>();
        }

        //Mad Titan's Game and Last Stand - Titan, no King
        else if (transitionBoard.currentPlayer().getOpponent().getPlayerKing() == null) {
            kingAttacks =
                    Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerAmazon().getPiecePosition(),
                            transitionBoard.currentPlayer().getLegalMoves());

        } else{ //All other games, containing either just a King or a King and a Titan
            kingAttacks =
                    Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                            transitionBoard.currentPlayer().getLegalMoves());
        }

        if(!kingAttacks.isEmpty()){
            return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }

        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    public abstract Collection<Piece> getActivePieces();

    public abstract Alliance getAlliance();

    public abstract Player getOpponent();

    protected abstract Collection<Move> calculateKingCastles(Collection<Move> playerLegals,
                                                             Collection<Move> opponentsLegals);
}

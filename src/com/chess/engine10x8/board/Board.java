package com.chess.engine10x8.board;

import com.chess.engine10x8.Alliance;
import com.chess.engine10x8.pieces.*;
import com.chess.engine10x8.player.BlackPlayer;
import com.chess.engine10x8.player.Player;
import com.chess.engine10x8.player.WhitePlayer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import java.util.*;

public class Board {

    private final List<Tile> gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    private final WhitePlayer whitePlayer;
    private final BlackPlayer blackPlayer;
    private final Player currentPlayer;

    private final Pawn enPassantPawn;
    private final Sergeant enPassantSergeant;
    private final WolfPawn enPassantWolfPawn;

    private Board(Builder builder){
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);

        this.enPassantPawn = builder.enPassantPawn;
        this.enPassantSergeant = builder.enPassantSergeant;
        this.enPassantWolfPawn = builder.enPassantWolfPawn;
        final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
        final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces);

        this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
        this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
    }

    @Override
    public String toString(){
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < BoardUtilities.NUM_TILES; i++){
            final String tileText = this.gameBoard.get(i).toString();
            builder.append(String.format("%3s", tileText));
            if((i + 1) % BoardUtilities.NUM_TILES_PER_ROW == 0){
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public Player whitePlayer() {
        return this.whitePlayer;
    }

    public Player blackPlayer(){
        return this.blackPlayer;
    }

    public Player currentPlayer(){
        return this.currentPlayer;
    }

    public Pawn getEnPassantPawn(){
        return this.enPassantPawn;
    }

    public Sergeant getEnPassantSergeant(){
        return this.enPassantSergeant;
    }

    public WolfPawn getEnPassantWolfPawn(){
        return this.enPassantWolfPawn;
    }

    public Collection<Piece> getBlackPieces(){
        return this.blackPieces;
    }

    public Collection<Piece> getWhitePieces(){
        return this.whitePieces;
    }



    private Collection<Move> calculateLegalMoves(Collection<Piece> pieces){
        final List<Move> legalMoves = new ArrayList<>();

        for(final Piece piece : pieces){
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }
        return ImmutableList.copyOf(legalMoves);
    }

    private static Collection<Piece> calculateActivePieces(final List<Tile> gameBoard,
                                                    final Alliance alliance){
        final List<Piece> activePieces = new ArrayList<>();

        for(final Tile tile : gameBoard){
            if(tile.isTileOccupied()){
                final Piece piece = tile.getPiece();
                if(piece.getPieceAlliance() == alliance){
                    activePieces.add(piece);
                }
            }
        }
        return ImmutableList.copyOf(activePieces);
    }

    public Tile getTile(final int tileCoordinate){
        return gameBoard.get(tileCoordinate);
    }

    private static List<Tile> createGameBoard(final Builder builder){
        final Tile[] tiles = new Tile[BoardUtilities.NUM_TILES];
        for(int i = 0; i < BoardUtilities.NUM_TILES; i++){
            tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
        }
        return ImmutableList.copyOf(tiles);
    }

    public static Board createWolfBoard(){
        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(new Queen(Alliance.BLACK, 0));
        builder.setPiece(new Chancellor(Alliance.BLACK, 1));
        builder.setPiece(new Archbishop(Alliance.BLACK, 2));
        builder.setPiece(new Rook(Alliance.BLACK, 3));
        builder.setPiece(new Bishop(Alliance.BLACK, 4));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Nightrider(Alliance.BLACK, 6));
        builder.setPiece(new King(Alliance.BLACK, 7, false, false));
        builder.setPiece(new WolfPawn(Alliance.BLACK, 8, true));
        builder.setPiece(new Sergeant(Alliance.BLACK, 9, true));
        builder.setPiece(new Sergeant(Alliance.BLACK, 10, true));
        builder.setPiece(new WolfPawn(Alliance.BLACK, 11, true));
        builder.setPiece(new WolfPawn(Alliance.BLACK, 12, true));
        builder.setPiece(new Sergeant(Alliance.BLACK, 13, true));
        builder.setPiece(new Sergeant(Alliance.BLACK, 14, true));
        builder.setPiece(new WolfPawn(Alliance.BLACK, 15, true));
        builder.setPiece(new WolfPawn(Alliance.BLACK, 17, true));
        builder.setPiece(new WolfPawn(Alliance.BLACK, 18, true));
        builder.setPiece(new WolfPawn(Alliance.BLACK, 21, true));
        builder.setPiece(new WolfPawn(Alliance.BLACK, 22, true));



        // White Layout
        builder.setPiece(new WolfPawn(Alliance.WHITE, 57, true));
        builder.setPiece(new WolfPawn(Alliance.WHITE, 58, true));
        builder.setPiece(new WolfPawn(Alliance.WHITE, 61, true));
        builder.setPiece(new WolfPawn(Alliance.WHITE, 62, true));

        builder.setPiece(new WolfPawn(Alliance.WHITE, 64, true));
        builder.setPiece(new Sergeant(Alliance.WHITE, 65, true));
        builder.setPiece(new Sergeant(Alliance.WHITE, 66, true));
        builder.setPiece(new WolfPawn(Alliance.WHITE, 67, true));
        builder.setPiece(new WolfPawn(Alliance.WHITE, 68, true));
        builder.setPiece(new Sergeant(Alliance.WHITE, 69, true));
        builder.setPiece(new Sergeant(Alliance.WHITE, 70, true));
        builder.setPiece(new WolfPawn(Alliance.WHITE, 71, true));
        builder.setPiece(new King(Alliance.WHITE, 72, false, false));
        builder.setPiece(new Nightrider(Alliance.WHITE, 73));
        builder.setPiece(new Bishop(Alliance.WHITE, 74));
        builder.setPiece(new Bishop(Alliance.WHITE, 75));
        builder.setPiece(new Rook(Alliance.WHITE, 76));
        builder.setPiece(new Archbishop(Alliance.WHITE, 77));
        builder.setPiece(new Chancellor(Alliance.WHITE, 78));
        builder.setPiece(new Queen(Alliance.WHITE, 79));

        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    }

    public static Board createAlmostChessBoard(){
        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Chancellor(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 4, true, true));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 9, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 10, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 11, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 12, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 14, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 15, true));

        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 48, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 49, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 50, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 51, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 52, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 53, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 54, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 55, true));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Chancellor(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.WHITE, 60, true, true));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));

        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    }

    public static Board createSortOfAlmostChessBoard(){
        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 4, true, true));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 9, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 10, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 11, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 12, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 14, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 15, true));

        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 48, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 49, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 50, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 51, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 52, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 53, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 54, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 55, true));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Chancellor(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.WHITE, 60, true, true));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));

        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    }

    public static Board createChess960Board(){
        final Builder builder = new Builder();

        List<Integer> randomMainRankPositions = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            randomMainRankPositions.add(i);
        }
        Collections.shuffle(randomMainRankPositions);

        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, randomMainRankPositions.get(0)));
        builder.setPiece(new Knight(Alliance.BLACK, randomMainRankPositions.get(1)));
        builder.setPiece(new Bishop(Alliance.BLACK, randomMainRankPositions.get(2)));
        builder.setPiece(new Queen(Alliance.BLACK, randomMainRankPositions.get(3)));
        builder.setPiece(new King(Alliance.BLACK, randomMainRankPositions.get(4), true, true));
        builder.setPiece(new Bishop(Alliance.BLACK, randomMainRankPositions.get(5)));
        builder.setPiece(new Knight(Alliance.BLACK, randomMainRankPositions.get(6)));
        builder.setPiece(new Rook(Alliance.BLACK, randomMainRankPositions.get(7)));
        builder.setPiece(new Pawn(Alliance.BLACK, 8, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 9, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 10, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 11, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 12, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 14, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 15, true));

        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 48, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 49, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 50, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 51, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 52, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 53, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 54, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 55, true));
        builder.setPiece(new Rook(Alliance.WHITE, randomMainRankPositions.get(0)+56));
        builder.setPiece(new Knight(Alliance.WHITE, randomMainRankPositions.get(1)+56));
        builder.setPiece(new Bishop(Alliance.WHITE, randomMainRankPositions.get(2)+56));
        builder.setPiece(new Queen(Alliance.WHITE, randomMainRankPositions.get(3)+56));
        builder.setPiece(new King(Alliance.WHITE, randomMainRankPositions.get(4)+56, true, true));
        builder.setPiece(new Bishop(Alliance.WHITE, randomMainRankPositions.get(5)+56));
        builder.setPiece(new Knight(Alliance.WHITE, randomMainRankPositions.get(6)+56));
        builder.setPiece(new Rook(Alliance.WHITE, randomMainRankPositions.get(7)+56));

        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    }

    public static Board createMadBlackKingBoard(){
        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(new Amazon(Alliance.BLACK, 4));

        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 48, false));
        builder.setPiece(new Pawn(Alliance.WHITE, 49, false));
        builder.setPiece(new Pawn(Alliance.WHITE, 50, false));
        builder.setPiece(new Pawn(Alliance.WHITE, 51, false));
        builder.setPiece(new Pawn(Alliance.WHITE, 52, false));
        builder.setPiece(new Pawn(Alliance.WHITE, 53, false));
        builder.setPiece(new Pawn(Alliance.WHITE, 54, false));
        builder.setPiece(new Pawn(Alliance.WHITE, 55, false));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Queen(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.WHITE, 60, true, true));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));

        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    }

    public static Board createMadWhiteKingBoard(){
        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 4, true, true));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 9, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 10, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 11, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 12, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 13, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 14, false));
        builder.setPiece(new Pawn(Alliance.BLACK, 15, false));

        // White Layout
        builder.setPiece(new Amazon(Alliance.WHITE, 60));

        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    }

    public static Board createHordeBoard(){
        final Builder builder = new Builder();

        // Black Layout
        builder.setPiece(new Pawn(Alliance.BLACK, 0, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 1, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 2, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 3, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 4, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 5, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 6, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 7, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 8, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 9, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 10, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 11, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 12, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 13, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 14, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 15, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 16, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 17, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 18, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 19, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 20, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 21, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 22, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 23, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 24, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 25, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 26, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 27, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 28, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 29, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 30, true));
        builder.setPiece(new Pawn(Alliance.BLACK, 31, true));

        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 48, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 49, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 50, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 51, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 52, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 53, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 54, true));
        builder.setPiece(new Pawn(Alliance.WHITE, 55, true));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Queen(Alliance.WHITE, 59));
        builder.setPiece(new King(Alliance.WHITE, 60, true, true));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));

        builder.setMoveMaker(Alliance.WHITE);

        return builder.build();
    }

    public Iterable<Move> getAllLegalMoves() {
        return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(),
                                                                this.blackPlayer.getLegalMoves()));
    }

    public static class Builder{

        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;
        Pawn enPassantPawn;
        Sergeant enPassantSergeant;
        WolfPawn enPassantWolfPawn;

        public Builder(){
            this.boardConfig = new HashMap<>();
        }

        public Builder setPiece(final Piece piece){
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance nextMoveMaker){
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build(){
            return new Board(this);
        }

        public void setEnPassantPawn(Pawn enPassantPawn){
            this.enPassantPawn = enPassantPawn;
        }
        public void setEnPassantSergeant(Sergeant enPassantSergeant){
            this.enPassantSergeant = enPassantSergeant;
        }
        public void setEnPassantWolfPawn(WolfPawn enPassantWolfPawn){
            this.enPassantWolfPawn = enPassantWolfPawn;
        }

    }
}

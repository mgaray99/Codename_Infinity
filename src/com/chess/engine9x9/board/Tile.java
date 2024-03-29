package com.chess.engine9x9.board;

import com.chess.engine9x9.pieces.Piece;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mike Garay on 10/24/2019
 */

public abstract class Tile {
    protected final int tileCoordinate; //preventing mutability

    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles(){

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for(int i = 0; i < BoardUtilities.NUM_TILES; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }

        //Alternate return statement:
        //return Collections.unmodifiableMap(emptyTileMap);

        //Using guava:
        return ImmutableMap.copyOf(emptyTileMap);
    }

    public static Tile createTile(final int tileCoordinate, final Piece piece){
        // return the result of "boolean statement" ? "true result" : "false result"
        // Effectively:
        //    if (piece != null){
        //        return new OccupiedTile(tileCoordinate, piece);
        //    } else{
        //        return EMPTY_TILES.get(tileCoordinate);
        //    }
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    private Tile(final int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();

    public abstract Piece getPiece();

    public int getTileCoordinate(){
        return this.tileCoordinate;
    }

    public static final class EmptyTile extends Tile {
        EmptyTile(final int coordinate){
            super(coordinate);
        }

        @Override
        public String toString(){
            return "-";
        }

        @Override
        public boolean isTileOccupied(){
            return false;
        }

        @Override
        public Piece getPiece(){
            return null;
        }
    }

    public static final class OccupiedTile extends Tile {
        private final Piece pieceOnTile; //preventing mutability

        private OccupiedTile(int tileCoordinate, final Piece pieceOnTile){
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public String toString(){
            //TODO: ?
            return getPiece().getPieceAlliance().isBlack() ?
                    getPiece().toString().toLowerCase() :
                    getPiece().toString();
        }

        @Override
        public boolean isTileOccupied(){
            return true;
        }

        @Override
        public Piece getPiece(){
            return this.pieceOnTile;
        }
    }
}

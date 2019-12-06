package com.chess.engine9x9.board;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public enum BoardUtilities {

    INSTANCE;

    public final boolean[] FIRST_COLUMN = initColumn(0);
    public final boolean[] SECOND_COLUMN = initColumn(1);
    public final boolean[] EIGHTH_COLUMN = initColumn(7);
    public final boolean[] NINTH_COLUMN = initColumn(8);


    public final boolean[] NINTH_RANK = initRow(0);
    public final boolean[] EIGHTH_RANK = initRow(9);
    public final boolean[] SEVENTH_RANK = initRow(18);
    public final boolean[] SIXTH_RANK = initRow(27);
    public final boolean[] FIFTH_RANK = initRow(36);
    public final boolean[] FOURTH_RANK = initRow(45);
    public final boolean[] THIRD_RANK = initRow(54);
    public final boolean[] SECOND_RANK = initRow(63);
    public final boolean[] FIRST_RANK = initRow(72);

    public final String[] ALGEBRAIC_NOTATION = initializeAlgebraicNotation();
    public final Map<String, Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();

    public static final int NUM_TILES = 81;
    public static final int NUM_TILES_PER_ROW = 9;
    public static final int START_TILE_INDEX = 0;

    private static boolean[] initColumn(int columnNumber){
        final boolean[] column = new boolean[NUM_TILES];

        do{
            column[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        } while(columnNumber < NUM_TILES);
        return column;
    }

    private static boolean[] initRow(int rowNumber){
        final boolean[] row = new boolean[NUM_TILES];
        do{
            row[rowNumber] = true;
            rowNumber++;
        } while(rowNumber % NUM_TILES_PER_ROW != 0);
        return row;
    }

    private Map<String, Integer> initializePositionToCoordinateMap(){
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = START_TILE_INDEX; i < NUM_TILES; i++){
            positionToCoordinate.put(ALGEBRAIC_NOTATION[i], i);
        }
        return ImmutableMap.copyOf(positionToCoordinate);
    }

    private static String[] initializeAlgebraicNotation(){
        return new String[] {
                "a9", "b9", "c9", "d9", "e9", "f9", "g9", "h9", "i9",
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8", "i8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7", "i7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6", "i6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5", "i5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4", "i4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3", "i3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2", "i2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "i1"
        };
    }

    public static boolean isValidTileCoordinate(final int coordinate){
        return coordinate >= START_TILE_INDEX && coordinate < NUM_TILES;
    }

    public int getCoordinateAtPosition(final String position) {
        return POSITION_TO_COORDINATE.get(position);
    }

    public String getPositionAtCoordinate(final int coordinate){
        return ALGEBRAIC_NOTATION[coordinate];
    }
}

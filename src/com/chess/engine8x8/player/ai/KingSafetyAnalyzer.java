package com.chess.engine8x8.player.ai;

import com.chess.engine8x8.board.BoardUtilities;
import com.chess.engine8x8.board.Move;
import com.chess.engine8x8.pieces.Piece;
import com.chess.engine8x8.player.Player;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class KingSafetyAnalyzer {

    private static final KingSafetyAnalyzer INSTANCE = new KingSafetyAnalyzer();
    private static final List<List<Boolean>> COLUMNS = initColumns();

    private KingSafetyAnalyzer() {
    }

    public static KingSafetyAnalyzer get() {
        return INSTANCE;
    }

    public static List<Boolean> convertBooleanArrayToList(boolean[] arr){
        List<Boolean> list = new ArrayList<>();
        for(boolean b: arr){
            list.add(b);
        }
        return list;
    }

    private static List<List<Boolean>> initColumns() {
        final List<List<Boolean>> columns = new ArrayList<>();
        columns.add(convertBooleanArrayToList(BoardUtilities.INSTANCE.FIRST_COLUMN));
        columns.add(convertBooleanArrayToList(BoardUtilities.INSTANCE.SECOND_COLUMN));
        columns.add(convertBooleanArrayToList(BoardUtilities.INSTANCE.THIRD_COLUMN));
        columns.add(convertBooleanArrayToList(BoardUtilities.INSTANCE.FOURTH_COLUMN));
        columns.add(convertBooleanArrayToList(BoardUtilities.INSTANCE.FIFTH_COLUMN));
        columns.add(convertBooleanArrayToList(BoardUtilities.INSTANCE.SIXTH_COLUMN));
        columns.add(convertBooleanArrayToList(BoardUtilities.INSTANCE.SEVENTH_COLUMN));
        columns.add(convertBooleanArrayToList(BoardUtilities.INSTANCE.EIGHTH_COLUMN));
        return ImmutableList.copyOf(columns);
    }

    public KingDistance calculateKingTropism(final Player player) {
        final int playerKingSquare = player.getPlayerKing().getPiecePosition();
        final Collection<Move> enemyMoves = player.getOpponent().getLegalMoves();
        Piece closestPiece = null;
        int closestDistance = Integer.MAX_VALUE;
        for(final Move move : enemyMoves) {
            final int currentDistance = calculateChebyshevDistance(playerKingSquare, move.getDestinationCoordinate());
            if(currentDistance < closestDistance) {
                closestDistance = currentDistance;
                closestPiece = move.getMovedPiece();
            }
        }
        return new KingDistance(closestPiece, closestDistance);
    }

    private int calculateChebyshevDistance(final int kingTileId,
                                           final int enemyAttackTileId) {

        final int squareOneRank = getRank(kingTileId);
        final int squareTwoRank = getRank(enemyAttackTileId);

        final int squareOneFile = getFile(kingTileId);
        final int squareTwoFile = getFile(enemyAttackTileId);

        final int rankDistance = Math.abs(squareTwoRank - squareOneRank);
        final int fileDistance = Math.abs(squareTwoFile - squareOneFile);

        return Math.max(rankDistance, fileDistance);
    }

    private static int getFile(final int coordinate) {
        if(convertBooleanArrayToList(BoardUtilities.INSTANCE.FIRST_COLUMN).get(coordinate)) {
            return 1;
        } else if(convertBooleanArrayToList(BoardUtilities.INSTANCE.SECOND_COLUMN).get(coordinate)) {
            return 2;
        } else if(convertBooleanArrayToList(BoardUtilities.INSTANCE.THIRD_COLUMN).get(coordinate)) {
            return 3;
        } else if(convertBooleanArrayToList(BoardUtilities.INSTANCE.FOURTH_COLUMN).get(coordinate)) {
            return 4;
        } else if(convertBooleanArrayToList(BoardUtilities.INSTANCE.FIFTH_COLUMN).get(coordinate)) {
            return 5;
        } else if(convertBooleanArrayToList(BoardUtilities.INSTANCE.SIXTH_COLUMN).get(coordinate)) {
            return 6;
        } else if(convertBooleanArrayToList(BoardUtilities.INSTANCE.SEVENTH_COLUMN).get(coordinate)) {
            return 7;
        } else if(convertBooleanArrayToList(BoardUtilities.INSTANCE.EIGHTH_COLUMN).get(coordinate)) {
            return 8;
        }
        throw new RuntimeException("should not reach here!");
    }

    private static int getRank(final int coordinate) {
        if(convertBooleanArrayToList(BoardUtilities.INSTANCE.FIRST_RANK).get(coordinate)) {
            return 1;
        } else if(convertBooleanArrayToList(BoardUtilities.INSTANCE.SECOND_RANK).get(coordinate)) {
            return 2;
        } else if(convertBooleanArrayToList(BoardUtilities.INSTANCE.THIRD_RANK).get(coordinate)) {
            return 3;
        } else if(convertBooleanArrayToList(BoardUtilities.INSTANCE.FOURTH_RANK).get(coordinate)) {
            return 4;
        } else if(convertBooleanArrayToList(BoardUtilities.INSTANCE.FIFTH_RANK).get(coordinate)) {
            return 5;
        } else if(convertBooleanArrayToList(BoardUtilities.INSTANCE.SIXTH_RANK).get(coordinate)) {
            return 6;
        } else if(convertBooleanArrayToList(BoardUtilities.INSTANCE.SEVENTH_RANK).get(coordinate)) {
            return 7;
        } else if(convertBooleanArrayToList(BoardUtilities.INSTANCE.EIGHTH_RANK).get(coordinate)) {
            return 8;
        }
        throw new RuntimeException("should not reach here!");
    }

    static class KingDistance {

        final Piece enemyPiece;
        final int distance;

        KingDistance(final Piece enemyDistance,
                     final int distance) {
            this.enemyPiece = enemyDistance;
            this.distance = distance;
        }

        public Piece getEnemyPiece() {
            return enemyPiece;
        }

        public int getDistance() {
            return distance;
        }

        public int tropismScore() {
            return (enemyPiece.getPieceValue()/10) * distance;
        }

    }

}
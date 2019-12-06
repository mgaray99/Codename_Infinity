package com.chess.engine10x8.pieces;

import com.chess.engine10x8.Alliance;
import com.chess.engine10x8.board.Board;
import com.chess.engine10x8.board.Move;

import java.util.Collection;

/**
 * Created by Mike Garay on 10/24/2019
 */

public abstract class Piece {

    protected final PieceType pieceType;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;
    private final int cachedHashCode;

    public Piece(final PieceType pieceType,
                 final int piecePosition,
                 final Alliance pieceAlliance,
                 final boolean isFirstMove){
        this.pieceType = pieceType;
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        this.isFirstMove = isFirstMove;
        this.cachedHashCode = computeHashCode();
    }

    private int computeHashCode(){
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(final Object other){
        if(this == other){
            return true;
        }
        if(!(other instanceof Piece)){
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.getPiecePosition() &&
                pieceType == otherPiece.getPieceType() &&
                pieceAlliance == otherPiece.getPieceAlliance() &&
                isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode(){
        return this.cachedHashCode;
    }

    public int getPiecePosition(){
        return this.piecePosition;
    }

    public Alliance getPieceAlliance(){
        return this.pieceAlliance;
    }

    public boolean isFirstMove(){
        return this.isFirstMove;
    }

    public PieceType getPieceType(){
        return this.pieceType;
    }

    public int getPieceValue(){
        return this.pieceType.getPieceValue();
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public abstract Piece movePiece(Move move);

    public enum PieceType{

        PAWN("P", 100){
            @Override
            public boolean isKing(){
                return false;
            }

            @Override
            public boolean isRook(){
                return false;
            }
        },
        WOLFPAWN("P", 100){
            @Override
            public boolean isKing(){
                return false;
            }

            @Override
            public boolean isRook(){
                return false;
            }
        },
        KNIGHT("N", 300){
            @Override
            public boolean isKing(){
                return false;
            }

            @Override
            public boolean isRook(){
                return false;
            }
        },
        BISHOP( "B", 300){
            @Override
            public boolean isKing(){
                return false;
            }

            @Override
            public boolean isRook(){
                return false;
            }
        },
        ROOK("R", 500){
            @Override
            public boolean isKing(){
                return false;
            }

            @Override
            public boolean isRook(){
                return true;
            }
        },
        QUEEN("Q", 900){
            @Override
            public boolean isKing(){
                return false;
            }

            @Override
            public boolean isRook(){
                return false;
            }
        },
        KING( "K", 10000){
            @Override
            public boolean isKing(){
                return true;
            }

            @Override
            public boolean isRook(){
                return false;
            }
        }, AMAZON("QN", 1200){
            @Override
            public boolean isKing(){
                return true;
            }

            @Override
            public boolean isRook(){
                return false;
            }
        }, CHANCELLOR("RN", 800){
            @Override
            public boolean isKing(){
                return false;
            }

            @Override
            public boolean isRook(){
                return true;
            }
        }, ARCHBISHOP("BN", 600){
            @Override
            public boolean isKing(){
                return false;
            }

            @Override
            public boolean isRook(){
                return false;
            }
        }, CENTAUR("MN", 600){
            @Override
            public boolean isKing(){
                return false;
            }

            @Override
            public boolean isRook(){
                return false;
            }
        }, MANN("M", 300){
            @Override
            public boolean isKing(){
                return false;
            }

            @Override
            public boolean isRook(){
                return false;
            }
        }, NIGHTRIDER("NN", 600){
            @Override
            public boolean isKing(){
                return false;
            }

            @Override
            public boolean isRook(){
                return false;
            }
        }, SERGEANT("PP", 200){
            @Override
            public boolean isKing(){
                return false;
            }

            @Override
            public boolean isRook(){
                return false;
            }
        }, AMAZONRIDER("QNN", 1500){
            @Override
            public boolean isKing(){
                return false;
            }

            @Override
            public boolean isRook(){
                return false;
            }
        };

        private String pieceName;
        private int pieceValue;

        PieceType(final String pieceName, final int pieceValue){
            this.pieceName = pieceName;
            this.pieceValue = pieceValue;
        }

        @Override
        public String toString(){
            return this.pieceName;
        }

        public String toFullString(){
            String first = this.pieceName;
            String fullName;
            switch(first){
                case "P": fullName = "PAWN";
                    break;
                case "N": fullName = "KNIGHT";
                    break;
                case "B": fullName = "BISHOP";
                    break;
                case "R": fullName = "ROOK";
                    break;
                case "Q": fullName = "QUEEN";
                    break;
                case "K": fullName = "KING";
                    break;
                case "QN": fullName = "AMAZON";
                    break;
                case "RN": fullName = "CHANCELLOR";
                    break;
                case "BN": fullName = "ARCHBISHOP";
                    break;
                case "MN": fullName = "CENTAUR";
                    break;
                case "M": fullName = "MANN";
                    break;
                // These are Wolf Chess pieces, for which I tried to make up unique notations for
                case "NN": fullName = "NIGHTRIDER";
                    break;
                case "PP": fullName = "SERGEANT";
                    break;
                case "QNN": fullName = "AMAZONRIDER";
                    break;
                default: fullName = "Error";
            }
            return fullName;
        }

        public int getPieceValue(){
            return this.pieceValue;
        }

        public abstract boolean isKing();

        public abstract boolean isRook();
    }
}


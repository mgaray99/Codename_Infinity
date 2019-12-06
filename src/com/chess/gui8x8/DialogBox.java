package com.chess.gui8x8;

import com.chess.engine8x8.board.Move;
import com.chess.engine8x8.player.MoveStatus;
import com.chess.engine8x8.player.MoveTransition;
import com.chess.engine8x8.player.Player;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.io.File;

public class DialogBox extends JPanel {
    public static JLabel gameLabel;

    private static File projectDirectory = new File(".");
    private static String projectDirectoryPath = projectDirectory.getAbsolutePath();
    private static String defaultPieceImagesPath = projectDirectoryPath.substring(0, projectDirectoryPath.length()-1) + "/src/Piece_Images/Characters/";

    private static final Dimension DIALOG_BOX_DIMENSION = new Dimension(1000, 100);
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    DialogBox(String gameMode){
        this.setLayout(new BorderLayout());
        this.setPreferredSize(DIALOG_BOX_DIMENSION);
        this.setBorder(PANEL_BORDER);
        this.gameLabel = new JLabel(makeInitialLabel(gameMode));
        this.add(gameLabel, BorderLayout.CENTER);
        this.gameLabel.setFont(new Font("Verdana", Font.BOLD, 16));
        this.setVisible(true);
    }

    public static String makeInitialLabel(String gameMode){
        if(gameMode == "Clash of the Avengers"){
            String text = " " +"\"They're not stopping, and neither are we.\"" + "   " +
                    "Standard Chess, in the style of the Clash of the Avengers.";
            return text;
        }
        else if(gameMode == "Battle of Sokovia"){
            String text = " " +"\"Like the old man said - together.\"" + "   " +
                    "Standard Chess, in the style of the Battle of Sokovia.";
            return text;
        }
        else if(gameMode == "Battle of Xandar"){
            String text = " " +"\"You said it yourself...We're the Guardians of the Galaxy.\"" + "   " +
                    "Standard Chess, in the style of the Battle of Xandar.";
            return text;
        }
        else if(gameMode == "Battle of New York"){
            String text = " " +"\"If we can't protect the Earth, you can be damned sure we'll avenge it.\"" + "   " +
                    "Standard Chess, in the style of the Battle of New York.";
            return text;
        }
        else if(gameMode == "Battle of Wakanda"){
            String text = " " +"\"Wakanda Forever!\"" + "   " +
                    "Standard Chess, in the style of the Battle of Wakanda.";
            return text;
        }
        else if(gameMode == "Battle of Titan"){
            String text = " " +"\"I think you'll find our will equal to yours.\"" + "   " +
                    "Standard Chess, in the style of the Battle of Titan.";
            return text;
        }
        else if(gameMode == "Battle of Earth"){
            String text = " " + "\"Avengers! ...Assemble.\"" + "   " +
                    "Standard Chess, in the style of the Battle of Earth.";
            return text;
        }else if (gameMode == "Battle of Wakanda: Horde"){
            String text = " " +"\"The Black Order has blood to spare.\"" + "   " +
                    "Horde chess, in the style of the Battle of Wakanda.";
            return text;
        }
        else if (gameMode == "Battle of Earth: Horde"){
            String text = " " +"\"Don't worry...she's got help.\"" + "   " +
                    "Horde chess, in the style of the Battle of Earth.";
            return text;
        }else if (gameMode == "Battle of Sokovia: Horde"){
            String text = " " +"\"This is exactly what I wanted: All of you, against all of me.\"" + "   " +
                    "Horde chess, in the style of the Battle of Sokovia.";
            return text;
        }
        else if (gameMode == "Battle of New York: Horde"){
            String text = " " +"\"Call it, Captain.\"" + "   " +
                    "Horde chess, in the style of the Battle of New York.";
            return text;
        }
        else{
            throw new RuntimeException("Should not reach here! DialogBox error?");
        }
    }

    public static void updateLabel(Move move){
        if(move.getMovedPiece() == null){
            return;
        }
        if(Table.get().getGameBoard().currentPlayer().isInCheckMate()){
            setLabel("Checkmate", Table.get().getGameBoard().currentPlayer().getAlliance().toString());
            return;
        }
        if(Table.get().getGameBoard().currentPlayer().isInStalemate()){
            setLabel("Stalemate", Table.get().getGameBoard().currentPlayer().getAlliance().toString());
            return;
        }
        DialogBox.gameLabel.setText(getLastMoveMade(move));
    }

    public static String checkWarning(String currentPlayer){
        String royalPiece;
        String royalPieceType;
        String royalCharacter;
        String finalRoyalCharacter;
        if(ChooseGame.iconMap.containsKey("WK") && ChooseGame.iconMap.containsKey("BK")){
            royalPiece = currentPlayer.substring(0,1) + "K";
            royalPieceType = "KING";
            String royalCharacterFile = ChooseGame.iconMap.get(royalPiece).substring(defaultPieceImagesPath.length());
            royalCharacter = royalCharacterFile.substring(0, royalCharacterFile.indexOf('.'));
            if(royalCharacter.contains("_")){
                royalCharacter = royalCharacter.replace('_', ' ');
            }
            if(royalCharacter.contains("-")){
                royalCharacter = royalCharacter.replace('-', '(');
                finalRoyalCharacter = royalCharacter.substring(0, royalCharacter.indexOf('(')) + " " + royalCharacter.substring(royalCharacter.indexOf('(')) + ")";
            }
            else{
                finalRoyalCharacter = royalCharacter;
            }
            if(finalRoyalCharacter.contains("@")) finalRoyalCharacter = finalRoyalCharacter.replace('@', '-');
        }
        else if(ChooseGame.iconMap.containsKey("WT") && currentPlayer == "WHITE"){
            royalPiece = currentPlayer.substring(0,1) + "QN";
            royalPieceType = "AMAZON";
            String royalCharacterFile = ChooseGame.iconMap.get(royalPiece).substring(defaultPieceImagesPath.length());
            royalCharacter = royalCharacterFile.substring(0, royalCharacterFile.indexOf('.'));
            if(royalCharacter.contains("_")){
                royalCharacter = royalCharacter.replace('_', ' ');
            }
            if(royalCharacter.contains("-")){
                royalCharacter = royalCharacter.replace('-', '(');
                finalRoyalCharacter = royalCharacter.substring(0, royalCharacter.indexOf('(')) + " " + royalCharacter.substring(royalCharacter.indexOf('(')) + ")";
            }
            else{
                finalRoyalCharacter = royalCharacter;
            }
            if(finalRoyalCharacter.contains("@")) finalRoyalCharacter = finalRoyalCharacter.replace('@', '-');
        }
        else if(ChooseGame.iconMap.containsKey("BQN") && currentPlayer == "BLACK"){
            //System.out.println("Checkmate!");
            royalPiece = currentPlayer.substring(0,1) + "QN";
            royalPieceType = "AMAZON";
            String royalCharacterFile = ChooseGame.iconMap.get(royalPiece).substring(defaultPieceImagesPath.length());
            royalCharacter = royalCharacterFile.substring(0, royalCharacterFile.indexOf('.'));
            if(royalCharacter.contains("_")){
                royalCharacter = royalCharacter.replace('_', ' ');
            }
            if(royalCharacter.contains("-")){
                royalCharacter = royalCharacter.replace('-', '(');
                finalRoyalCharacter = royalCharacter.substring(0, royalCharacter.indexOf('(')) + " " + royalCharacter.substring(royalCharacter.indexOf('(')) + ")";
            }
            else{
                finalRoyalCharacter = royalCharacter;
            }
            if(finalRoyalCharacter.contains("@")) finalRoyalCharacter = finalRoyalCharacter.replace('@', '-');
        }
        else{
            //System.out.println("Checkmate!");
            royalPiece = currentPlayer.substring(0,1) + "K";
            royalPieceType = "KING";
            //System.out.println(royalPiece);
            String royalCharacterFile = ChooseGame.iconMap.get(royalPiece).substring(defaultPieceImagesPath.length());
            royalCharacter = royalCharacterFile.substring(0, royalCharacterFile.indexOf('.'));
            if(royalCharacter.contains("_")){
                royalCharacter = royalCharacter.replace('_', ' ');
            }
            if(royalCharacter.contains("-")){
                royalCharacter = royalCharacter.replace('-', '(');
                finalRoyalCharacter = royalCharacter.substring(0, royalCharacter.indexOf('(')) + " " + royalCharacter.substring(royalCharacter.indexOf('(')) + ")";
            }
            else{
                finalRoyalCharacter = royalCharacter;
            }
            if(finalRoyalCharacter.contains("@")) finalRoyalCharacter = finalRoyalCharacter.replace('@', '-');
        }
        return currentPlayer + "'S " + royalPieceType + " " + finalRoyalCharacter + " is in Check!";
    }

    public static void setLabel(String gameOver, String currentPlayer){
        if(gameOver == "Check"){
            DialogBox.gameLabel.setText(currentPlayer + ", you're in Check!");
            return;
        }
        else if(gameOver == "Checkmate" || gameOver == "Stalemate"){
            String royalPiece;
            String royalPieceType;
            String royalCharacter;
            String finalRoyalCharacter;
            if(ChooseGame.iconMap.containsKey("WK") && ChooseGame.iconMap.containsKey("BK")){
                royalPiece = currentPlayer.substring(0,1) + "K";
                royalPieceType = "KING";
                String royalCharacterFile = ChooseGame.iconMap.get(royalPiece).substring(defaultPieceImagesPath.length());
                royalCharacter = royalCharacterFile.substring(0, royalCharacterFile.indexOf('.'));
                if(royalCharacter.contains("_")){
                    royalCharacter = royalCharacter.replace('_', ' ');
                }
                if(royalCharacter.contains("-")){
                    royalCharacter = royalCharacter.replace('-', '(');
                    finalRoyalCharacter = royalCharacter.substring(0, royalCharacter.indexOf('(')) + " " + royalCharacter.substring(royalCharacter.indexOf('(')) + ")";
                }
                else{
                    finalRoyalCharacter = royalCharacter;
                }
                if(finalRoyalCharacter.contains("@")) finalRoyalCharacter = finalRoyalCharacter.replace('@', '-');
            }
            else if(ChooseGame.iconMap.containsKey("WT") && currentPlayer == "WHITE"){
                royalPiece = currentPlayer.substring(0,1) + "QN";
                royalPieceType = "AMAZON";
                String royalCharacterFile = ChooseGame.iconMap.get(royalPiece).substring(defaultPieceImagesPath.length());
                royalCharacter = royalCharacterFile.substring(0, royalCharacterFile.indexOf('.'));
                if(royalCharacter.contains("_")){
                    royalCharacter = royalCharacter.replace('_', ' ');
                }
                if(royalCharacter.contains("-")){
                    royalCharacter = royalCharacter.replace('-', '(');
                    finalRoyalCharacter = royalCharacter.substring(0, royalCharacter.indexOf('(')) + " " + royalCharacter.substring(royalCharacter.indexOf('(')) + ")";
                }
                else{
                    finalRoyalCharacter = royalCharacter;
                }
                if(finalRoyalCharacter.contains("@")) finalRoyalCharacter = finalRoyalCharacter.replace('@', '-');
            }
            else if(ChooseGame.iconMap.containsKey("BQN") && currentPlayer == "BLACK"){
                //System.out.println("Checkmate!");
                royalPiece = currentPlayer.substring(0,1) + "QN";
                royalPieceType = "AMAZON";
                String royalCharacterFile = ChooseGame.iconMap.get(royalPiece).substring(defaultPieceImagesPath.length());
                royalCharacter = royalCharacterFile.substring(0, royalCharacterFile.indexOf('.'));
                if(royalCharacter.contains("_")){
                    royalCharacter = royalCharacter.replace('_', ' ');
                }
                if(royalCharacter.contains("-")){
                    royalCharacter = royalCharacter.replace('-', '(');
                    finalRoyalCharacter = royalCharacter.substring(0, royalCharacter.indexOf('(')) + " " + royalCharacter.substring(royalCharacter.indexOf('(')) + ")";
                }
                else{
                    finalRoyalCharacter = royalCharacter;
                }
                if(finalRoyalCharacter.contains("@")) finalRoyalCharacter = finalRoyalCharacter.replace('@', '-');
            }else{
                //System.out.println("Checkmate!");
                royalPiece = currentPlayer.substring(0,1) + "K";
                royalPieceType = "KING";
                String royalCharacterFile = ChooseGame.iconMap.get(royalPiece).substring(defaultPieceImagesPath.length());
                royalCharacter = royalCharacterFile.substring(0, royalCharacterFile.indexOf('.'));
                if(royalCharacter.contains("_")){
                    royalCharacter = royalCharacter.replace('_', ' ');
                }
                if(royalCharacter.contains("-")){
                    royalCharacter = royalCharacter.replace('-', '(');
                    finalRoyalCharacter = royalCharacter.substring(0, royalCharacter.indexOf('(')) + " " + royalCharacter.substring(royalCharacter.indexOf('(')) + ")";
                }
                else{
                    finalRoyalCharacter = royalCharacter;
                }
                if(finalRoyalCharacter.contains("@")) finalRoyalCharacter = finalRoyalCharacter.replace('@', '-');
            }
            DialogBox.gameLabel.setText("Game Over! " + currentPlayer + "'S " + royalPieceType + " " + finalRoyalCharacter + " is in " + gameOver + "!");
        }
        else{
            DialogBox.gameLabel.setText("Game Over! " + Table.get().getGameBoard().currentPlayer().getAlliance() + " has run out of movable pieces!");
            return;
        }
    }

    public static String getLastMoveMade(Move move){

        String movedPiece = move.getMovedPiece().getPieceAlliance().toString().substring(0, 1) + move.getMovedPiece().toString();
        String movedCharacterFile = ChooseGame.iconMap.get(movedPiece).substring(defaultPieceImagesPath.length());
        String movedCharacter = movedCharacterFile.substring(0, movedCharacterFile.indexOf('.'));
        if(movedCharacter.contains("_")){
            movedCharacter = movedCharacter.replace('_', ' ');
        }
        String finalMovedCharacter;
        if(movedCharacter.contains("-")){
            movedCharacter = movedCharacter.replace('-', '(');
            finalMovedCharacter = movedCharacter.substring(0, movedCharacter.indexOf('(')) + " " + movedCharacter.substring(movedCharacter.indexOf('(')) + ")";
        }
        else{
            finalMovedCharacter = movedCharacter;
        }
        if(finalMovedCharacter.contains("@")) finalMovedCharacter = finalMovedCharacter.replace('@', '-');
        String currentPlayer = move.getMovedPiece().getPieceAlliance().toString();

        if(move.isAttack()){
            String attackedPiece = move.getAttackedPiece().getPieceAlliance().toString().substring(0,1) + move.getAttackedPiece().toString();
            String attackedCharacterFile = ChooseGame.iconMap.get(attackedPiece).substring(defaultPieceImagesPath.length());
            String attackedCharacter = attackedCharacterFile.substring(0, attackedCharacterFile.indexOf('.'));
            if(attackedCharacter.contains("_")){
                attackedCharacter = attackedCharacter.replace('_', ' ');
            }
            String finalAttackedCharacter;
            if(attackedCharacter.contains("-")){
                attackedCharacter = attackedCharacter.replace('-', '(');
                finalAttackedCharacter = attackedCharacter.substring(0, attackedCharacter.indexOf('(')) + " " + attackedCharacter.substring(attackedCharacter.indexOf('(')) + ")";
            }
            else{
                finalAttackedCharacter = attackedCharacter;
            }
            if(finalAttackedCharacter.contains("@")) finalAttackedCharacter = finalAttackedCharacter.replace('@', '-');
            String attackedPlayer = move.getAttackedPiece().getPieceAlliance().toString();

            if(move.isPromotionMove()){
                String promotionPiece = move.getMovedPiece().getPieceAlliance().toString().substring(0,1) + ('Q');
                String promotionCharacterFile = ChooseGame.iconMap.get(promotionPiece).substring(defaultPieceImagesPath.length());
                String promotionCharacter = promotionCharacterFile.substring(0, promotionCharacterFile.indexOf('.'));
                if(promotionCharacter.contains("_")){
                    promotionCharacter = promotionCharacter.replace('_', ' ');
                }
                String finalPromotionCharacter;
                if(promotionCharacter.contains("-")){
                    promotionCharacter = promotionCharacter.replace('-', '(');
                    finalPromotionCharacter = promotionCharacter.substring(0, promotionCharacter.indexOf('(')) + " " + promotionCharacter.substring(promotionCharacter.indexOf('(')) + ")";
                }
                else{
                    finalPromotionCharacter = promotionCharacter;
                }

                if(finalPromotionCharacter.contains("@")) finalPromotionCharacter = finalPromotionCharacter.replace('@', '-');

                MoveTransition transition = Player.testMove(Table.get().getGameBoard(), move);
                if(transition.getMoveStatus() == MoveStatus.LEAVES_PLAYER_IN_CHECK){
                    return currentPlayer + "'S "
                            + move.getMovedPiece().getPieceType().toFullString() + " "
                            + finalMovedCharacter + " has tried to move to "
                            + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + ", but that would leave them in Check!";
                }
                else if(Table.get().getGameBoard().currentPlayer().isInCheck() &&
                        move.getMovedPiece().getPieceType().isKing()){
                    return currentPlayer + "'S "
                            + move.getMovedPiece().getPieceType().toFullString() + " "
                            + finalMovedCharacter + " has tried to move to "
                            + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + ", but "
                            + checkWarning(Table.get().getGameBoard().currentPlayer().getAlliance().toString());
                }
                else if(Table.get().getGameBoard().currentPlayer().isInCheck()){
                    return currentPlayer + "'S "
                            + move.getMovedPiece().getPieceType().toFullString() + " "
                            + finalMovedCharacter + " has defeated "
                            + attackedPlayer + "'S "
                            + move.getAttackedPiece().getPieceType().toFullString() + " "
                            + finalAttackedCharacter + " and summoned "
                            + "QUEEN" + " "
                            + finalPromotionCharacter + " at "
                            + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + "! "
                            + checkWarning(Table.get().getGameBoard().currentPlayer().getAlliance().toString());
                }
                else return currentPlayer + "'S "
                        + move.getMovedPiece().getPieceType().toFullString() + " "
                        + finalMovedCharacter + " has defeated "
                        + attackedPlayer + "'S "
                        + move.getAttackedPiece().getPieceType().toFullString() + " "
                        + finalAttackedCharacter + " and summoned "
                        + "QUEEN" + " "
                        + finalPromotionCharacter + " at "
                        + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + "!";
            }
            else{
                MoveTransition transition = Player.testMove(Table.get().getGameBoard(), move);
                if(transition.getMoveStatus() == MoveStatus.LEAVES_PLAYER_IN_CHECK){
                    return currentPlayer + "'S "
                            + move.getMovedPiece().getPieceType().toFullString() + " "
                            + finalMovedCharacter + " has tried to move to "
                            + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + ", but that would leave them in Check!";
                }
                else if(Table.get().getGameBoard().currentPlayer().isInCheck() &&
                        move.getMovedPiece().getPieceType().isKing()){
                    return currentPlayer + "'S "
                            + move.getMovedPiece().getPieceType().toFullString() + " "
                            + finalMovedCharacter + " has tried to move to "
                            + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + ", but "
                            + checkWarning(Table.get().getGameBoard().currentPlayer().getAlliance().toString());
                }
                else if(Table.get().getGameBoard().currentPlayer().isInCheck()){
                    return currentPlayer + "'S "
                            + move.getMovedPiece().getPieceType().toFullString() + " "
                            + finalMovedCharacter + " has defeated "
                            + attackedPlayer + "'S "
                            + move.getAttackedPiece().getPieceType().toFullString() + " "
                            + finalAttackedCharacter + " at "
                            + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + "! "
                            + checkWarning(Table.get().getGameBoard().currentPlayer().getAlliance().toString());
                }
                else return currentPlayer + "'S "
                        + move.getMovedPiece().getPieceType().toFullString() + " "
                        + finalMovedCharacter + " has defeated "
                        + attackedPlayer + "'S "
                        + move.getAttackedPiece().getPieceType().toFullString() + " "
                        + finalAttackedCharacter + " at "
                        + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + "!";
            }
        }
        else if(move.isRegularMove()){
            MoveTransition transition = Player.testMove(Table.get().getGameBoard(), move);
            if(transition.getMoveStatus() == MoveStatus.LEAVES_PLAYER_IN_CHECK){
                return currentPlayer + "'S "
                        + move.getMovedPiece().getPieceType().toFullString() + " "
                        + finalMovedCharacter + " has tried to move to "
                        + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + ", but that would leave them in Check!";
            }
            else if(Table.get().getGameBoard().currentPlayer().isInCheck() &&
                    move.getMovedPiece().getPieceType().isKing()){
                return currentPlayer + "'S "
                        + move.getMovedPiece().getPieceType().toFullString() + " "
                        + finalMovedCharacter + " has tried to move to "
                        + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + ", but "
                        + checkWarning(Table.get().getGameBoard().currentPlayer().getAlliance().toString());
            }
            else if(Table.get().getGameBoard().currentPlayer().isInCheck()){
                return currentPlayer + "'S "
                        + move.getMovedPiece().getPieceType().toFullString() + " "
                        + finalMovedCharacter + " has moved to "
                        + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + "! "
                        + checkWarning(Table.get().getGameBoard().currentPlayer().getAlliance().toString());
            }
            else return currentPlayer + "'S "
                    + move.getMovedPiece().getPieceType().toFullString() + " "
                    + finalMovedCharacter + " has moved to "
                    + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + "!";
        }
        else if(move.isPromotionMove()){
            String promotionPiece = move.getMovedPiece().getPieceAlliance().toString().substring(0,1) + ('Q');
            String promotionCharacterFile = ChooseGame.iconMap.get(promotionPiece).substring(defaultPieceImagesPath.length());
            String promotionCharacter = promotionCharacterFile.substring(0, promotionCharacterFile.indexOf('.'));
            if(promotionCharacter.contains("_")){
                promotionCharacter = promotionCharacter.replace('_', ' ');
            }
            String finalPromotionCharacter;
            if(promotionCharacter.contains("-")){
                promotionCharacter = promotionCharacter.replace('-', '(');
                finalPromotionCharacter = promotionCharacter.substring(0, promotionCharacter.indexOf('(')) + " " + promotionCharacter.substring(promotionCharacter.indexOf('(')) + ")";
            }
            else{
                finalPromotionCharacter = promotionCharacter;
            }

            if(finalPromotionCharacter.contains("@")) finalPromotionCharacter = finalPromotionCharacter.replace('@', '-');

            MoveTransition transition = Player.testMove(Table.get().getGameBoard(), move);
            if(transition.getMoveStatus() == MoveStatus.LEAVES_PLAYER_IN_CHECK){
                return currentPlayer + "'S "
                        + move.getMovedPiece().getPieceType().toFullString() + " "
                        + finalMovedCharacter + " has tried to move to "
                        + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + ", but that would leave them in Check!";
            }
            else if(Table.get().getGameBoard().currentPlayer().isInCheck() &&
                    move.getMovedPiece().getPieceType().isKing()){
                return currentPlayer + "'S "
                        + move.getMovedPiece().getPieceType().toFullString() + " "
                        + finalMovedCharacter + " has tried to move to "
                        + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + ", but "
                        + checkWarning(Table.get().getGameBoard().currentPlayer().getAlliance().toString());
            }
            else if(Table.get().getGameBoard().currentPlayer().isInCheck()){
                return currentPlayer + "'S "
                        + move.getMovedPiece().getPieceType().toFullString() + " "
                        + finalMovedCharacter + " has summoned "
                        + "QUEEN" + " "
                        + finalPromotionCharacter + " at "
                        + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + "! "
                        + checkWarning(Table.get().getGameBoard().currentPlayer().getAlliance().toString());
            }
            else return currentPlayer + "'S "
                    + move.getMovedPiece().getPieceType().toFullString() + " "
                    + finalMovedCharacter + " has summoned "
                    + "QUEEN" + " "
                    + finalPromotionCharacter + " at "
                    + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + "!";
        }
        else if(move.isCastlingMove()){
            String rookPiece = move.getMovedPiece().getPieceAlliance().toString().substring(0,1) + ('R');
            String rookCharacterFile = ChooseGame.iconMap.get(rookPiece).substring(defaultPieceImagesPath.length());
            String rookCharacter = rookCharacterFile.substring(0, rookCharacterFile.indexOf('.'));
            if(rookCharacter.contains("_")){
                rookCharacter = rookCharacter.replace('_', ' ');
            }
            String finalRookCharacter;
            if(rookCharacter.contains("-")){
                rookCharacter = rookCharacter.replace('-', '(');
                finalRookCharacter = rookCharacter.substring(0, rookCharacter.indexOf('(')) + " " + rookCharacter.substring(rookCharacter.indexOf('(')) + ")";
            }
            else{
                finalRookCharacter = rookCharacter;
            }

            if(finalRookCharacter.contains("@")) finalRookCharacter = finalRookCharacter.replace('@', '-');

            MoveTransition transition = Player.testMove(Table.get().getGameBoard(), move);
            if(transition.getMoveStatus() == MoveStatus.LEAVES_PLAYER_IN_CHECK){
                return currentPlayer + "'S "
                        + move.getMovedPiece().getPieceType().toFullString() + " "
                        + finalMovedCharacter + " has tried to move to "
                        + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + ", but that would leave them in Check!";
            }
            else if(Table.get().getGameBoard().currentPlayer().isInCheck() &&
                    move.getMovedPiece().getPieceType().isKing()){
                return currentPlayer + "'S "
                        + move.getMovedPiece().getPieceType().toFullString() + " "
                        + finalMovedCharacter + " has tried to move to "
                        + move.toString().substring(Math.max(move.toString().length() - 2, 0)) + ", but "
                        + checkWarning(Table.get().getGameBoard().currentPlayer().getAlliance().toString());
            }
            else if(Table.get().getGameBoard().currentPlayer().isInCheck()){
                return currentPlayer + "'S "
                        + move.getMovedPiece().getPieceType().toFullString() + " "
                        + finalMovedCharacter + " has been castled by "
                        + "ROOK" + " "
                        + finalRookCharacter + "! "
                        + checkWarning(Table.get().getGameBoard().currentPlayer().getAlliance().toString());
            }
            else return currentPlayer + "'S "
                    + move.getMovedPiece().getPieceType().toFullString() + " "
                    + finalMovedCharacter + " has been castled by "
                    + "ROOK" + " "
                    + finalRookCharacter + "!";
        }
        else{
            return "ERROR: Should not reach here.";
        }
    }
}

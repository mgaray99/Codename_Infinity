package com.chess.gui8x8;

import com.chess.engine8x8.board.Move;
import com.chess.engine8x8.pieces.Piece;
import com.google.common.primitives.Ints;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static com.chess.gui8x8.Table.*;

public class TakenPiecesPanel extends JPanel{

    //Used from InfinityChess class

    private final JPanel northPanel;
    private final JPanel southPanel;

    private static final Color PANEL_COLOR = Color.decode("#299FC6");
    //private static final Color PANEL_COLOR = Color.decode("#004A9E");
    private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(150, 600);
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    public TakenPiecesPanel(String gameMode){
        super(new BorderLayout());
        this.setBackground(PANEL_COLOR);
        this.setBorder(PANEL_BORDER);

        // allowing for 32 pawns to be taken in Horde mode
        if (gameMode == "Horde"){
            this.northPanel = new JPanel(new GridLayout(8, 4));
        } else {
            this.northPanel = new JPanel(new GridLayout(8, 2));
        }

        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        this.add(this.northPanel, BorderLayout.NORTH);
        this.add(this.southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_DIMENSION);
    }

    public void redo(final MoveLog moveLog){

        this.southPanel.removeAll();
        this.northPanel.removeAll();

        final List<Piece> whiteTakenPieces = new ArrayList<>();
        final List<Piece> blackTakenPieces = new ArrayList<>();

        for(final Move move : moveLog.getMoves()){
            if(move.isAttack()){
                final Piece takenPiece = move.getAttackedPiece();
                if(takenPiece.getPieceAlliance().isWhite()){
                    whiteTakenPieces.add(takenPiece);
                } else if(takenPiece.getPieceAlliance().isBlack()){
                    blackTakenPieces.add(takenPiece);
                } else{
                    throw new RuntimeException("Should not reach here.");
                }
            }
        }



        Collections.sort(whiteTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });

        Collections.sort(blackTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Ints.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });

        for(final Piece takenPiece : whiteTakenPieces){
            String alliance = takenPiece.getPieceAlliance().toString().substring(0, 1);
            String piece = takenPiece.toString();
            //System.out.println(alliance + piece);
            //System.out.println(iconMap.get(alliance + piece));
            try{
                final BufferedImage image = ImageIO.read(new File(ChooseGame.iconMap.get(alliance + piece)));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
                this.southPanel.add(imageLabel);
            } catch(final IOException e){
                e.printStackTrace();
            }
        }
        for(final Piece takenPiece : blackTakenPieces){
            String alliance = takenPiece.getPieceAlliance().toString().substring(0, 1);
            String piece = takenPiece.toString();

            //System.out.println(alliance + piece);
            //System.out.println(iconMap.get(alliance + piece));
            //System.out.println(iconMap.toString());
            try{
                final BufferedImage image = ImageIO.read(new File(ChooseGame.iconMap.get(alliance + piece)));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
                this.northPanel.add(imageLabel);
            } catch(final IOException e){
                e.printStackTrace();
            }
        }
        validate();
    }
}

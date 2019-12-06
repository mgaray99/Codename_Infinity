package com.chess.gui10x8;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Arrays;
import java.util.Set;

import javax.swing.*;

public class ChangeCharacters extends JFrame{

    private static File projectDirectory = new File(".");
    private static String projectDirectoryPath = projectDirectory.getAbsolutePath();
    private static String defaultPieceImagesPath = projectDirectoryPath.substring(0, projectDirectoryPath.length()-1) + "/src/Piece_Images/Characters/";

    public ChangeCharacters(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        setTitle("Change Characters");
        getContentPane().setLayout(null);

        final JPopupMenu popMenu = new JPopupMenu();

        ActionListener menuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String piece = e.getActionCommand();
                changeMainCharacter(convertPieceToKey(piece));
            }
        };


        JMenuItem item;
        //JButton button;
        Set<String> keySet = ChooseGame.iconMap.keySet();
        //System.out.println(keySet);
        String[] keyArray = new String[keySet.size()];
        keySet.toArray(keyArray);
        Arrays.sort(keyArray);

        for(String key : keyArray){
            String piece = convertKeyToPiece(key);
            item = new JMenuItem(piece);
            item.addActionListener(menuListener);
            popMenu.add(item);
        }

        JButton btnOpenMenu = new JButton("Choose Piece");
        btnOpenMenu.setBounds(122, 71, 250, 25);
        btnOpenMenu.setAlignmentX(RIGHT_ALIGNMENT);
        btnOpenMenu.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                popMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        getContentPane().add(btnOpenMenu);

    }

    public String convertKeyToPiece(String key){
        String piece;
        switch(key) {
            case "WK":
                piece = "White King";
                break;
            case "WQ":
                piece = "White Queen";
                break;
            case "WB":
                piece = "White Bishop";
                break;
            case "WN":
                piece = "White Knight";
                break;
            case "WR":
                piece = "White Rook";
                break;
            case "WP":
                piece = "White Pawn";
                break;
            case "WQN":
                piece = "White Amazon";
                break;
            case "WRN":
                piece = "White Chancellor";
                break;
            case "WBN":
                piece = "White Archbishop";
                break;
            case "WMN":
                piece = "White Centaur";
                break;
            case "WM":
                piece = "White Mann";
                break;
            case "BK":
                piece = "Black King";
                break;
            case "BQ":
                piece = "Black Queen";
                break;
            case "BB":
                piece = "Black Bishop";
                break;
            case "BN":
                piece = "Black Knight";
                break;
            case "BR":
                piece = "Black Rook";
                break;
            case "BP":
                piece = "Black Pawn";
                break;
            case "BQN":
                piece = "Black Amazon";
                break;
            case "BRN":
                piece = "Black Chancellor";
                break;
            case "BBN":
                piece = "Black Archbishop";
                break;
            case "BMN":
                piece = "Black Centaur";
                break;
            case "BM":
                piece = "Black Mann";
                break;
            default:
                return "";
        }
        return piece;
    }

    public String convertPieceToKey(String piece){
        String key;
        switch(piece) {
            case "White King":
                key = "WK";
                break;
            case "White Queen":
                key = "WQ";
                break;
            case "White Bishop":
                key = "WB";
                break;
            case "White Knight":
                key = "WN";
                break;
            case "White Rook":
                key = "WR";
                break;
            case "White Pawn":
                key = "WP";
                break;
            case "White Amazon":
                key = "WQN";
                break;
            case "White Chancellor":
                key = "WRN";
                break;
            case "White Archbishop":
                key = "WBN";
                break;
            case "White Centaur":
                key = "WMN";
                break;
            case "White Mann":
                key = "WM";
                break;
            case "Black King":
                key = "BK";
                break;
            case "Black Queen":
                key = "BQ";
                break;
            case "Black Bishop":
                key = "BB";
                break;
            case "Black Knight":
                key = "BN";
                break;
            case "Black Rook":
                key = "BR";
                break;
            case "Black Pawn":
                key = "BP";
                break;
            case "Black Amazon":
                key = "BQN";
                break;
            case "Black Chancellor":
                key = "BRN";
                break;
            case "Black Archbishop":
                key = "BBN";
                break;
            case "Black Centaur":
                key = "BMN";
                break;
            case "Black Mann":
                key = "BM";
                break;
            default:
                return "";
        }
        return key;
    }

    public String chooseFile(String path, String dialogTitle) {
        JButton open = new JButton();
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(path));
        fc.setDialogTitle(dialogTitle);
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION){
            File f = fc.getSelectedFile();
            String marvelCharacter = f.getName();
            return marvelCharacter;
        }
        return null;
    }

    public void changeMainCharacter(String key) {
        //String firstMarvelCharacter = chooseFile(pieceImagesPath + resize, "Choose the original character");
        //System.out.println(firstMarvelCharacter);
        String firstMarvelCharacter = ChooseGame.iconMap.get(key).substring(defaultPieceImagesPath.length());
        String secondMarvelCharacter = chooseFile(defaultPieceImagesPath, "Choose a new character");
        if(ChooseGame.iconMap.containsValue(defaultPieceImagesPath + secondMarvelCharacter)){
            secondMarvelCharacter = firstMarvelCharacter;
            //System.out.println(secondMarvelCharacter);
            //System.out.println("Found duplicate");
            JFrame error = new JFrame();
            error.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            error.setBounds(100, 100, 600, 500);
            error.setTitle("Cannot Have A Duplicate Character");
            error.getContentPane().setLayout(null);

            JButton btnOpenMenu = new JButton("Pick Again");
            btnOpenMenu.setBounds(122, 71, 250, 25);
            btnOpenMenu.setAlignmentX(RIGHT_ALIGNMENT);
            btnOpenMenu.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    error.dispose();
                    changeMainCharacter(key);
                }
            });
            error.getContentPane().add(btnOpenMenu);
            error.setVisible(true);
        }
        //System.out.println(secondMarvelCharacter);
        //if (firstMarvelCharacter.equals(secondMarvelCharacter)) return;
        //System.out.println(ChooseGame.iconMap.toString());
        //System.out.println((pieceImagesPath + resize + firstMarvelCharacter));
        if(secondMarvelCharacter != null){
            ChooseGame.iconMap.put(key, defaultPieceImagesPath + secondMarvelCharacter);
        }
        //System.out.println(ChooseGame.iconMap.get(key));
        //for (Map.Entry<String,String> entry : ChooseGame.iconMap.entrySet()){
        //System.out.println(entry.getValue());
        //if (entry.getValue().equals(pieceImagesPath + resize + firstMarvelCharacter)){
        //System.out.println("recognized");
        //entry.setValue(pieceImagesPath + resize + secondMarvelCharacter);
        //}
        //}
        //System.out.println(ChooseGame.iconMap.toString());
    }

}

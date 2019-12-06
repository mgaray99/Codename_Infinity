package com.chess.gui9x9;

import com.chess.engine9x9.board.Board;
import com.chess.engine9x9.pieces.Piece;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;

import javax.swing.*;

public class ChooseGame extends JFrame{

    public static Table table;
    public static Map<String, String> iconMap;

    private static File projectDirectory = new File(".");
    private static String projectDirectoryPath = projectDirectory.getAbsolutePath();
    private static String defaultPieceImagesPath = projectDirectoryPath.substring(0, projectDirectoryPath.length()-1) + "/src/Piece_Images/Characters/";


    public ChooseGame(){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        setTitle("Avengers: Infinity Chess by Mike Garay");
        getContentPane().setLayout(null);

        final JPopupMenu yorkPopMenu = new JPopupMenu();
        final JPopupMenu xandarPopMenu = new JPopupMenu();
        final JPopupMenu sokoviaPopMenu = new JPopupMenu();
        final JPopupMenu clashPopMenu = new JPopupMenu();
        final JPopupMenu wakandaPopMenu = new JPopupMenu();
        final JPopupMenu titanPopMenu = new JPopupMenu();
        final JPopupMenu earthPopMenu = new JPopupMenu();

        ActionListener menuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String gameMode = e.getActionCommand();
                Board gameBoard;
                if(gameMode == "Battle of Wakanda: Modern"){
                    gameBoard = Board.createModernBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("BN", defaultPieceImagesPath + "Black_Dwarf.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Bruce_Banner-Hulkbuster.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Corvus_Glaive.png");
                    iconMap.put("WBN", defaultPieceImagesPath + "Dr_Strange-Modern.png");
                    iconMap.put("BBN", defaultPieceImagesPath + "Thanos-Reality_Stone.png");
                    table = new Table(gameBoard, gameMode);
                    //Table.setTable();
                    //Table.get().show();
                    //Table table = new Table(gameBoard, gameMode);
                    dispose();
                }else if(gameMode == "Battle of Wakanda: Chancellors"){
                    gameBoard = Board.createChancellorsBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("BN", defaultPieceImagesPath + "Black_Dwarf.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Bruce_Banner-Hulkbuster.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Corvus_Glaive.png");
                    iconMap.put("WRN", defaultPieceImagesPath + "Black_Panther-Civil_War.png");
                    iconMap.put("BRN", defaultPieceImagesPath + "Thanos-Soul_Stone.png");
                    table = new Table(gameBoard, gameMode);
                    //Table.setTable();
                    //Table.get().show();
                    //Table table = new Table(gameBoard, gameMode);
                    dispose();
                }else if(gameMode == "Battle of Wakanda: Ministers"){
                    gameBoard = Board.createMinistersBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("BN", defaultPieceImagesPath + "Black_Dwarf.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Bruce_Banner-Hulkbuster.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Corvus_Glaive.png");
                    table = new Table(gameBoard, gameMode);
                    //Table.setTable();
                    //Table.get().show();
                    //Table table = new Table(gameBoard, gameMode);
                    dispose();
                }else if(gameMode == "Battle of Titan: Modern"){
                    gameBoard = Board.createModernBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("WK", defaultPieceImagesPath + "Star@Lord-Guardians_of_the_Galaxy.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Iron_Man-Infinity_War.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Dr_Strange-Modern.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Spider@Man-Infinity_War.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Drax_the_Destroyer-Guardians_of_the_Galaxy.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Nebula.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Thanos-Power_Stone.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Thanos-Reality_Stone.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Thanos-Space_Stone.png");
                    iconMap.put("BR", defaultPieceImagesPath + "Thanos-Soul_Stone.png");
                    table = new Table(gameBoard, gameMode);
                    //Table.setTable();
                    //Table.get().show();
                    //Table table = new Table(gameBoard, gameMode);
                    dispose();
                }else if(gameMode == "Battle of Titan: Chancellors"){
                    gameBoard = Board.createChancellorsBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("WK", defaultPieceImagesPath + "Star@Lord-Guardians_of_the_Galaxy.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Iron_Man-Infinity_War.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Dr_Strange-Modern.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Spider@Man-Infinity_War.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Drax_the_Destroyer-Guardians_of_the_Galaxy.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Nebula.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Thanos-Power_Stone.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Thanos-Reality_Stone.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Thanos-Space_Stone.png");
                    iconMap.put("BR", defaultPieceImagesPath + "Thanos-Soul_Stone.png");
                    table = new Table(gameBoard, gameMode);
                    //Table.setTable();
                    //Table.get().show();
                    //Table table = new Table(gameBoard, gameMode);
                    dispose();
                }else if(gameMode == "Battle of Titan: Ministers"){
                    gameBoard = Board.createMinistersBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("WK", defaultPieceImagesPath + "Star@Lord-Guardians_of_the_Galaxy.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Iron_Man-Infinity_War.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Dr_Strange-Modern.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Spider@Man-Infinity_War.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Drax_the_Destroyer-Guardians_of_the_Galaxy.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Nebula.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Thanos-Power_Stone.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Thanos-Reality_Stone.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Thanos-Space_Stone.png");
                    iconMap.put("BR", defaultPieceImagesPath + "Thanos-Soul_Stone.png");
                    table = new Table(gameBoard, gameMode);
                    //Table.setTable();
                    //Table.get().show();
                    //Table table = new Table(gameBoard, gameMode);
                    dispose();
                }else if(gameMode == "Battle of Earth: Modern"){
                    gameBoard = Board.createModernBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("WK", defaultPieceImagesPath + "Iron_Man-Mk_V_Armor.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Ms_Marvel-Captain_Marvel.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Captain_America-Mighty.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Ebony_Maw.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Thanos-Power_Stone.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Hawkeye-Avengers_Age_of_Ultron.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Thor-Infinity_War.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Black_Dwarf.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Scarlet_Witch-Civil_War.png");

                    iconMap.put("WBN", defaultPieceImagesPath + "Dr_Strange-Modern.png");
                    iconMap.put("BBN", defaultPieceImagesPath + "Supergiant.png");
                    table = new Table(gameBoard, gameMode);
                    //Table.setTable();
                    //Table.get().show();
                    //Table table = new Table(gameBoard, gameMode);
                    dispose();
                }else if(gameMode == "Battle of Earth: Chancellors"){
                    gameBoard = Board.createChancellorsBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("WK", defaultPieceImagesPath + "Iron_Man-Mk_V_Armor.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Ms_Marvel-Captain_Marvel.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Captain_America-Mighty.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Ebony_Maw.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Thanos-Power_Stone.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Hawkeye-Avengers_Age_of_Ultron.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Thor-Infinity_War.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Black_Dwarf.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Scarlet_Witch-Civil_War.png");

                    iconMap.put("WBN", defaultPieceImagesPath + "Dr_Strange-Modern.png");
                    iconMap.put("BBN", defaultPieceImagesPath + "Supergiant.png");
                    table = new Table(gameBoard, gameMode);
                    //Table.setTable();
                    //Table.get().show();
                    //Table table = new Table(gameBoard, gameMode);
                    dispose();
                }else if(gameMode == "Battle of Earth: Ministers"){
                    gameBoard = Board.createMinistersBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("WK", defaultPieceImagesPath + "Iron_Man-Mk_V_Armor.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Ms_Marvel-Captain_Marvel.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Captain_America-Mighty.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Ebony_Maw.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Thanos-Power_Stone.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Hawkeye-Avengers_Age_of_Ultron.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Thor-Infinity_War.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Black_Dwarf.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Scarlet_Witch-Civil_War.png");
                    table = new Table(gameBoard, gameMode);
                    //Table.setTable();
                    //Table.get().show();
                    //Table table = new Table(gameBoard, gameMode);
                    dispose();
                }
                else if (gameMode == "Clash of the Avengers: Modern"){
                    gameBoard = Board.createModernBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("WK", defaultPieceImagesPath + "Captain_America-Avengers_Age_of_Ultron.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Scarlet_Witch-Civil_War.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Falcon-Infinity_War.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Winter_Soldier-Modern.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Ant@Man-Modern.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Hawkeye-Avengers_Age_of_Ultron.png");

                    iconMap.put("BK", defaultPieceImagesPath + "Iron_Man-Avengers_Age_of_Ultron.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Vision-Avengers_Age_of_Ultron.png");
                    iconMap.put("BB", defaultPieceImagesPath + "War_Machine-Civil_War.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Black_Panther-Civil_War.png");
                    iconMap.put("BR", defaultPieceImagesPath + "Spider@Man-Amazing.png");
                    iconMap.put("BP", defaultPieceImagesPath + "Black_Widow-Avengers_Age_of_Ultron.png");
                    table = new Table(gameBoard, gameMode);
                    dispose();
                }else if (gameMode == "Clash of the Avengers: Chancellors"){
                    gameBoard = Board.createChancellorsBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("WK", defaultPieceImagesPath + "Captain_America-Avengers_Age_of_Ultron.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Scarlet_Witch-Civil_War.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Falcon-Infinity_War.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Winter_Soldier-Modern.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Ant@Man-Modern.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Hawkeye-Avengers_Age_of_Ultron.png");

                    iconMap.put("BK", defaultPieceImagesPath + "Iron_Man-Avengers_Age_of_Ultron.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Vision-Avengers_Age_of_Ultron.png");
                    iconMap.put("BB", defaultPieceImagesPath + "War_Machine-Civil_War.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Black_Panther-Civil_War.png");
                    iconMap.put("BR", defaultPieceImagesPath + "Spider@Man-Amazing.png");
                    iconMap.put("BP", defaultPieceImagesPath + "Black_Widow-Avengers_Age_of_Ultron.png");
                    table = new Table(gameBoard, gameMode);
                    dispose();
                }
                else if (gameMode == "Clash of the Avengers: Ministers"){
                    gameBoard = Board.createMinistersBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("WK", defaultPieceImagesPath + "Captain_America-Avengers_Age_of_Ultron.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Scarlet_Witch-Civil_War.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Falcon-Infinity_War.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Winter_Soldier-Modern.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Ant@Man-Modern.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Hawkeye-Avengers_Age_of_Ultron.png");

                    iconMap.put("BK", defaultPieceImagesPath + "Iron_Man-Avengers_Age_of_Ultron.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Vision-Avengers_Age_of_Ultron.png");
                    iconMap.put("BB", defaultPieceImagesPath + "War_Machine-Civil_War.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Black_Panther-Civil_War.png");
                    iconMap.put("BR", defaultPieceImagesPath + "Spider@Man-Amazing.png");
                    iconMap.put("BP", defaultPieceImagesPath + "Black_Widow-Avengers_Age_of_Ultron.png");
                    table = new Table(gameBoard, gameMode);
                    dispose();
                }
                else if (gameMode == "Battle of Sokovia: Modern"){
                    gameBoard = Board.createModernBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    //Avengers: done
                    iconMap.put("WK", defaultPieceImagesPath + "Captain_America-Avengers_Age_of_Ultron.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Vision-Avengers_Age_of_Ultron.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Scarlet_Witch-Avengers_Age_of_Ultron.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Iron_Man-Avengers_Age_of_Ultron.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Quicksilver-Avengers_Age_of_Ultron.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Hawkeye-Avengers_Age_of_Ultron.png");

                    //TODO: Ultron pieces
                    iconMap.put("BK", defaultPieceImagesPath + "Ultron-Avengers_Age_of_Ultron.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Ultron-Classic.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Malekith.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Kurse.png");
                    iconMap.put("BR", defaultPieceImagesPath + "Mandarin.png");
                    iconMap.put("BP", defaultPieceImagesPath + "Ultron_Sentry.png");
                    table = new Table(gameBoard, gameMode);
                    dispose();
                }else if (gameMode == "Battle of Sokovia: Chancellors"){
                    gameBoard = Board.createChancellorsBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    //Avengers: done
                    iconMap.put("WK", defaultPieceImagesPath + "Captain_America-Avengers_Age_of_Ultron.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Vision-Avengers_Age_of_Ultron.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Scarlet_Witch-Avengers_Age_of_Ultron.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Iron_Man-Avengers_Age_of_Ultron.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Quicksilver-Avengers_Age_of_Ultron.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Hawkeye-Avengers_Age_of_Ultron.png");

                    //TODO: Ultron pieces
                    iconMap.put("BK", defaultPieceImagesPath + "Ultron-Avengers_Age_of_Ultron.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Ultron-Classic.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Malekith.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Kurse.png");
                    iconMap.put("BR", defaultPieceImagesPath + "Mandarin.png");
                    iconMap.put("BP", defaultPieceImagesPath + "Ultron_Sentry.png");
                    table = new Table(gameBoard, gameMode);
                    dispose();
                }
                else if (gameMode == "Battle of Sokovia: Ministers"){
                    gameBoard = Board.createMinistersBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    //Avengers: done
                    iconMap.put("WK", defaultPieceImagesPath + "Captain_America-Avengers_Age_of_Ultron.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Vision-Avengers_Age_of_Ultron.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Scarlet_Witch-Avengers_Age_of_Ultron.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Iron_Man-Avengers_Age_of_Ultron.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Quicksilver-Avengers_Age_of_Ultron.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Hawkeye-Avengers_Age_of_Ultron.png");

                    //TODO: Ultron pieces
                    iconMap.put("BK", defaultPieceImagesPath + "Ultron-Avengers_Age_of_Ultron.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Ultron-Classic.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Malekith.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Kurse.png");
                    iconMap.put("BR", defaultPieceImagesPath + "Mandarin.png");
                    iconMap.put("BP", defaultPieceImagesPath + "Ultron_Sentry.png");
                    table = new Table(gameBoard, gameMode);
                    dispose();
                }
                else if (gameMode == "Battle of Xandar: Modern"){
                    gameBoard = Board.createModernBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("WK", defaultPieceImagesPath + "Star@Lord-Guardians_of_the_Galaxy.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Drax_the_Destroyer-Guardians_of_the_Galaxy.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Rocket_Raccoon-Guardians_of_the_Galaxy.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Gamora-Guardians_of_the_Galaxy.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Groot-Guardians_of_the_Galaxy.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Yondu-Guardians_of_the_Galaxy.png");

                    iconMap.put("BK", defaultPieceImagesPath + "Ronan.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Cosmic_Ronan.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Ronan-Original.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Korath-Classic.png");
                    iconMap.put("BR", defaultPieceImagesPath + "Nebula.png");
                    table = new Table(gameBoard, gameMode);
                    dispose();
                }
                else if (gameMode == "Battle of Xandar: Chancellors"){
                    gameBoard = Board.createChancellorsBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("WK", defaultPieceImagesPath + "Star@Lord-Guardians_of_the_Galaxy.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Drax_the_Destroyer-Guardians_of_the_Galaxy.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Rocket_Raccoon-Guardians_of_the_Galaxy.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Gamora-Guardians_of_the_Galaxy.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Groot-Guardians_of_the_Galaxy.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Yondu-Guardians_of_the_Galaxy.png");

                    iconMap.put("BK", defaultPieceImagesPath + "Ronan.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Cosmic_Ronan.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Ronan-Original.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Korath-Classic.png");
                    iconMap.put("BR", defaultPieceImagesPath + "Nebula.png");
                    table = new Table(gameBoard, gameMode);
                    dispose();
                }
                else if (gameMode == "Battle of Xandar: Ministers"){
                    gameBoard = Board.createMinistersBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("WK", defaultPieceImagesPath + "Star@Lord-Guardians_of_the_Galaxy.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Drax_the_Destroyer-Guardians_of_the_Galaxy.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Rocket_Raccoon-Guardians_of_the_Galaxy.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Gamora-Guardians_of_the_Galaxy.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Groot-Guardians_of_the_Galaxy.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Yondu-Guardians_of_the_Galaxy.png");

                    iconMap.put("BK", defaultPieceImagesPath + "Ronan.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Cosmic_Ronan.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Ronan-Original.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Korath-Classic.png");
                    iconMap.put("BR", defaultPieceImagesPath + "Nebula.png");
                    table = new Table(gameBoard, gameMode);
                    dispose();
                }
                else if (gameMode == "Battle of New York: Modern"){
                    gameBoard = Board.createModernBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("WK", defaultPieceImagesPath + "Captain_America-Avengers.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Thor-Avengers.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Hawkeye-Avengers.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Iron_Man-Avengers.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Hulk-Avengers.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Black_Widow-Avengers.png");

                    iconMap.put("BK", defaultPieceImagesPath + "Loki-Modern.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Loki.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Red_Skull.png");
                    iconMap.put("BR", defaultPieceImagesPath + "Iron_Monger.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Abomination.png");
                    table = new Table(gameBoard, gameMode);
                    dispose();
                }else if (gameMode == "Battle of New York: Chancellors"){
                    gameBoard = Board.createChancellorsBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("WK", defaultPieceImagesPath + "Captain_America-Avengers.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Thor-Avengers.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Hawkeye-Avengers.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Iron_Man-Avengers.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Hulk-Avengers.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Black_Widow-Avengers.png");

                    iconMap.put("BK", defaultPieceImagesPath + "Loki-Modern.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Loki.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Red_Skull.png");
                    iconMap.put("BR", defaultPieceImagesPath + "Iron_Monger.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Abomination.png");
                    table = new Table(gameBoard, gameMode);
                    dispose();
                }else if (gameMode == "Battle of New York: Ministers"){
                    gameBoard = Board.createMinistersBoard();
                    if(iconMap != null) iconMap.clear();
                    iconMap = makeIconMap(gameBoard);
                    iconMap.put("WK", defaultPieceImagesPath + "Captain_America-Avengers.png");
                    iconMap.put("WQ", defaultPieceImagesPath + "Thor-Avengers.png");
                    iconMap.put("WB", defaultPieceImagesPath + "Hawkeye-Avengers.png");
                    iconMap.put("WR", defaultPieceImagesPath + "Iron_Man-Avengers.png");
                    iconMap.put("WN", defaultPieceImagesPath + "Hulk-Avengers.png");
                    iconMap.put("WP", defaultPieceImagesPath + "Black_Widow-Avengers.png");

                    iconMap.put("BK", defaultPieceImagesPath + "Loki-Modern.png");
                    iconMap.put("BQ", defaultPieceImagesPath + "Loki.png");
                    iconMap.put("BB", defaultPieceImagesPath + "Red_Skull.png");
                    iconMap.put("BR", defaultPieceImagesPath + "Iron_Monger.png");
                    iconMap.put("BN", defaultPieceImagesPath + "Abomination.png");
                    table = new Table(gameBoard, gameMode);
                    dispose();
                }
                else{
                    throw new RuntimeException("Should not reach here! Gamemode error?");
                }



            }
        };

        JMenuItem item;
        //TODO: Add Battle of Titan: Decimation, Battle of Titan: 14,000,605, Battle of Titan: Battle of Earth:
        String[] gameModes = {
                "Battle of New York: Modern",
                "Battle of New York: Chancellors",
                "Battle of New York: Ministers",
                "Battle of Xandar: Modern",
                "Battle of Xandar: Chancellors",
                "Battle of Xandar: Ministers",
                "Battle of Sokovia: Modern",
                "Battle of Sokovia: Chancellors",
                "Battle of Sokovia: Ministers",
                "Clash of the Avengers: Modern",
                "Clash of the Avengers: Chancellors",
                "Clash of the Avengers: Ministers",
                "Battle of Wakanda: Modern",
                "Battle of Wakanda: Chancellors",
                "Battle of Wakanda: Ministers",
                "Battle of Titan: Modern",
                "Battle of Titan: Chancellors",
                "Battle of Titan: Ministers",
                "Battle of Earth: Modern",
                "Battle of Earth: Chancellors",
                "Battle of Earth: Ministers"
                };

        for(String gameMode : gameModes){
            if(gameMode.contains("Battle of New York")){
                item = new JMenuItem(gameMode);
                item.addActionListener(menuListener);
                yorkPopMenu.add(item);
            }
            else if(gameMode.contains("Battle of Xandar")){
                item = new JMenuItem(gameMode);
                item.addActionListener(menuListener);
                xandarPopMenu.add(item);
            }
            else if(gameMode.contains("Battle of Sokovia")){
                item = new JMenuItem(gameMode);
                item.addActionListener(menuListener);
                sokoviaPopMenu.add(item);
            }
            else if(gameMode.contains("Clash of the Avengers")){
                item = new JMenuItem(gameMode);
                item.addActionListener(menuListener);
                clashPopMenu.add(item);
            }
            else if(gameMode.contains("Battle of Wakanda") || gameMode.contains("Battle of Titan")){
                item = new JMenuItem(gameMode);
                item.addActionListener(menuListener);
                wakandaPopMenu.add(item);
            }
            else if(gameMode.contains("Battle of Earth")){
                item = new JMenuItem(gameMode);
                item.addActionListener(menuListener);
                earthPopMenu.add(item);
            }
        }
        JButton yorkOpenMenu = new JButton("The Avengers");
        yorkOpenMenu.setBounds(122, 71, 250, 25);
        yorkOpenMenu.setAlignmentX(RIGHT_ALIGNMENT);
        yorkOpenMenu.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                yorkPopMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        getContentPane().add(yorkOpenMenu);

        JButton xandarOpenMenu = new JButton("Guardians of the Galaxy");
        xandarOpenMenu.setBounds(122, 121, 250, 25);
        xandarOpenMenu.setAlignmentX(RIGHT_ALIGNMENT);
        xandarOpenMenu.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                xandarPopMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        getContentPane().add(xandarOpenMenu);

        JButton sokoviaOpenMenu = new JButton("Avengers: Age of Ultron");
        sokoviaOpenMenu.setBounds(122, 171, 250, 25);
        sokoviaOpenMenu.setAlignmentX(RIGHT_ALIGNMENT);
        sokoviaOpenMenu.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                sokoviaPopMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        getContentPane().add(sokoviaOpenMenu);

        JButton clashOpenMenu = new JButton("Captain America: Civil War");
        clashOpenMenu.setBounds(122, 221, 250, 25);
        clashOpenMenu.setAlignmentX(RIGHT_ALIGNMENT);
        clashOpenMenu.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                clashPopMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        getContentPane().add(clashOpenMenu);

        JButton wakandaOpenMenu = new JButton("Avengers: Infinity War");
        wakandaOpenMenu.setBounds(122, 271, 250, 25);
        wakandaOpenMenu.setAlignmentX(RIGHT_ALIGNMENT);
        wakandaOpenMenu.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                wakandaPopMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        getContentPane().add(wakandaOpenMenu);

        JButton earthOpenMenu = new JButton("Avengers: Endgame");
        earthOpenMenu.setBounds(122, 321, 250, 25);
        earthOpenMenu.setAlignmentX(RIGHT_ALIGNMENT);
        earthOpenMenu.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                earthPopMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        getContentPane().add(earthOpenMenu);

    }
    public static Map<String, String> makeIconMap(Board gameBoard){
        iconMap = new HashMap<String, String>();
        for(Piece piece : gameBoard.getWhitePieces()){
            String pieceName = "W" + piece.toString();
            String marvelCharacter;
            switch(pieceName){
                case "WK": marvelCharacter = defaultPieceImagesPath + "Vision-Avengers_Age_of_Ultron.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "WQ": marvelCharacter = defaultPieceImagesPath + "Thor-Infinity_War.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "WB": marvelCharacter = defaultPieceImagesPath + "Scarlet_Witch-Civil_War.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "WN": marvelCharacter = defaultPieceImagesPath + "Iron_Man-Infinity_War.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "WR": marvelCharacter = defaultPieceImagesPath + "Captain_America-Infinity_War.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "WP": marvelCharacter = defaultPieceImagesPath + "Black_Widow-Infinity_War.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                //case "WA": marvelCharacter = promotionPieceImagesPath + "Binary_Captain_Marvel.png";
                //    iconMap.put(pieceName, marvelCharacter);
                //    break;

                case "WQN": marvelCharacter = defaultPieceImagesPath + "Ms_Marvel-Captain_Marvel.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "WRN": marvelCharacter = defaultPieceImagesPath + "Captain_America-Mighty.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "WBN": marvelCharacter = defaultPieceImagesPath + "Dr_Strange-Modern.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "WMN": marvelCharacter = defaultPieceImagesPath + "Hulk-Avengers_Age_of_Ultron.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "WM": marvelCharacter = defaultPieceImagesPath + "Ronin-Classic.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;

                default:   marvelCharacter = "Invalid";
                    iconMap.put("", marvelCharacter);
                    System.out.println("White characters weren't mapped successfully:");
            }
        }
        for(Piece piece: gameBoard.getBlackPieces()){
            String pieceName = "B" + piece.toString();
            String marvelCharacter;
            switch(pieceName){
                case "BK": marvelCharacter = defaultPieceImagesPath + "Thanos.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "BQ": marvelCharacter = defaultPieceImagesPath + "Thanos-Power_Stone.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "BB": marvelCharacter = defaultPieceImagesPath + "Ebony_Maw.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "BN": marvelCharacter = defaultPieceImagesPath + "Ronan.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "BR": marvelCharacter = defaultPieceImagesPath + "Proxima_Midnight.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "BP": marvelCharacter = defaultPieceImagesPath + "Savage_Soldier.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                //case "BA": marvelCharacter = promotionPieceImagesPath + "Infinite_Warrior_Thanos.png";
                //    iconMap.put(pieceName, marvelCharacter);
                //    break;

                case "BQN": marvelCharacter = defaultPieceImagesPath + "Thanos-Infinity_Gauntlet.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "BRN": marvelCharacter = defaultPieceImagesPath + "Corvus_Glaive.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "BBN": marvelCharacter = defaultPieceImagesPath + "Supergiant.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "BMN": marvelCharacter = defaultPieceImagesPath + "Black_Dwarf.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;
                case "BM": marvelCharacter = defaultPieceImagesPath + "Nebula.png";
                    iconMap.put(pieceName, marvelCharacter);
                    break;

                default:   marvelCharacter = "Invalid";
                    iconMap.put("", marvelCharacter);
                    System.out.println("Black characters weren't mapped successfully:");
            }
        }
        return iconMap;
    }

}

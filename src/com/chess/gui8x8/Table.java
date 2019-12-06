package com.chess.gui8x8;

import com.chess.engine8x8.board.*;
import com.chess.engine8x8.pieces.Piece;
import com.chess.engine8x8.player.MoveTransition;
import com.chess.engine8x8.player.Player;
import com.chess.engine8x8.player.ai.MiniMax;
import com.chess.engine8x8.player.ai.MoveStrategy;
import com.chess.engine8x8.player.ai.StockAlphaBeta;
import com.chess.engine8x8.player.ai.StandardBoardEvaluator;
import com.chess.pgn.FenUtilities;
import com.chess.pgn.MySqlGamePersistence;
import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.chess.pgn.PGNUtilities.persistPGNFile;
import static com.chess.pgn.PGNUtilities.writeGameToPGNFile;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table extends Observable{

    private final JFrame gameFrame;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;
    private final BoardPanel boardPanel;
    private final MoveLog moveLog;
    private final DialogBox dialogBox;

    private final GameSetup gameSetup;
    private final boolean useBook;
    private final String gameMode;

    private Board chessBoard;

    //Used from InfinityChess class

    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;

    private Move computerMove;

    private boolean highlightLegalMoves;
    private boolean showPieceInfo;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(1100, 750);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(560, 560);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(70,70);
    private static Color lightTileColor = Color.decode("#E8ECEF");
    private static Color darkTileColor = Color.decode("#7D8796");
    private static final Color backgroundColor = Color.decode("#004A9E");
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    private static File projectDirectory = new File(".");
    private static String projectDirectoryPath = projectDirectory.getAbsolutePath();
    private static String defaultMiscPath = projectDirectoryPath.substring(0, projectDirectoryPath.length()-1) + "/src/Piece_Images/misc/";

    //private static final Table INSTANCE = ChooseGame.table;

   //should be private
    public Table(Board board, String title){

        this.gameFrame = new JFrame(title);
        this.gameMode = title;
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.gameFrame.setBackground(backgroundColor);
        this.chessBoard = board;
        this.gameHistoryPanel = new GameHistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel(title);
        this.boardPanel = new BoardPanel();
        //this.boardPanel.setBorder(PANEL_BORDER);
        this.moveLog = new MoveLog();
        this.dialogBox = new DialogBox(title);
        this.addObserver(new TableGameAIWatcher());
        this.gameSetup = new GameSetup(this.gameFrame, true);
        this.useBook = false;
        this.boardDirection = BoardDirection.NORMAL;
        this.highlightLegalMoves = false;
        this.showPieceInfo = false;
        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.add(this.dialogBox, BorderLayout.SOUTH);
        center(this.gameFrame);
        this.gameFrame.setVisible(true);
    }

    public static Table get(){
        return ChooseGame.table;
    }

    public void show(){
        Table.get().getMoveLog().clear();
        Table.get().getGameHistoryPanel().redo(chessBoard, Table.get().getMoveLog());
        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
        Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
    }

    private GameSetup getGameSetup(){
        return this.gameSetup;
    }

    private boolean getUseBook() {
        return this.useBook;
    }

    private JFrame getGameFrame() { return this.gameFrame; }

    public Board getGameBoard(){
        return this.chessBoard;
    }

    private static void center(final JFrame frame) {
        final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        final int w = frame.getSize().width;
        final int h = frame.getSize().height;
        final int x = (dim.width - w) / 2;
        final int y = (dim.height - h) / 2;
        frame.setLocation(x, y);
    }

    private JMenuBar createTableMenuBar(){
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());
        tableMenuBar.add(createOptionsMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu(){
        final JMenu fileMenu = new JMenu("File");

        final JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChooseGame chooseGame = new ChooseGame();
                getGameFrame().dispose();
                chooseGame.setVisible(true);
            }
        });

        fileMenu.add(newGame);

        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                int option = chooser.showOpenDialog(Table.get().getGameFrame());
                if (option == JFileChooser.APPROVE_OPTION){
                    loadPGNFile(chooser.getSelectedFile());
                }
            }
        });
        fileMenu.add(openPGN);

        final JMenuItem openFEN = new JMenuItem("Load FEN File");
        openFEN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                String fenString = JOptionPane.showInputDialog("Input FEN");
                undoAllMoves();
                chessBoard = FenUtilities.createGameFromFEN(fenString);
                Table.get().getBoardPanel().drawBoard(chessBoard);
            }
        });
        fileMenu.add(openFEN);

        final JMenuItem saveToPGN = new JMenuItem("Save Game");
        saveToPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileFilter() {
                    @Override
                    public String getDescription() {
                        return ".pgn";
                    }
                    @Override
                    public boolean accept(final File file) {
                        return file.isDirectory() || file.getName().toLowerCase().endsWith("pgn");
                    }
                });
                final int option = chooser.showSaveDialog(Table.get().getGameFrame());
                if (option == JFileChooser.APPROVE_OPTION) {
                    savePGNFile(chooser.getSelectedFile());
                }
            }
        });
        fileMenu.add(saveToPGN);

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });

        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private JMenu createPreferencesMenu(){
        final JMenu preferencesMenu = new JMenu("Preferences");

        final JMenu colorChooserSubMenu = new JMenu("Choose Colors");
        colorChooserSubMenu.setMnemonic(KeyEvent.VK_S);

        final JMenuItem chooseDarkMenuItem = new JMenuItem("Choose Dark Tile Color");
        colorChooserSubMenu.add(chooseDarkMenuItem);

        final JMenuItem chooseLightMenuItem = new JMenuItem("Choose Light Tile Color");
        colorChooserSubMenu.add(chooseLightMenuItem);

        preferencesMenu.add(colorChooserSubMenu);

        chooseDarkMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final Color colorChoice = JColorChooser.showDialog(Table.get().getGameFrame(), "Choose Dark Tile Color",
                        Table.get().getGameFrame().getBackground());
                if (colorChoice != null) {
                    Table.get().getBoardPanel().setTileDarkColor(chessBoard, colorChoice);
                }
            }
        });

        chooseLightMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final Color colorChoice = JColorChooser.showDialog(Table.get().getGameFrame(), "Choose Light Tile Color",
                        Table.get().getGameFrame().getBackground());
                if (colorChoice != null) {
                    Table.get().getBoardPanel().setTileLightColor(chessBoard, colorChoice);
                }
            }
        });

        final JMenuItem changeCharacters = new JMenuItem("Change Characters");
        changeCharacters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeCharacters changer = new ChangeCharacters();
                changer.setVisible(true);

                boardPanel.drawBoard(chessBoard);
            }
        });
        preferencesMenu.add(changeCharacters);

        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
        flipBoardMenuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(final ActionEvent e){
                boardDirection = boardDirection.opposite();
                boardPanel.drawBoard(chessBoard);
            }
        });
        preferencesMenu.add(flipBoardMenuItem);
        preferencesMenu.addSeparator();
        final JCheckBoxMenuItem legalMoveHighlighterCheckbox = new JCheckBoxMenuItem("Highlight Legal Moves", false);
        legalMoveHighlighterCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highlightLegalMoves = legalMoveHighlighterCheckbox.isSelected();
            }
        });

        preferencesMenu.add(legalMoveHighlighterCheckbox);

        final JCheckBoxMenuItem showPieceInfoCheckbox = new JCheckBoxMenuItem("Show Piece Info When Clicked", false);
        legalMoveHighlighterCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPieceInfo = showPieceInfoCheckbox.isSelected();
            }
        });

        preferencesMenu.add(showPieceInfoCheckbox);

        return preferencesMenu;
    }

    private JMenu createOptionsMenu(){

        final JMenu optionsMenu = new JMenu("Options");

        final JMenuItem resetMenuItem = new JMenuItem("Reset Board");
        resetMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                undoAllMoves();
                Table.get().getDialogBox().makeInitialLabel(gameMode);
            }

        });
        optionsMenu.add(resetMenuItem);

        final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game");
        setupGameMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Table.get().getGameSetup().promptUser();
                Table.get().setupUpdate(Table.get().getGameSetup());
            }
        });

        optionsMenu.add(setupGameMenuItem);

        final JMenuItem evaluateBoardMenuItem = new JMenuItem("Evaluate Board");
        evaluateBoardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println(StandardBoardEvaluator.get().evaluate(chessBoard, gameSetup.getSearchDepth()));

            }
        });
        optionsMenu.add(evaluateBoardMenuItem);

        final JMenuItem escapeAnalysis = new JMenuItem("Escape Analysis Score");
        escapeAnalysis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if(moveLog.size() != 0){
                    final Move lastMove = moveLog.getMoves().get(moveLog.size() - 1);
                    if(lastMove != null) {
                        System.out.println(MoveUtilities.exchangeScore(lastMove));
                    }
                }

            }
        });
        optionsMenu.add(escapeAnalysis);

        final JMenuItem legalMovesMenuItem = new JMenuItem("Current State");
        legalMovesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.out.println(chessBoard.getWhitePieces());
                System.out.println(chessBoard.getBlackPieces());
                System.out.println(playerInfo(chessBoard.currentPlayer()));
                System.out.println(playerInfo(chessBoard.currentPlayer().getOpponent()));
            }
        });
        optionsMenu.add(legalMovesMenuItem);

        final JMenuItem undoMoveMenuItem = new JMenuItem("Undo Last Move");
        undoMoveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if(Table.get().getMoveLog().size() > 0) {
                    undoLastMove();
                }
            }
        });
        optionsMenu.add(undoMoveMenuItem);

        return optionsMenu;
    }

    private void setupUpdate(final GameSetup gameSetup){
        setChanged();
        notifyObservers(gameSetup);
    }

    private static class TableGameAIWatcher implements Observer{
        @Override
        public void update(final Observable o, final Object arg) {
            if (Table.get().getGameSetup().isAIPlayer(Table.get().getGameBoard().currentPlayer()) &&
            !Table.get().getGameBoard().currentPlayer().isInCheckMate() &&
            !Table.get().getGameBoard().currentPlayer().isInStalemate()){
                //create an AI thread
                //execute AI work
                final AIThinkTank thinkTank = new AIThinkTank();
                thinkTank.execute();
            }

            if(Table.get().getGameBoard().currentPlayer().isInCheckMate()){
                //JOptionPane.showMessageDialog(Table.get().getBoardPanel());
                Table.get().getDialogBox().setLabel("Checkmate", Table.get().getGameBoard().currentPlayer().getAlliance().toString());
                //System.out.println("Game Over, " + Table.get().getGameBoard().currentPlayer()+ " is in checkmate!");
            }
            if(Table.get().getGameBoard().currentPlayer().isInStalemate()){
                //JOptionPane.showMessageDialog(Table.get().getBoardPanel());
                Table.get().getDialogBox().setLabel("Stalemate", Table.get().getGameBoard().currentPlayer().getAlliance().toString());
                //System.out.println("Game Over, " + Table.get().getGameBoard().currentPlayer()+ " is in stalemate!");
            }
        }
    }

    private static String playerInfo(final Player player) {
        return ("Player is: " +player.getAlliance() + "\nlegal moves (" +player.getLegalMoves().size()+ ") = " +player.getLegalMoves() + "\ninCheck = " +
                player.isInCheck() + "\nisInCheckMate = " +player.isInCheckMate() +
                "\nisCastled = " +player.isCastled())+ "\n";
    }

    public void updateGameBoard(final Board board){
        this.chessBoard = board;
    }

    public void updateComputerMove(final Move move){
        this.computerMove = move;
    }

    private void undoAllMoves() {
        for(int i = Table.get().getMoveLog().size() - 1; i >= 0; i--) {
            final Move lastMove = Table.get().getMoveLog().removeMove(Table.get().getMoveLog().size() - 1);
            this.chessBoard = this.chessBoard.currentPlayer().unMakeMove(lastMove).getTransitionBoard();
        }
        this.computerMove = null;
        Table.get().getMoveLog().clear();
        Table.get().getGameHistoryPanel().redo(chessBoard, Table.get().getMoveLog());
        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
        Table.get().getBoardPanel().drawBoard(chessBoard);
    }

    private void undoLastMove() {
        final Move lastMove = Table.get().getMoveLog().removeMove(Table.get().getMoveLog().size() - 1);
        this.chessBoard = this.chessBoard.currentPlayer().unMakeMove(lastMove).getTransitionBoard();
        this.computerMove = null;
        Table.get().getMoveLog().removeMove(lastMove);
        Table.get().getGameHistoryPanel().redo(chessBoard, Table.get().getMoveLog());
        Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
        Table.get().getBoardPanel().drawBoard(chessBoard);
    }

    private static void loadPGNFile(final File pgnFile) {
        try {
            persistPGNFile(pgnFile);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private static void savePGNFile(final File pgnFile) {
        try {
            writeGameToPGNFile(pgnFile, Table.get().getMoveLog());
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private MoveLog getMoveLog(){
        return this.moveLog;
    }

    private GameHistoryPanel getGameHistoryPanel(){
        return this.gameHistoryPanel;
    }

    private TakenPiecesPanel getTakenPiecesPanel(){
        return this.takenPiecesPanel;
    }

    private DialogBox getDialogBox(){ return this.dialogBox; }

    private BoardPanel getBoardPanel(){
        return this.boardPanel;
    }

    private void moveMadeUpdate(final PlayerType playerType){
        setChanged();
        notifyObservers(playerType);
    }

    private static class AIThinkTank extends SwingWorker<Move, String> {

        private AIThinkTank() {
        }

        @Override
        protected Move doInBackground() throws Exception {
            final Move bestMove;
            final Move bookMove = Table.get().getUseBook()
                    ? MySqlGamePersistence.get().getNextBestMove(Table.get().getGameBoard(),
                    Table.get().getGameBoard().currentPlayer(),
                    Table.get().getMoveLog().getMoves().toString().replaceAll("\\[", "").replaceAll("\\]", ""))
                    : Move.MoveFactory.getNullMove();
            if (Table.get().getUseBook() && bookMove != Move.MoveFactory.getNullMove()) {
                bestMove = bookMove;
            }
            else {
                int numPieces = Table.get().getGameBoard().getWhitePieces().size() +
                        Table.get().getGameBoard().getBlackPieces().size();
                //int bonusDepth = Math.min(4, 1 + Math.round((float)32/numPieces));
                int bonusDepth = 0;
                final StockAlphaBeta strategy =
                        new StockAlphaBeta(Table.get().getGameSetup().getSearchDepth() + bonusDepth);
                //strategy.addObserver(Table.get().getDebugPanel());
                bestMove = strategy.execute(
                        Table.get().getGameBoard());
            }
            return bestMove;
        }

        @Override
        public void done() {
            try {
                final Move bestMove = get();
                Table.get().updateComputerMove(bestMove);
                Table.get().updateGameBoard(Table.get().getGameBoard().currentPlayer().makeMove(bestMove).getTransitionBoard());
                Table.get().getMoveLog().addMove(bestMove);
                Table.get().getGameHistoryPanel().redo(Table.get().getGameBoard(), Table.get().getMoveLog());
                Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
                Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
                //Table.get().getDebugPanel().redo();
                Table.get().moveMadeUpdate(PlayerType.COMPUTER);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    public enum BoardDirection {
        NORMAL {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles){
                return boardTiles;
            }

            @Override
            BoardDirection opposite(){
                return FLIPPED;
            }
        },
        FLIPPED {
            @Override
            List<TilePanel> traverse(final List<TilePanel> boardTiles){
                return Lists.reverse(boardTiles);
            }

            @Override
            BoardDirection opposite(){
                return NORMAL;
            }
        };

        abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
        abstract BoardDirection opposite();
    }

    private class BoardPanel extends JPanel{
        final List<TilePanel> boardTiles;

        BoardPanel(){
            super(new GridLayout(8, 8));
            this.boardTiles = new ArrayList<>();
            for(int i = 0; i < BoardUtilities.NUM_TILES; i++){
                final TilePanel tilePanel = new TilePanel(this, i);
                this.boardTiles.add(tilePanel);
                tilePanel.setBorder(PANEL_BORDER);
                add(tilePanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(final Board board){
            removeAll();
            for(final TilePanel tilePanel : boardDirection.traverse(boardTiles)){
                tilePanel.drawTile(board);
                add(tilePanel);
            }
            validate();
            repaint();
        }

        void setTileDarkColor(final Board board,
                              final Color darkColor) {
            for (final TilePanel boardTile : boardTiles) {
                boardTile.setDarkTileColor(darkColor);
            }
            drawBoard(board);
        }

        void setTileLightColor(final Board board,
                               final Color lightColor) {
            for (final TilePanel boardTile : boardTiles) {
                boardTile.setLightTileColor(lightColor);
            }
            drawBoard(board);
        }
    }

    public static class MoveLog{
        private final List<Move> moves;

        MoveLog(){
            this.moves = new ArrayList<>();
        }

        public List<Move> getMoves(){
            return this.moves;
        }

        public void addMove(final Move move){
            this.moves.add(move);
        }

        public int size(){
            return this.moves.size();
        }

        public void clear(){
            this.moves.clear();
        }

        public Move removeMove(int index){
            return this.moves.remove(index);
        }

        public boolean removeMove(final Move move){
            return this.moves.remove(move);
        }
    }

    enum PlayerType{
        HUMAN,
        COMPUTER
    }

    public class TilePanel extends JPanel{

        private final int tileId;

        TilePanel(final BoardPanel boardPanel,
                  final int tileId){
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            assignTilePieceIcon(chessBoard);

            addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(final MouseEvent e) {

                    if(isRightMouseButton(e)) {

                        sourceTile = null;
                        destinationTile = null;
                        humanMovedPiece = null;

                    } else if(isLeftMouseButton(e)){
                        if(sourceTile == null){
                            sourceTile = chessBoard.getTile(tileId);
                            humanMovedPiece = sourceTile.getPiece();
                            if(humanMovedPiece == null){
                                sourceTile = null;
                            }
                        } else{

                            destinationTile = chessBoard.getTile(tileId);
                            final Move move = Move.MoveFactory.createMove(chessBoard,
                                                                            sourceTile.getTileCoordinate(),
                                                                            destinationTile.getTileCoordinate());
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if(transition.getMoveStatus().isDone()){
                                chessBoard = transition.getTransitionBoard();
                                moveLog.addMove(move);
                            }
                            Table.get().getDialogBox().updateLabel(move);
                            sourceTile = null;
                            destinationTile = null;
                            humanMovedPiece = null;

                        }
                        SwingUtilities.invokeLater(new Runnable(){
                            @Override
                            public void run(){
                                gameHistoryPanel.redo(chessBoard, moveLog);
                                takenPiecesPanel.redo(moveLog);

                                if(gameSetup.isAIPlayer(chessBoard.currentPlayer())){
                                    Table.get().moveMadeUpdate(PlayerType.HUMAN);
                                }

                                boardPanel.drawBoard(chessBoard);
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });

            validate();
        }

        public void drawTile(final Board board){
            assignTileColor();
            assignTilePieceIcon(board);
            highlightLegals(board);
            validate();
            repaint();
        }

        void setLightTileColor(final Color color) {
            lightTileColor = color;
        }

        void setDarkTileColor(final Color color) {
            darkTileColor = color;
        }

        private void assignTilePieceIcon(final Board board){

            this.removeAll();
            //System.out.println(iconMap);
            if(board.getTile(this.tileId).isTileOccupied()){
                try {

                    //WHITE => W
                    //BISHOP => B
                    String alliance = board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0, 1);
                    String piece = board.getTile(this.tileId).getPiece().toString();
                    //System.out.println(alliance + piece);
                    //System.out.println(iconMap.get(alliance + piece));
                    final BufferedImage image =
                            //ImageIO.read(new File(defaultPieceImagesPath +
                              //      board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0, 1) +
                              //      board.getTile(this.tileId).getPiece().toString() +
                              //      ".png"));
                            ImageIO.read(new File(ChooseGame.iconMap.get(alliance + piece)));

                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        private void highlightLegals(final Board board){
            if(highlightLegalMoves){
                for(final Move move: pieceLegalMoves(board)){
                    if(move.getDestinationCoordinate() == this.tileId){
                        try{
                            add(new JLabel(new ImageIcon(ImageIO.read(new File(defaultMiscPath + "Green_Arc_Reactor.png")))));
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private void showPieceInfo(final Piece piece){
            if(showPieceInfo){

            }
        }

        private Collection<Move> pieceLegalMoves(final Board board){
            if(humanMovedPiece != null &&
                    humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance()){
                return humanMovedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }

        private void assignTileColor(){
            if(BoardUtilities.INSTANCE.EIGHTH_RANK[this.tileId] ||
                BoardUtilities.INSTANCE.SIXTH_RANK[this.tileId] ||
                BoardUtilities.INSTANCE.FOURTH_RANK[this.tileId] ||
                BoardUtilities.INSTANCE.SECOND_RANK[this.tileId]){
                setBackground(this.tileId % 2 == 0 ? lightTileColor : darkTileColor);
            } else if(BoardUtilities.INSTANCE.SEVENTH_RANK[this.tileId] ||
                        BoardUtilities.INSTANCE.FIFTH_RANK[this.tileId] ||
                        BoardUtilities.INSTANCE.THIRD_RANK[this.tileId] ||
                        BoardUtilities.INSTANCE.FIRST_RANK[this.tileId]){
                setBackground(this.tileId % 2 != 0 ? lightTileColor : darkTileColor);
            }

        }

    }

}

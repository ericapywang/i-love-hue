package org.cis120.ilovehue;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

public class DrawBoard extends JPanel {

    private ILoveHue game;
    private final JLabel status;
    private boolean gameStarted;
    private boolean gameWon;

    public DrawBoard(JLabel statusInit) {

        game = new ILoveHue();
        status = statusInit;
        gameStarted = false;
        gameWon = false;

        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (gameStarted) {
                    Point p = e.getPoint();
                    if (p.x > 0 && p.x < 320 && p.y > 0 && p.y < 320) {
                        System.out.println("x: " + p.x + " y: " + p.y);
                        int row = p.x / 40;
                        int col = p.y / 40;
                        if (game.getFirstTile() == null) {
                            game.setFirstTile(row, col);
                        } else {
                            game.setSecondTile(row, col);
                            game.playTurn();
                        }
                        updateStatus();
                        repaint(); // repaints the game board
                    }
                }
            }
        });

    }


    private void updateStatus() {
        if (gameStarted) {
            status.setText("Moves: " + game.getMoves());
            gameWon = game.checkBoard();
        } else {
            status.setText("Moves: 0");
        }

        if (gameWon) {
            status.setText("Congrats, you won! Moves: " + game.getMoves());
            gameStarted = false;
        }
    }

    public void undo() {
        game.undo();
        updateStatus();
        repaint();
    }

    public void newGame() {
        game.newGame();
        gameStarted = false;
        gameWon = false;
        updateStatus();
        repaint();

        requestFocusInWindow();
    }

    public boolean start() {
        if (!gameStarted) {
            game.randomizeBoard();
            gameStarted = true;
            gameWon = false;

            updateStatus();
            repaint();

            return true;
        }
        return false;
    }


    // helper method to return the board from the lines
    public Tile[][] getBoard(List<String> lines) {

        String[] rgbValuesList;
        String[] individualValues;
        Tile[][] board = new Tile[8][8];
        int r;
        int g;
        int b;

        for (int row = 0; row < 8; row++) {
            rgbValuesList = lines.get(row).split(", ");

            for (int col = 0; col < 8; col++) {
                individualValues = rgbValuesList[col].split(" ");
                r = Integer.parseInt(individualValues[0]);
                g = Integer.parseInt(individualValues[1]);
                b = Integer.parseInt(individualValues[2]);

                // fill in board
                board[row][col] = new Tile(r, g, b, row, col);
            }
        }
        return board;
    }

    public void loadGame() {

        gameWon = false;

        // fileReader
        FileReader fileReader = new FileReader();
        List<String> lines = new LinkedList<>();
        try {
            lines = fileReader.readIn(new BufferedReader(new java.io.FileReader(
                    "files/game_state.csv")));
        } catch (java.io.FileNotFoundException fileNotFoundException) {
            final JFrame exception = new JFrame("ERROR MESSAGE");
            JOptionPane.showMessageDialog(exception, "ERROR: IOException occurred");
        }

        Tile[][] originalBoard;
        Tile[][] scrambledBoard;

        // read in the original board state
        originalBoard = getBoard(lines);
        lines.subList(0, 8).clear();

        scrambledBoard = getBoard(lines);
        lines.subList(0, 8).clear();

        for (String s : lines) {
            System.out.println(s);
        }

        LinkedList<Tile[]> tilesSwapped = new LinkedList<Tile[]>();
        String[] pos;

        int row1;
        int col1;
        int row2;
        int col2;

        // read into LinkedList
        for (int i = 0; i < lines.size(); i++) {

            pos = lines.get(i).split(" ");
            row1 = Integer.parseInt(pos[0]);
            col1 = Integer.parseInt(pos[1]);
            row2 = Integer.parseInt(pos[2]);
            col2 = Integer.parseInt(pos[3]);

            Tile[] t = new Tile[2];
            t[0] = originalBoard[row1][col1];
            t[1] = originalBoard[row2][col2];

            tilesSwapped.add(t);

        }

        game = new ILoveHue(originalBoard, scrambledBoard);

        int length = tilesSwapped.size();


        // loop through and play turns
        for (int i = 0; i < length; i++) {
            game.setFirstTile(tilesSwapped.get(i)[0].getRow(), tilesSwapped.get(i)[0].getCol());
            game.setSecondTile(tilesSwapped.get(i)[1].getRow(), tilesSwapped.get(i)[1].getCol());
            game.playTurn();

            System.out.println("running through: turn " + game.getMoves());
        }

        gameStarted = true;
        updateStatus();
        repaint();
    }

    public boolean checkBoard() {
        return game.checkBoard();
    }

    /** SETTERS/GETTERS */
    public LinkedList<Tile[]> getTilesSwapped() {
        return game.getTilesSwapped();
    }

    public LinkedList<String> getOriginalRGB() {
        return game.getOriginalRGB();
    }

    public LinkedList<String> getScrambledRGB() {
        return game.getScrambledRGB();
    }



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.draw(g);
    }


}

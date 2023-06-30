package org.cis120.ilovehue;

import java.util.LinkedList;
import java.awt.*;
import java.util.Random;

public class ILoveHue {

    private Board board;
    private Tile firstTile;
    private Tile secondTile;

    private LinkedList<String> originalValues;
    private LinkedList<String> scrambledValues;

    public ILoveHue() {
        generateBoard();

        firstTile = null;
        secondTile = null;
    }

    public ILoveHue(Tile[][] originalBoard, Tile[][] scrambledBoard) {
        board = new Board(originalBoard, scrambledBoard);

        originalValues = writeBoard(originalBoard);
        scrambledValues = writeBoard(scrambledBoard);

    }

    public ILoveHue(Tile[][] originalBoard, Tile[][] scrambledBoard,
                    LinkedList<Tile[]> swappedTiles, Tile firstTile, Tile secondTile) {
        board = new Board(originalBoard, scrambledBoard, swappedTiles);
        this.firstTile = firstTile;
        this.secondTile = secondTile;

        originalValues = writeBoard(originalBoard);
        scrambledValues = writeBoard(scrambledBoard);

    }

    // write a board's RGB values into a LinkedList of strings -- for File I/O
    public LinkedList<String> writeBoard(Tile[][] board) {
        LinkedList<String> writtenBoard = new LinkedList<>();
        String line = "";
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                line += board[row][col].getR() + " " + board[row][col].getG() + " " +
                        board[row][col].getB();
                if (col != 7) {
                    line += ", ";
                }
            }
            writtenBoard.add(line);
            line = "";
        }

        return writtenBoard;

    }


    // generate the board
    public void generateBoard() {

        board = new Board();

        Tile[][] tiles = new Tile[8][8];

        // generate 2 left corners
        Random rand = new Random();

        Color c1 = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        Color c2 = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));

        tiles[0][0] = new Tile(c1.getRed(), c1.getGreen(), c1.getBlue(), 0, 0);
        tiles[7][0] = new Tile(c2.getRed(), c2.getGreen(), c2.getBlue(), 7, 0);

        // generate left column
        tiles = generateStarterRow(c1, c2, 0, tiles);
        board.addState(tiles);

        // generate 2 right corners
        Color c3 = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        Color c4 = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));

        tiles[0][7] = new Tile(c3.getRed(), c3.getGreen(), c3.getBlue(), 0, 7);
        tiles[7][7] = new Tile(c4.getRed(), c4.getGreen(), c4.getBlue(), 7, 7);

        // generate right column
        tiles = generateStarterRow(c3, c4, 7, tiles);
        board.addState(tiles);

        // generate everything in between
        for (int row = 0; row < 8; row++) {
            Tile t1 = board.getGeneratedTile(row, 0);
            Tile t2 = board.getGeneratedTile(row, 7);
            tiles = generateCols(t1, t2, row, tiles);
        }

        originalValues = writeBoard(tiles);
        board.addState(tiles); // add original board state
        board.boardGenerated();

    }

    /** ORIGINAL BOARD GENERATION HELPER METHODS **/
    public Tile[][] generateStarterRow(Color c1, Color c2, int col, Tile[][] tiles) {
        int rInc = (c1.getRed() - c2.getRed()) / 7;
        int gInc = (c1.getGreen() - c2.getGreen()) / 7;
        int bInc = (c1.getBlue() - c2.getBlue()) / 7;

        int r = c1.getRed();
        int g = c1.getGreen();
        int b = c1.getBlue();

        for (int row = 1; row < 7; row++) {
            r -= rInc;
            g -= gInc;
            b -= bInc;

            tiles[row][col] = new Tile(r, g, b, row, col);
        }

        return tiles;

    }

    public Tile[][] generateCols(Tile t1, Tile t2, int row, Tile[][] tiles) {
        int rInc = (t1.getR() - t2.getR()) / 7;
        int gInc = (t1.getG() - t2.getG()) / 7;
        int bInc = (t1.getB() - t2.getB()) / 7;

        int r = t1.getR();
        int g = t1.getG();
        int b = t1.getB();

        for (int col = 1; col < 7; col++) {
            r -= rInc;
            g -= gInc;
            b -= bInc;

            tiles[row][col] = new Tile(r, g, b, row, col);
        }

        return tiles;
    }

    // randomize the board after original generation
    public void randomizeBoard() {
        LinkedList<Tile> tiles = new LinkedList<>();

        // copy everything into a linked list
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (!((row == 0 && col == 0) || (row == 0 && col == 7) || (row == 7 && col == 0)
                        || (row == 7 && col == 7))) {
                    tiles.add(board.getGeneratedTile(row, col));
                }
            }
        }

        Random rand = new Random();
        int i;
        Tile[][] t = new Tile[8][8];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (!((row == 0 && col == 0) || (row == 0 && col == 7) || (row == 7 && col == 0)
                        || (row == 7 && col == 7))) {

                    // randomly pull tiles
                    i = rand.nextInt(tiles.size());

                    t[row][col] = tiles.get(i);
                    t[row][col].setRow(row);
                    t[row][col].setCol(col);

                    tiles.remove(i);
                } else {
                    t[row][col] = board.getGeneratedTile(row, col);
                    System.out.println("corner");
                }
            }
        }

        scrambledValues = writeBoard(t);
        board.addState(t);

    }


    /** GAMEPLAY METHODS **/
    // runs for each turn, checks if a play was successful
    public boolean playTurn() {
        System.out.println("playing turn:");

        // if both tiles are set to something and if the same tile isn't clicked for one turn, play
        if ((firstTile != null && secondTile != null)
                && ((firstTile.getRow() != secondTile.getRow())
                || (firstTile.getCol() != secondTile.getCol()))) {
            System.out.println("switching");
            board.switchTiles(firstTile, secondTile);
            board.incMoves();

            firstTile = null;
            secondTile = null;

            return true;

        }

        return false;

    }

    public void undo() {
        board.undo();
    }

    public boolean checkBoard() {
        return board.boardComplete();
    }

    public void newGame() {
        firstTile = null;
        secondTile = null;
        generateBoard();
    }

    /** SETTERS/GETTERS **/
    public boolean checkNotCornerTile(int row, int col) {
        return !(row == 0 && col == 0) && !(row == 7 && col == 0) && !(row == 0 && col == 7)
                && !(row == 7 && col == 7);
    }

    public void setFirstTile(int row, int col) {
        if (checkNotCornerTile(row, col)) {
            firstTile = board.getTile(row, col);
        }
    }

    public void setSecondTile(int row, int col) {
        if (checkNotCornerTile(row, col)) {
            secondTile = board.getTile(row, col);
        }
    }

    public Tile getTile(int row, int col) {
        return board.getTile(row, col);
    }

    public Tile getFirstTile() {
        return firstTile;
    }

    public Tile getSecondTile() {
        return secondTile;
    }

    public int getMoves() {
        return board.getMoves();
    }

    public LinkedList<String> getOriginalRGB() {
        return originalValues;
    }

    public LinkedList<String> getScrambledRGB() {
        return scrambledValues;
    }

    public int getBoardStatesLength() {
        return board.getBoardStatesLength();
    }

    public Tile[][] getBoardState(int i) {
        return board.getBoardState(i);
    }

    public LinkedList<Tile[]> getTilesSwapped() {
        return board.getTilesSwapped();
    }



    /** DRAW **/
    public void draw(Graphics g) {
        // Draws board grid
        g.drawLine(0, 0, 0, 320);
        g.drawLine(0, 320, 320, 320);
        g.drawLine(320, 320, 320, 0);
        g.drawLine(320, 0, 0, 0);

        board.draw(g);
    }


}

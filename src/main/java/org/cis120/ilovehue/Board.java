package org.cis120.ilovehue;

import java.util.LinkedList;
import java.awt.*;

public class Board {

    // private final Tile[][] originalBoard; // original unscrambled board
    private LinkedList<Tile[][]> boardStates;
    private int moves;

    private LinkedList<Tile[]> tilesSwapped;

    // generate new board
    public Board() {
        boardStates = new LinkedList<>();
        boardStates.add(new Tile[8][8]);
        tilesSwapped = new LinkedList<>();
        moves = 0;
    }

    public Board(Tile[][] originalBoard, Tile[][] scrambledBoard) {
        boardStates = new LinkedList<>();
        boardStates.add(originalBoard);
        boardStates.add(scrambledBoard);

        tilesSwapped = new LinkedList<>();
        moves = 0;
    }

    public Board(Tile[][] originalBoard, Tile[][] scrambledBoard, LinkedList<Tile[]> tilesSwapped) {
        boardStates = new LinkedList<>();
        boardStates.add(originalBoard);
        boardStates.add(scrambledBoard);

        this.tilesSwapped = tilesSwapped;
        moves = tilesSwapped.size();
    }

    /** GAMEPLAY METHODS **/
    // new move
    public void switchTiles(Tile firstTile, Tile secondTile) {

        System.out.println("switching tiles (" + firstTile.getRow() + ", " + firstTile.getCol()
                + ") and (" + secondTile.getRow() + ", " + secondTile.getCol() + ")");
        Tile[] tiles = new Tile[2];
        tiles[0] = firstTile;
        tiles[1] = secondTile;

        int[] tilesInt = new int[4];
        tilesInt[0] = firstTile.getRow();
        tilesInt[1] = firstTile.getCol();
        tilesInt[2] = secondTile.getRow();
        tilesInt[3] = secondTile.getCol();

        tilesSwapped.add(tiles);

        int initRow = firstTile.getRow();
        int initCol = firstTile.getCol();

        int newRow = secondTile.getRow();
        int newCol = secondTile.getCol();

        Tile[][] b = boardStates.getLast().clone();

        b[newRow][newCol] = firstTile;
        b[initRow][initCol] = secondTile;

        firstTile.setRow(newRow);
        firstTile.setCol(newCol);

        secondTile.setRow(initRow);
        secondTile.setCol(initCol);

        boardStates.add(b);
        System.out.println("new state added at index " + boardStates.indexOf(b));
        System.out.println("boardStates list: " + boardStates.size());

    }

    // if undo is clicked
    public void undo() {
        if (moves != 0) {
            Tile[] tiles = tilesSwapped.getLast();
            System.out.println("row1 " + tiles[1].getRow() + " col1 " + tiles[1].getCol());
            System.out.println("row0 " + tiles[0].getRow() + " col0 " + tiles[0].getCol());

            System.out.println("r1 " + tiles[1].getR() + " g1 " + tiles[1].getG() + " b1 "
                    + tiles[1].getB());
            System.out.println("r0 " + tiles[0].getR() + " g0 " + tiles[0].getG() + " b0 "
                    + tiles[0].getB());

            tilesSwapped.removeLast();

            boardStates.removeLast();
            moves--;

            switchTiles(tiles[1], tiles[0]);

            tilesSwapped.removeLast();

            boardStates.removeLast();
        }
    }

    // check if board is complete
    public boolean boardComplete() {

        if (boardStates.size() == 1) {
            return false;
        }
        Tile[][] currentBoard = boardStates.getLast();
        Tile[][] originalBoard = boardStates.getFirst();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                System.out.println("row: " + row + " col: " + col);
                System.out.println("r1: " + currentBoard[row][col].getR() + " g1: "
                        + currentBoard[row][col].getG() + " b1: " + currentBoard[row][col].getB());
                System.out.println("r2: " + originalBoard[row][col].getR() + " g2: "
                        + originalBoard[row][col].getG() + " b2: "
                        + originalBoard[row][col].getB());

                if ((currentBoard[row][col].getR() != originalBoard[row][col].getR())
                        || (currentBoard[row][col].getG() != originalBoard[row][col].getG())
                        || (currentBoard[row][col].getB() != originalBoard[row][col].getB())) {
                    System.out.println("false");
                    System.out.println("-----------------------");
                    return false;
                }
            }
        }

        System.out.println("true");
        return true;

    }

    public void boardGenerated() {
        while (boardStates.size() != 1) {
            boardStates.removeFirst();
        }
    }

    // add newly scrambled board to LinkedList
    public void addState(Tile[][] b) {
        boardStates.add(b);
    }


    /** SETTER/GETTER METHODS **/
    public Tile getGeneratedTile(int row, int col) {
        Tile[][] originalBoard = boardStates.getLast();
        return originalBoard[row][col];
    }

    // get the tile that the player clicks on
    public Tile getTile(int row, int col) {
        System.out.println("row " + row + " col " + col);

        Tile[][] b = boardStates.getLast();
        System.out.println("r: " + b[row][col].getR() + " g: " + b[row][col].getG() + " b: "
                + b[row][col].getB());
        return b[row][col];

    }

    public void incMoves() {
        moves++;
        System.out.println("MOVES: " + moves);
        System.out.println("BOARD STATES: " + boardStates.size());
    }

    public int getMoves() {
        return moves;
    }

    public int getBoardStatesLength() {
        return boardStates.size();
    }

    public Tile[][] getBoardState(int i) {
        return boardStates.get(i);
    }

    public LinkedList<Tile[]> getTilesSwapped() {
        return tilesSwapped;
    }


    /** DRAW */
    public void draw(Graphics g) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boardStates.getLast()[row][col].draw(g);
            }
        }
    }

}

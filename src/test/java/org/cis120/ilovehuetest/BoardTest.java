package org.cis120.ilovehuetest;

import org.cis120.ilovehue.ILoveHue;
import org.cis120.ilovehue.Board;
import static org.junit.jupiter.api.Assertions.*;
import org.cis120.ilovehue.Tile;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class BoardTest {

    /** TEST SWITCH TILES */
    @Test
    public void testSwitchTiles() {
        ILoveHue game = new ILoveHue();
        Board b = new Board(game.getBoardState(0), game.getBoardState(0), new LinkedList<>());

        Tile[][] originalBoard = b.getBoardState(0);
        Tile[][] randomizedBoard = b.getBoardState(1);

        assertEquals(originalBoard[0][0].getR(), randomizedBoard[0][0].getR());
        assertEquals(originalBoard[0][0].getG(), randomizedBoard[0][0].getG());
        assertEquals(originalBoard[0][0].getB(), randomizedBoard[0][0].getB());

        int r1 = b.getTile(5, 5).getR();
        int g1 = b.getTile(5, 5).getG();
        int b1 = b.getTile(5, 5).getB();

        int r2 = b.getTile(3, 3).getR();
        int g2 = b.getTile(3, 3).getG();
        int b2 = b.getTile(3, 3).getB();

        b.switchTiles(b.getTile(5, 5), b.getTile(3, 3));

        Tile[][] move1 = b.getBoardState(2);

        assertEquals(originalBoard[2][0].getR(), move1[2][0].getR());
        assertEquals(originalBoard[2][0].getG(), move1[2][0].getG());
        assertEquals(originalBoard[2][0].getB(), move1[2][0].getB());

        assertNotEquals(originalBoard[5][5].getR(), r1);
        assertNotEquals(originalBoard[5][5].getG(), g1);
        assertNotEquals(originalBoard[5][5].getB(), b1);

        assertNotEquals(originalBoard[3][3].getR(), r2);
        assertNotEquals(originalBoard[3][3].getG(), g2);
        assertNotEquals(originalBoard[3][3].getB(), b2);

        assertEquals(move1[3][3].getR(), r1);
        assertEquals(move1[3][3].getG(), g1);
        assertEquals(move1[3][3].getB(), b1);

        assertEquals(move1[5][5].getR(), r2);
        assertEquals(move1[5][5].getG(), g2);
        assertEquals(move1[5][5].getB(), b2);

    }

    /** UNDO TESTS */
    @Test
    public void undoTest() {
        ILoveHue game = new ILoveHue();
        assertEquals(game.getMoves(), 0);
        assertEquals(game.getBoardStatesLength(), 1);

        game.randomizeBoard();
        assertEquals(game.getMoves(), 0);
        assertEquals(game.getBoardStatesLength(), 2);

        game.setFirstTile(5, 0); // [5][0]
        Tile t1 = game.getFirstTile();
        int r1 = t1.getR();
        int g1 = t1.getG();
        int b1 = t1.getB();

        game.setSecondTile(1, 0); // [0][0]
        Tile t2 = game.getSecondTile();
        int r2 = t2.getR();
        int g2 = t2.getG();
        int b2 = t2.getB();
        game.playTurn();

        assertEquals(game.getMoves(), 1);
        assertEquals(game.getBoardStatesLength(), 3);

        assertEquals(game.getTile(5, 0).getR(), r2);
        assertEquals(game.getTile(5, 0).getG(), g2);
        assertEquals(game.getTile(5, 0).getB(), b2);

        assertEquals(game.getTile(1, 0).getR(), r1);
        assertEquals(game.getTile(1, 0).getG(), g1);
        assertEquals(game.getTile(1, 0).getB(), b1);

        // undo once
        game.undo();
        assertEquals(game.getMoves(), 0);
        assertEquals(game.getBoardStatesLength(), 2);

        assertEquals(game.getTile(5, 0).getR(), r1);
        assertEquals(game.getTile(5, 0).getG(), g1);
        assertEquals(game.getTile(5, 0).getB(), b1);

        assertEquals(game.getTile(1, 0).getR(), r2);
        assertEquals(game.getTile(1, 0).getG(), g2);
        assertEquals(game.getTile(1, 0).getB(), b2);

        // undo on nothing
        game.undo();
        assertEquals(game.getMoves(), 0);
        assertEquals(game.getBoardStatesLength(), 2);

    }

    @Test
    public void testMultipleUndo() {
        ILoveHue game = new ILoveHue();
        assertEquals(game.getMoves(), 0);
        assertEquals(game.getBoardStatesLength(), 1);

        game.randomizeBoard();
        assertEquals(game.getMoves(), 0);
        assertEquals(game.getBoardStatesLength(), 2);

        game.setFirstTile(5, 0); // [5][0]
        Tile t1 = game.getFirstTile();
        int r1 = t1.getR();
        int g1 = t1.getG();
        int b1 = t1.getB();

        game.setSecondTile(1, 0); // [1][0]
        Tile t2 = game.getSecondTile();
        int r2 = t2.getR();
        int g2 = t2.getG();
        int b2 = t2.getB();

        // turn 1
        game.playTurn();

        assertEquals(game.getMoves(), 1);
        assertEquals(game.getBoardStatesLength(), 3);

        assertEquals(game.getTile(5, 0).getR(), r2);
        assertEquals(game.getTile(5, 0).getG(), g2);
        assertEquals(game.getTile(5, 0).getB(), b2);

        assertEquals(game.getTile(1, 0).getR(), r1);
        assertEquals(game.getTile(1, 0).getG(), g1);
        assertEquals(game.getTile(1, 0).getB(), b1);

        System.out.println("turn 1 successful");

        // turn 2
        game.setFirstTile(1, 0);
        Tile t3 = game.getFirstTile();
        int r3 = t3.getR();
        int g3 = t3.getG();
        int b3 = t3.getB();

        game.setSecondTile(3, 2);
        Tile t4 = game.getSecondTile();
        int r4 = t4.getR();
        int g4 = t4.getG();
        int b4 = t4.getB();

        game.playTurn();

        assertEquals(game.getMoves(), 2);
        assertEquals(game.getBoardStatesLength(), 4);

        assertEquals(game.getTile(3, 2).getR(), r3);
        assertEquals(game.getTile(3, 2).getG(), g3);
        assertEquals(game.getTile(3, 2).getB(), b3);

        assertEquals(game.getTile(1, 0).getR(), r4);
        assertEquals(game.getTile(1, 0).getG(), g4);
        assertEquals(game.getTile(1, 0).getB(), b4);

        assertEquals(r1, r3);
        assertEquals(g1, g3);
        assertEquals(b1, b3);

        System.out.println("turn 2 successful");

        // undo 1
        game.undo();
        assertEquals(game.getMoves(), 1);
        assertEquals(game.getBoardStatesLength(), 3);

        assertEquals(game.getTile(3, 2).getR(), r4);
        assertEquals(game.getTile(3, 2).getG(), g4);
        assertEquals(game.getTile(3, 2).getB(), b4);

        assertEquals(game.getTile(1, 0).getR(), r3);
        assertEquals(game.getTile(1, 0).getG(), g3);
        assertEquals(game.getTile(1, 0).getB(), b3);

        System.out.println("undo 1 successful");

        // undo 2
        game.undo();
        assertEquals(game.getMoves(), 0);
        assertEquals(game.getBoardStatesLength(), 2);

        assertEquals(game.getTile(5, 0).getR(), r1);
        assertEquals(game.getTile(5, 0).getG(), g1);
        assertEquals(game.getTile(5, 0).getB(), b1);

        assertEquals(game.getTile(1, 0).getR(), r2);
        assertEquals(game.getTile(1, 0).getG(), g2);
        assertEquals(game.getTile(1, 0).getB(), b2);

        System.out.println("undo 2 successful");

    }

    /** TEST BOARD COMPLETE */
    @Test
    public void testBoardNotComplete() {
        ILoveHue game = new ILoveHue();
        game.randomizeBoard();

        Tile[][] originalBoard = game.getBoardState(0);
        Tile[][] randomizedBoard = game.getBoardState(1);
        Board b = new Board(originalBoard, randomizedBoard, game.getTilesSwapped());

        b.switchTiles(b.getTile(5, 5), b.getTile(3, 3));
        assertFalse(b.boardComplete());

    }

    @Test
    public void testBoardComplete() {
        ILoveHue game = new ILoveHue();

        Tile[][] originalBoard = game.getBoardState(0);

        Board b = new Board(originalBoard, originalBoard, game.getTilesSwapped());
        assertTrue(b.boardComplete());

    }

    /** TEST GET TILE */
    @Test
    public void testGetTile() {

        ILoveHue game = new ILoveHue();
        Tile[][] board = game.getBoardState(0);

        Board b = new Board(board, board, game.getTilesSwapped());

        assertEquals(board[0][0].getR(), b.getTile(0, 0).getR());
        assertEquals(board[0][0].getG(), b.getTile(0, 0).getG());
        assertEquals(board[0][0].getB(), b.getTile(0, 0).getB());

    }


}

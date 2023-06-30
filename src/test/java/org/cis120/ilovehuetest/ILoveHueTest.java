package org.cis120.ilovehuetest;

import org.cis120.ilovehue.ILoveHue;
import static org.junit.jupiter.api.Assertions.*;

import org.cis120.ilovehue.Tile;
import org.junit.jupiter.api.Test;

public class ILoveHueTest {

    /** GENERATE BOARD TEST */
    @Test
    public void testGenerateBoard() {

        ILoveHue game = new ILoveHue();
        assertEquals(game.getBoardStatesLength(), 1);

    }

    /** RANDOMIZE BOARD TEST */
    @Test
    public void testRandomizeBoardCorners() {

        ILoveHue game = new ILoveHue();
        Tile[][] originalBoard = game.getBoardState(0);

        game.randomizeBoard();
        Tile[][] randomizedBoard = game.getBoardState(1);

        assertEquals(game.getBoardStatesLength(), 2);

        assertEquals(originalBoard[0][0].getR(), randomizedBoard[0][0].getR());
        assertEquals(originalBoard[0][0].getG(), randomizedBoard[0][0].getG());
        assertEquals(originalBoard[0][0].getB(), randomizedBoard[0][0].getB());

        assertEquals(originalBoard[0][7].getR(), randomizedBoard[0][7].getR());
        assertEquals(originalBoard[0][7].getG(), randomizedBoard[0][7].getG());
        assertEquals(originalBoard[0][7].getB(), randomizedBoard[0][7].getB());

        assertEquals(originalBoard[7][0].getR(), randomizedBoard[7][0].getR());
        assertEquals(originalBoard[7][0].getG(), randomizedBoard[7][0].getG());
        assertEquals(originalBoard[7][0].getB(), randomizedBoard[7][0].getB());

        assertEquals(originalBoard[7][7].getR(), randomizedBoard[7][7].getR());
        assertEquals(originalBoard[7][7].getG(), randomizedBoard[7][7].getG());
        assertEquals(originalBoard[7][7].getB(), randomizedBoard[7][7].getB());

    }

    @Test
    public void testRandomizeBoardTiles() {
        ILoveHue game = new ILoveHue();
        Tile[][] originalBoard = game.getBoardState(0);

        game.randomizeBoard();
        Tile[][] randomizedBoard = game.getBoardState(1);

        assertEquals(game.getBoardStatesLength(), 2);

        // will not pass if the original tile is randomly placed in the same location,
        // but small probability of this occurring
        assertNotEquals(originalBoard[4][3].getR(), randomizedBoard[4][3].getR());
        assertNotEquals(originalBoard[4][3].getG(), randomizedBoard[4][3].getG());
        assertNotEquals(originalBoard[4][3].getB(), randomizedBoard[4][3].getB());

    }

    /** PLAY TURN TEST */
    @Test
    public void testPlayTurn() {
        ILoveHue game = new ILoveHue();
        game.randomizeBoard();

        game.setFirstTile(3, 3);
        game.setSecondTile(5, 5);

        assertTrue(game.playTurn());
        assertEquals(game.getBoardStatesLength(), 3);
        assertEquals(game.getMoves(), 1);

    }

    @Test
    public void testPlayTurnNullTiles() {
        ILoveHue game = new ILoveHue();
        game.randomizeBoard();

        assertFalse(game.playTurn());
        assertEquals(game.getBoardStatesLength(), 2);
        assertEquals(game.getMoves(), 0);

        game.setFirstTile(3, 3);

        assertFalse(game.playTurn());
        assertEquals(game.getBoardStatesLength(), 2);
        assertEquals(game.getMoves(), 0);

    }

    @Test
    public void testSameTile() {
        ILoveHue game = new ILoveHue();
        game.randomizeBoard();

        game.setFirstTile(3, 3);
        game.setSecondTile(3, 3);

        assertFalse(game.playTurn());
        assertEquals(game.getBoardStatesLength(), 2);
        assertEquals(game.getMoves(), 0);

    }

    @Test
    public void testSameRowOrCol() {
        ILoveHue game = new ILoveHue();
        game.randomizeBoard();

        game.setFirstTile(3, 2);
        game.setSecondTile(3, 3);

        assertTrue(game.playTurn());
        assertEquals(game.getBoardStatesLength(), 3);
        assertEquals(game.getMoves(), 1);

        game.setFirstTile(3, 3);
        game.setSecondTile(2, 3);

        assertTrue(game.playTurn());
        assertEquals(game.getBoardStatesLength(), 4);
        assertEquals(game.getMoves(), 2);

    }

    /** NEW GAME TEST */
    @Test
    public void testNewGame() {
        ILoveHue game = new ILoveHue();
        game.randomizeBoard();

        game.setFirstTile(5, 5);
        game.setSecondTile(3, 3);

        assertNotNull(game.getFirstTile());
        assertNotNull(game.getSecondTile());

        game.newGame();

        assertNull(game.getFirstTile());
        assertNull(game.getSecondTile());

    }

    /** SET TILES TEST */
    @Test
    public void testSetTiles() {
        ILoveHue game = new ILoveHue();
        game.randomizeBoard();

        Tile t1 = game.getTile(5, 5);
        Tile t2 = game.getTile(3, 3);

        game.setFirstTile(5, 5);
        game.setSecondTile(3, 3);

        assertEquals(game.getFirstTile(), t1);
        assertEquals(game.getSecondTile(), t2);

    }

    @Test
    public void testSetCornerTile() {
        ILoveHue game = new ILoveHue();
        game.randomizeBoard();

        game.setFirstTile(0, 0);
        game.setSecondTile(7, 0);

        assertNull(game.getFirstTile());
        assertNull(game.getSecondTile());
    }

}

package org.cis120.ilovehuetest;

import org.cis120.ilovehue.DrawBoard;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;

public class DrawBoardTest {

    @Test
    public void testStartGame() {
        JLabel label = new JLabel();
        DrawBoard b = new DrawBoard(label);

        assertTrue(b.start());
        assertFalse(b.start());

    }

}

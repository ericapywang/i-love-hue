package org.cis120.ilovehue;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RunGame implements Runnable {

    public void run() {
        // top level frame
        final JFrame frame = new JFrame("I Love Hue");
        frame.setLocation(300, 300);

        // status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Click start to randomize board!");
        status_panel.add(status);

        // game board
        final DrawBoard board = new DrawBoard(status);
        frame.add(board, BorderLayout.CENTER);

        /** BUTTONS */
        // control panel
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // undo button
        final JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.undo();
            }
        });

        // new game button
        final JButton newGame = new JButton("New Board");
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.newGame();
            }
        });

        // start button
        final JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.start();
            }
        });

        // load previous game button
        final JButton loadGame = new JButton("Load Previous");
        loadGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.loadGame();
            }
        });

        // save button
        final JButton saveGame = new JButton("Save and Exit");
        saveGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (board.getTilesSwapped().size() > 0 && !board.checkBoard()) {

                    FileReader fileReader = new FileReader();

                    // fileReader
                    fileReader.writeOut(board.getOriginalRGB(), board.getScrambledRGB(),
                            board.getTilesSwapped(), "files/game_state.csv");
                    board.loadGame();
                    fileReader.writeOut(board.getOriginalRGB(), board.getScrambledRGB(),
                            board.getTilesSwapped(), "files/game_state.csv");
                    System.exit(0);

                } else {
                    final JFrame message = new JFrame();
                    JOptionPane.showMessageDialog(message,
                            "Cannot save game with 0 moves!");
                }
            }
        });

        // load instructions button
        final JButton instructions = new JButton("Instructions");
        final JFrame instructionsWindow = new JFrame("Instructions");
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(instructionsWindow,
                        "Welcome to I Love Hue!\n\nTo play the game, once the board is " +
                                "randomized, click two tiles separately to switch their " +
                                "positions. You cannot move the corner tiles. Continue doing so " +
                                "until the tiles are all put back in the original gradient order!" +
                                "\n\nClicking the 'undo' button will undo your last move. You " +
                                "have unlimited undos.\n\nClicking the 'New Board' button will " +
                                "generate a newly randomized color gradient. Make sure to click " +
                                "'start' to randomize the board before you start!\n\nThe 'Save' " +
                                "button will save your current game and progress (as long as you " +
                                "have made a move) and exit the window, and then clicking the " +
                                "'Load Previous' button will load in the last game that was " +
                                "saved, as well as your progress.");
            }
        });

        control_panel.add(undo);
        control_panel.add(newGame);
        control_panel.add(start);
        control_panel.add(loadGame);
        control_panel.add(saveGame);
        status_panel.add(instructions);

        // Put the frame on the screen
        frame.setSize(new Dimension(500,500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

}

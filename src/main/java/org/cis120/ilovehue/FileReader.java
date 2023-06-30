package org.cis120.ilovehue;

import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class FileReader {

    public FileReader() {}

    // read the file from the csv
    public List<String> readIn(BufferedReader br) {

        String line;
        List<String> lines = new LinkedList<>();

        while (true) {
            try {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                lines.add(line);
            } catch (IOException e) {
                final JFrame exception = new JFrame("ERROR MESSAGE");
                JOptionPane.showMessageDialog(exception, "ERROR: IOException occurred");
            }
        }

        return lines;

    }

    // write the file to the csv
    public void writeOut(LinkedList<String> originalBoard, LinkedList<String> scrambledBoard,
                         LinkedList<Tile[]> tilesSwapped, String filePath) {
        try {
            File file = Paths.get(filePath).toFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            // write out original board
            List<String> toWrite = new LinkedList<>();
            toWrite.addAll(originalBoard);
            toWrite.addAll(scrambledBoard);

            // write out moves
            for (Tile[] tiles : tilesSwapped) {
                toWrite.add(tiles[0].getRow() + " " + tiles[0].getCol() + " " + tiles[1].getRow() +
                        " " + tiles[1].getCol());
            }

            for (String line : toWrite) {
                bw.write(line);
                bw.newLine();
            }

            bw.close();

        } catch (IOException e) {
            final JFrame exception = new JFrame("ERROR MESSAGE");
            JOptionPane.showMessageDialog(exception, "ERROR: IOException occurred");
        }
    }
}

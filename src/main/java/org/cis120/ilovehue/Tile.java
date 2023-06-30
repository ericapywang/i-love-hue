package org.cis120.ilovehue;

import java.awt.*;

public class Tile {

    private final int r;
    private final int g;
    private final int b;
    private int row;
    private int col;

    public Tile(int r, int g, int b, int row, int col) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.row = row;
        this.col = col;
    }

    public int getR() {
        return r;
    }
    public int getG() {
        return g;
    }
    public int getB() {
        return b;
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }
    public void setCol(int col) {
        this.col = col;
    }

    // each tile is 20x20 pixels
    public void draw(Graphics gc) {
        gc.setColor(new Color(this.r, this.g, this.b));
        gc.fillRect(row * 40, col * 40, 40, 40);

        gc.setColor(Color.BLACK);
        gc.fillOval(15, 15, 10, 10);
        gc.fillOval(295, 15, 10, 10);
        gc.fillOval(15, 295, 10, 10);
        gc.fillOval(295, 295, 10, 10);
    }

}

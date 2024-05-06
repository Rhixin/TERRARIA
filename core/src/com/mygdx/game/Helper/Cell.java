package com.mygdx.game.Helper;

public class Cell {
    public int row;
    public int column;

    public Cell(int x , int y){
        row= x;
        column = y;
    }

    public int compareTo(Cell otherCell) {
        // Compare the row values first
        if (this.row != otherCell.row) {
            return Integer.compare(this.row, otherCell.row);
        } else {
            // If row values are the same, compare the column values
            return Integer.compare(this.column, otherCell.column);
        }
    }

}

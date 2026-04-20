package com.sudoku.dao;

//This class represents single cell in Sudoku grid. Based on game rules, a cell is empty or pre-filled. 

public class SudokuCell {
    private Integer value;
    private final boolean preFilled;
    private final int row;
    private final int column;

    public SudokuCell(int row, int column, Integer value, boolean preFilled) {
        this.row = row;
        this.column = column;
        this.value = value;
        this.preFilled = preFilled;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        if (preFilled) {
            throw new IllegalStateException("Cannot modify a pre-filled cell");
        } else if(value != null && (value < 1 || value > 9)) {
            throw new IllegalArgumentException("Value must be between 1 and 9");
        }
        this.value = value;
    }

    public boolean isEmpty() {
        return value == null;
    }

    public boolean isPreFilled() {
        return preFilled;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void clear() {
        if (preFilled) {
            throw new IllegalStateException("Cannot clear a pre-filled cell");
        }
        this.value = null;
    }

    public SudokuCell copy() {
        return new SudokuCell(row, column, value, preFilled);
    }
}

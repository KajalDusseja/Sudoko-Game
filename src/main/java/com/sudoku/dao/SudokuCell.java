package com.sudoku.dao;

//This class represents single cell in Sudoku grid. Based on game rules, a cell is empty or pre-filled. 

public class SudokuCell {
    private static final int MIN_INDEX = 0;
    private static final int MAX_INDEX = 8;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 9;

    private Integer value;
    private final boolean preFilled;
    private final int row;
    private final int column;

    public SudokuCell(int row, int column, Integer value, boolean preFilled) {
        validatePosition(row, column);
        validateValue(value);
        if (preFilled && value == null) {
            throw new IllegalArgumentException("Pre-filled cells must have a value");
        }
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
        }
        validateValue(value);
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

    private void validatePosition(int row, int column) {
        if (row < MIN_INDEX || row > MAX_INDEX) {
            throw new IllegalArgumentException("Row must be between 0 and 8");
        }
        if (column < MIN_INDEX || column > MAX_INDEX) {
            throw new IllegalArgumentException("Column must be between 0 and 8");
        }
    }

    private void validateValue(Integer value) {
        if (value != null && (value < MIN_VALUE || value > MAX_VALUE)) {
            throw new IllegalArgumentException("Value must be between 1 and 9");
        }
    }
}

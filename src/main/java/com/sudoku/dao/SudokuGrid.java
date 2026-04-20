package com.sudoku.dao;

//This class represents the complete grid with cell, row, column and subgrid access methods. 

public class SudokuGrid {
    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;

    private final SudokuCell[][] grid;

    public SudokuGrid() {
        this.grid = new SudokuCell[SIZE][SIZE];
        initializeGrid();
    }

    private void initializeGrid() {
        // Create empty cells for the initial puzzle state
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new SudokuCell(i, j, null, false);
            }
        }
    }

    public SudokuCell getCell(int row, int column) {
        validateIndices(row, column);
        return grid[row][column];
    }

    // Note: We replace the entire cell rather than modifying it to ensure
    // pre-filled status is correctly reflected in the new cell
    public void setCell(int row, int column, int value) {
        validateIndices(row, column);
        validateValue(value);
        grid[row][column] = new SudokuCell(row, column, value, false);
    }

    public void setPreFilledCell(int row, int column, int value) {
        validateIndices(row, column);
        validateValue(value);
        grid[row][column] = new SudokuCell(row, column, value, true);
    }

    public SudokuCell[] getRow(int row) {
        validateRowIndex(row);
        return grid[row];
    }

    public SudokuCell[] getColumn(int column) {
        validateColumnIndex(column);
        SudokuCell[] columnCells = new SudokuCell[SIZE];
        for (int i = 0; i < SIZE; i++) {
            columnCells[i] = grid[i][column];
        }
        return columnCells;
    }

    public SudokuCell[] getSubgrid(int row, int column) {
        validateIndices(row, column);
        int subgridRow = (row / SUBGRID_SIZE) * SUBGRID_SIZE;
        int subgridCol = (column / SUBGRID_SIZE) * SUBGRID_SIZE;

        SudokuCell[] subgridCells = new SudokuCell[SIZE];
        int index = 0;
        for (int i = subgridRow; i < subgridRow + SUBGRID_SIZE; i++) {
            for (int j = subgridCol; j < subgridCol + SUBGRID_SIZE; j++) {
                subgridCells[index++] = grid[i][j];
            }
        }
        return subgridCells;
    }

    public boolean isFilled() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public SudokuGrid copy() {
        SudokuGrid copy = new SudokuGrid();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                SudokuCell original = grid[i][j];
                copy.grid[i][j] = original.copy();
            }
        }
        return copy;
    }

    public static int getSize() {
        return SIZE;
    }

    private void validateIndices(int row, int column) {
        validateRowIndex(row);
        validateColumnIndex(column);
    }

    private void validateRowIndex(int row) {
        if (row < 0 || row >= SIZE) {
            throw new IllegalArgumentException("Row must be between 0 and 8");
        }
    }

    private void validateColumnIndex(int column) {
        if (column < 0 || column >= SIZE) {
            throw new IllegalArgumentException("Column must be between 0 and 8");
        }
    }

    private void validateValue(int value) {
        if (value < 1 || value > 9) {
            throw new IllegalArgumentException("Value must be between 1 and 9");
        }
    }
}

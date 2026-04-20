package com.sudoku.service;

import com.sudoku.dao.SudokuGrid;

//This class generates the solution and verifies the correctness of moves. 

public class SudokuSolver {
    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;

    public boolean solve(SudokuGrid grid) {
        // Standard backtracking: find empty cell, and try 1-9 recursively
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (grid.getCell(row, col).isEmpty()) {
                    // Try each number 1-9 in this cell
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(grid, row, col, num)) {
                            grid.getCell(row, col).setValue(num);

                            // Recursively try to solve the rest
                            if (solve(grid)) {
                                return true;
                            }

                            // Backtrack if this path didn't work
                            grid.getCell(row, col).clear();
                        }
                    }
                    return false; // No valid number found for this cell
                }
            }
        }
        return true; // All cells filled successfully
    }

    public int findCorrectValue(SudokuGrid grid, int row, int col) {
        // For hint generation: try each value 1-9 and see which one leads to a valid solution
        if (!grid.getCell(row, col).isEmpty()) {
            return -1; // Cell is already filled
        }

        // Test each candidate value
        for (int num = 1; num <= 9; num++) {
            SudokuGrid solverGrid = grid.copy();
            
            if (isValid(solverGrid, row, col, num)) {
                solverGrid.getCell(row, col).setValue(num);

                // Check if this choice leads to a solvable puzzle
                if (solve(solverGrid)) {
                    return num; // Found the correct value for this cell
                }
            }
        }
        return -1; // No valid value found (shouldn't happen in valid puzzles)
    }

    private boolean isValid(SudokuGrid grid, int row, int col, int num) {
        // Check if number already exists in this row
        for (int j = 0; j < SIZE; j++) {
            if (grid.getCell(row, j).getValue() != null &&
                    grid.getCell(row, j).getValue() == num) {
                return false;
            }
        }

        // Check if number already exists in this column
        for (int i = 0; i < SIZE; i++) {
            if (grid.getCell(i, col).getValue() != null &&
                    grid.getCell(i, col).getValue() == num) {
                return false;
            }
        }

        // Check if number already exists in the 3x3 subgrid
        int subgridRow = (row / SUBGRID_SIZE) * SUBGRID_SIZE;
        int subgridCol = (col / SUBGRID_SIZE) * SUBGRID_SIZE;

        for (int i = subgridRow; i < subgridRow + SUBGRID_SIZE; i++) {
            for (int j = subgridCol; j < subgridCol + SUBGRID_SIZE; j++) {
                if (grid.getCell(i, j).getValue() != null &&
                        grid.getCell(i, j).getValue() == num) {
                    return false;
                }
            }
        }

        return true; // All checks passed, number is valid here
    }
}

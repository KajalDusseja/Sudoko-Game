package com.sudoku.service;

import com.sudoku.dao.SudokuGrid;

//This class creates the pre-filled puzzle as per the example. 
// Currently, it creates the same puzzle everytime but can be extended to random different sets 

public class PuzzleGenerator {

    public SudokuGrid generatePuzzle() {
        SudokuGrid grid = new SudokuGrid();

        int[][] puzzle = {
                {5, 3, 0, 0, 7, 0, 0, 0, 0},
                {6, 0, 0, 1, 9, 5, 0, 0, 0},
                {0, 9, 8, 0, 0, 0, 0, 6, 0},
                {8, 0, 0, 0, 6, 0, 0, 0, 3},
                {4, 0, 0, 8, 0, 3, 0, 0, 1},
                {7, 0, 0, 0, 2, 0, 0, 0, 6},
                {0, 6, 0, 0, 0, 0, 2, 8, 0},
                {0, 0, 0, 4, 1, 9, 0, 0, 5},
                {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        // Fill the grid with pre-filled cells (locked from player modification)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (puzzle[row][col] != 0) {
                    grid.setPreFilledCell(row, col, puzzle[row][col]);
                }
            }
        }

        return grid;
    }
}

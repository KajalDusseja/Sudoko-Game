package com.sudoku.service;

import java.util.List;
import java.util.Random;

import com.sudoku.dao.SudokuCell;
import com.sudoku.dao.SudokuGrid;
import com.sudoku.dao.SudokuValidator;
import com.sudoku.dao.ValidationError;

//This class is the main service which handles all the game logic. 

public class SudokuGameController {
    private SudokuGrid grid;
    private final SudokuValidator validator;
    private final SudokuSolver solver;

    public SudokuGameController() {
        this.validator = new SudokuValidator();
        this.solver = new SudokuSolver();
    }

    public void startNewGame() {
        PuzzleGenerator generator = new PuzzleGenerator();
        this.grid = generator.generatePuzzle();
    }

    public String placeNumber(int row, int column, int value) {
        SudokuCell cell = grid.getCell(row, column);

        // Prevent modifying pre-filled cells
        if (cell.isPreFilled()) {
            return String.format("Invalid move. %s is pre-filled.", formatCellRef(row, column));
        }

        // Validate input range
        if (value < 1 || value > 9) {
            return "Number must be between 1 and 9.";
        }

        cell.setValue(value);
        return null; // null indicates success
    }

    public String clearCell(int row, int column) {
        SudokuCell cell = grid.getCell(row, column);

        if (cell.isPreFilled()) {
            return String.format("Invalid move. %s is pre-filled.", formatCellRef(row, column));
        }

        cell.clear();
        return null;
    }

    public String getHint() {
        // Find all empty cells
        int[] emptyCells = findEmptyCells();

        if (emptyCells == null || emptyCells.length == 0) {
            return "No empty cells to hint.";
        }

        // Pick a random empty cell (use first half for variety)
        Random random = new Random();
        int cellCount = emptyCells.length / 2; // Each cell is represented by 2 integers (row, col)
        int randomIndex = random.nextInt(cellCount);
        int row = emptyCells[randomIndex * 2];
        int col = emptyCells[randomIndex * 2 + 1];

        // Find the correct value using backtracking solver
        int correctValue = solver.findCorrectValue(grid, row, col);

        if (correctValue != -1) {
            grid.getCell(row, col).setValue(correctValue);
            return String.format("Hint: Cell %s = %d", formatCellRef(row, col), correctValue);
        }

        return "Unable to generate hint.";
    }

    public String checkGrid() {
        List<ValidationError> errors = validator.validate(grid);

        if (errors.isEmpty()) {
            return "No rule violations detected.";
        }

        return errors.get(0).getMessage();
    }

    public boolean isSolved() {
        // Grid must be fully filled
        if (!grid.isFilled()) {
            return false;
        }

        List<ValidationError> errors = validator.validate(grid);
        return errors.isEmpty();
    }

    public SudokuGrid getGrid() {
        return grid;
    }

    private int[] findEmptyCells() {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid.getCell(i, j).isEmpty()) {
                    count++;
                }
            }
        }

        if (count == 0) {
            return null;
        }

        int[] cells = new int[count * 2];
        int index = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid.getCell(i, j).isEmpty()) {
                    cells[index++] = i;
                    cells[index++] = j;
                }
            }
        }
        return cells;
    }

    private String formatCellRef(int row, int column) {
        return String.valueOf((char) ('A' + row)) + (column + 1);
    }
}

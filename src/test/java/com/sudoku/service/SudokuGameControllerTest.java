package com.sudoku.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sudoku.dao.SudokuGrid;
import com.sudoku.service.SudokuGameController;

import static org.assertj.core.api.Assertions.*;

public class SudokuGameControllerTest {
    private SudokuGameController controller;

    @BeforeEach
    public void setup() {
        controller = new SudokuGameController();
        controller.startNewGame();
    }

    @Test
    public void testGameInitialization() {
        SudokuGrid grid = controller.getGrid();
        assertThat(grid).isNotNull();

        // Should have 30 pre-filled cells
        int prefilledCount = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid.getCell(i, j).isPreFilled()) {
                    prefilledCount++;
                }
            }
        }
        assertThat(prefilledCount).isEqualTo(30);
    }

    @Test
    public void testPlaceNumberInEmptyCell() {
        String error = controller.placeNumber(0, 3, 5);
        assertThat(error).isNull();
        assertThat(controller.getGrid().getCell(0, 3).getValue()).isEqualTo(5);
    }

    @Test
    public void testCannotPlaceInPrefilledCell() {
        SudokuGrid grid = controller.getGrid();
        // Find a prefilled cell
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid.getCell(i, j).isPreFilled()) {
                    String error = controller.placeNumber(i, j, 5);
                    assertThat(error).isNotNull().contains("pre-filled");
                    return;
                }
            }
        }
    }

    @Test
    public void testPlaceInvalidValue() {
        String error1 = controller.placeNumber(0, 3, 0);
        String error2 = controller.placeNumber(0, 3, 10);

        assertThat(error1).isNotNull().contains("between 1 and 9");
        assertThat(error2).isNotNull().contains("between 1 and 9");
    }

    @Test
    public void testClearCell() {
        controller.placeNumber(0, 3, 5);
        String error = controller.clearCell(0, 3);
        assertThat(error).isNull();
        assertThat(controller.getGrid().getCell(0, 3).isEmpty()).isTrue();
    }

    @Test
    public void testCannotClearPrefilledCell() {
        SudokuGrid grid = controller.getGrid();
        // Find a prefilled cell
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid.getCell(i, j).isPreFilled()) {
                    String error = controller.clearCell(i, j);
                    assertThat(error).isNotNull().contains("pre-filled");
                    return;
                }
            }
        }
    }

    @Test
    public void testCheckGridWithViolations() {
        controller.placeNumber(0, 3, 5);
        controller.placeNumber(0, 4, 5);  // Duplicate in row

        String result = controller.checkGrid();
        assertThat(result).contains("already exists");
    }

    @Test
    public void testCheckGridWithoutViolations() {
        // Clear the entire grid and add just one valid value
        SudokuGrid grid = controller.getGrid();
        
        // Clear all non-prefilled cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!grid.getCell(i, j).isPreFilled()) {
                    grid.getCell(i, j).clear();
                }
            }
        }
        
        // Place one number at an empty position - if this fails, the puzzle is invalid
        // Find any empty cell and place a value
        boolean placed = false;
        for (int i = 0; i < 9 && !placed; i++) {
            for (int j = 0; j < 9 && !placed; j++) {
                if (grid.getCell(i, j).isEmpty()) {
                    String error = controller.placeNumber(i, j, 1);
                    if (error == null) {
                        placed = true;
                    }
                }
            }
        }
        
        assertThat(placed).as("Should be able to place a number").isTrue();
        String result = controller.checkGrid();
        assertThat(result).contains("No rule violations detected");
    }

    @Test
    public void testHintRevealsCellAndIsValid() {
        String hint = controller.getHint();
        assertThat(hint).contains("Hint:").contains("=");

        // Check that a cell was revealed
        boolean cellRevealed = false;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!controller.getGrid().getCell(i, j).isEmpty() &&
                        !controller.getGrid().getCell(i, j).isPreFilled()) {
                    cellRevealed = true;
                    break;
                }
            }
        }
        assertThat(cellRevealed).isTrue();
    }

    @Test
    public void testHintWorksWhenOnlyOneCellIsEmpty() {
        int[][] solution = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        SudokuGrid grid = controller.getGrid();
        int emptyRow = 0;
        int emptyColumn = 2;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!grid.getCell(i, j).isPreFilled() && !(i == emptyRow && j == emptyColumn)) {
                    grid.getCell(i, j).setValue(solution[i][j]);
                }
            }
        }

        String hint = controller.getHint();

        assertThat(hint).isEqualTo("Hint: Cell A3 = 4");
        assertThat(grid.getCell(emptyRow, emptyColumn).getValue()).isEqualTo(4);
        assertThat(controller.isSolved()).isTrue();
    }

    @Test
    public void testSolvedPuzzleDetection() {
        // Place all valid numbers
        int[][] solution = {
                {5, 3, 4, 6, 7, 8, 9, 1, 2},
                {6, 7, 2, 1, 9, 5, 3, 4, 8},
                {1, 9, 8, 3, 4, 2, 5, 6, 7},
                {8, 5, 9, 7, 6, 1, 4, 2, 3},
                {4, 2, 6, 8, 5, 3, 7, 9, 1},
                {7, 1, 3, 9, 2, 4, 8, 5, 6},
                {9, 6, 1, 5, 3, 7, 2, 8, 4},
                {2, 8, 7, 4, 1, 9, 6, 3, 5},
                {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        // Start fresh
        controller.startNewGame();
        SudokuGrid grid = controller.getGrid();

        // Clear all non-prefilled cells
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!grid.getCell(i, j).isPreFilled() && !grid.getCell(i, j).isEmpty()) {
                    grid.getCell(i, j).clear();
                }
            }
        }

        // Fill with solution
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!grid.getCell(i, j).isPreFilled()) {
                    grid.getCell(i, j).setValue(solution[i][j]);
                }
            }
        }

        assertThat(controller.isSolved()).isTrue();
    }

    @Test
    public void testUnsolvedPuzzleDetection() {
        assertThat(controller.isSolved()).isFalse();
    }
}

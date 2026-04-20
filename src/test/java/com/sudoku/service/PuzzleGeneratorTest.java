package com.sudoku.service;

import org.junit.jupiter.api.Test;

import com.sudoku.dao.SudokuGrid;
import com.sudoku.service.PuzzleGenerator;

import static org.assertj.core.api.Assertions.*;

public class PuzzleGeneratorTest {

    @Test
    public void testPuzzleGeneration() {
        PuzzleGenerator generator = new PuzzleGenerator();
        SudokuGrid grid = generator.generatePuzzle();

        assertThat(grid).isNotNull();

        // Check that exactly 30 cells are pre-filled
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
    public void testPrefilledCellsAreValid() {
        PuzzleGenerator generator = new PuzzleGenerator();
        SudokuGrid grid = generator.generatePuzzle();

        // All prefilled cells should have values 1-9
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid.getCell(i, j).isPreFilled()) {
                    Integer value = grid.getCell(i, j).getValue();
                    assertThat(value).isNotNull().isBetween(1, 9);
                }
            }
        }
    }

    @Test
    public void testEmptyPuzzleCellsExist() {
        PuzzleGenerator generator = new PuzzleGenerator();
        SudokuGrid grid = generator.generatePuzzle();

        // Should have 51 empty cells (81 - 30)
        int emptyCount = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid.getCell(i, j).isEmpty()) {
                    emptyCount++;
                }
            }
        }
        assertThat(emptyCount).isEqualTo(51);
    }

    @Test
    public void testConsistentPuzzleGeneration() {
        PuzzleGenerator generator = new PuzzleGenerator();
        SudokuGrid grid1 = generator.generatePuzzle();
        SudokuGrid grid2 = generator.generatePuzzle();

        // Both should have the same pattern (as we use a fixed puzzle)
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Integer val1 = grid1.getCell(i, j).getValue();
                Integer val2 = grid2.getCell(i, j).getValue();
                assertThat(val1).isEqualTo(val2);
            }
        }
    }
}

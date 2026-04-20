package com.sudoku.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sudoku.dao.SudokuValidator;
import com.sudoku.dao.SudokuGrid;
import com.sudoku.dao.ValidationError;

import java.util.List;
import static org.assertj.core.api.Assertions.*;

public class SudokuValidatorTest {
    private SudokuValidator validator;
    private SudokuGrid grid;

    @BeforeEach
    public void setup() {
        validator = new SudokuValidator();
        grid = new SudokuGrid();
    }

    @Test
    public void testEmptyGridIsValid() {
        List<ValidationError> errors = validator.validate(grid);
        assertThat(errors).isEmpty();
    }

    @Test
    public void testDuplicateInRow() {
        grid.setCell(0, 0, 5);
        grid.setCell(0, 5, 5);

        List<ValidationError> errors = validator.validate(grid);
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getMessage()).contains("Row A").contains("5");
    }

    @Test
    public void testDuplicateInColumn() {
        grid.setCell(0, 0, 5);
        grid.setCell(5, 0, 5);

        List<ValidationError> errors = validator.validate(grid);
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getMessage()).contains("Column 1").contains("5");
    }

    @Test
    public void testDuplicateInSubgrid() {
        grid.setCell(0, 0, 5);
        grid.setCell(1, 1, 5);

        List<ValidationError> errors = validator.validate(grid);
        assertThat(errors).hasSize(1);
        assertThat(errors.get(0).getMessage()).contains("3×3 subgrid").contains("5");
    }

    @Test
    public void testValidPlacement() {
        grid.setCell(0, 0, 5);
        grid.setCell(0, 3, 6);
        grid.setCell(3, 0, 7);

        List<ValidationError> errors = validator.validate(grid);
        assertThat(errors).isEmpty();
    }

    @Test
    public void testMultipleErrors() {
        // Fill an entire row with the same number
        for (int i = 0; i < 9; i++) {
            grid.setCell(0, i, 5);
        }

        List<ValidationError> errors = validator.validate(grid);
        assertThat(errors).isNotEmpty();
    }

    @Test
    public void testValidCompletedPuzzle() {
        // Fill a valid completed puzzle
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

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid.setCell(i, j, solution[i][j]);
            }
        }

        List<ValidationError> errors = validator.validate(grid);
        assertThat(errors).isEmpty();
    }
}

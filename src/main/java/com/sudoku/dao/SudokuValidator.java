package com.sudoku.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// This class validates the grid as per rules. Checks for duplicates in rows, columns and subgrid. 

public class SudokuValidator {

    public List<ValidationError> validate(SudokuGrid grid) {
        List<ValidationError> errors = new ArrayList<>();

        for (int i = 0; i < SudokuGrid.getSize(); i++) {
            errors.addAll(validateRow(grid, i));
        }

        for (int i = 0; i < SudokuGrid.getSize(); i++) {
            errors.addAll(validateColumn(grid, i));
        }

        // Check all 3x3 subgrids
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                errors.addAll(validateSubgrid(grid, i * 3, j * 3));
            }
        }

        return errors;
    }

    private List<ValidationError> validateRow(SudokuGrid grid, int row) {
        List<ValidationError> errors = new ArrayList<>();
        SudokuCell[] rowCells = grid.getRow(row);
        Set<Integer> seen = new HashSet<>();

        for (SudokuCell cell : rowCells) {
            if (!cell.isEmpty()) {
                int value = cell.getValue();
                if (seen.contains(value)) {
                    // Return immediately to give user first error to fix
                    errors.add(new ValidationError(
                            String.format("Number %d already exists in Row %s.", value, getRowLabel(row))
                    ));
                    return errors;
                }
                seen.add(value);
            }
        }
        return errors;
    }

    private List<ValidationError> validateColumn(SudokuGrid grid, int column) {
        List<ValidationError> errors = new ArrayList<>();
        SudokuCell[] columnCells = grid.getColumn(column);
        Set<Integer> seen = new HashSet<>();

        for (SudokuCell cell : columnCells) {
            if (!cell.isEmpty()) {
                int value = cell.getValue();
                if (seen.contains(value)) {
                    errors.add(new ValidationError(
                            String.format("Number %d already exists in Column %d.", value, column + 1)
                    ));
                    return errors;
                }
                seen.add(value);
            }
        }
        return errors;
    }

    private List<ValidationError> validateSubgrid(SudokuGrid grid, int row, int column) {
        List<ValidationError> errors = new ArrayList<>();
        SudokuCell[] subgridCells = grid.getSubgrid(row, column);
        Set<Integer> seen = new HashSet<>();

        for (SudokuCell cell : subgridCells) {
            if (!cell.isEmpty()) {
                int value = cell.getValue();
                if (seen.contains(value)) {
                    errors.add(new ValidationError(
                            "Number " + value + " already exists in the same 3×3 subgrid."
                    ));
                    return errors;
                }
                seen.add(value);
            }
        }
        return errors;
    }

    private String getRowLabel(int row) {
        return String.valueOf((char) ('A' + row));
    }
}

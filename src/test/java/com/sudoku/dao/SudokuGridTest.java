package com.sudoku.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sudoku.dao.SudokuCell;
import com.sudoku.dao.SudokuGrid;

import static org.assertj.core.api.Assertions.*;

public class SudokuGridTest {
    private SudokuGrid grid;

    @BeforeEach
    public void setup() {
        grid = new SudokuGrid();
    }

    @Test
    public void testGridInitialization() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                SudokuCell cell = grid.getCell(i, j);
                assertThat(cell).isNotNull();
                assertThat(cell.isEmpty()).isTrue();
                assertThat(cell.isPreFilled()).isFalse();
            }
        }
    }

    @Test
    public void testSetCell() {
        grid.setCell(0, 0, 5);
        SudokuCell cell = grid.getCell(0, 0);
        assertThat(cell.getValue()).isEqualTo(5);
        assertThat(cell.isPreFilled()).isFalse();
    }

    @Test
    public void testSetPreFilledCell() {
        grid.setPreFilledCell(0, 0, 5);
        SudokuCell cell = grid.getCell(0, 0);
        assertThat(cell.getValue()).isEqualTo(5);
        assertThat(cell.isPreFilled()).isTrue();
    }

    @Test
    public void testGetRow() {
        grid.setCell(0, 0, 1);
        grid.setCell(0, 1, 2);
        grid.setCell(0, 2, 3);

        SudokuCell[] row = grid.getRow(0);
        assertThat(row).hasSize(9);
        assertThat(row[0].getValue()).isEqualTo(1);
        assertThat(row[1].getValue()).isEqualTo(2);
        assertThat(row[2].getValue()).isEqualTo(3);
    }

    @Test
    public void testGetColumn() {
        grid.setCell(0, 0, 1);
        grid.setCell(1, 0, 2);
        grid.setCell(2, 0, 3);

        SudokuCell[] column = grid.getColumn(0);
        assertThat(column).hasSize(9);
        assertThat(column[0].getValue()).isEqualTo(1);
        assertThat(column[1].getValue()).isEqualTo(2);
        assertThat(column[2].getValue()).isEqualTo(3);
    }

    @Test
    public void testGetSubgrid() {
        // Fill top-left 3x3 subgrid
        grid.setCell(0, 0, 1);
        grid.setCell(0, 1, 2);
        grid.setCell(1, 1, 3);

        SudokuCell[] subgrid = grid.getSubgrid(1, 1);
        assertThat(subgrid).hasSize(9);
        assertThat(subgrid[0].getValue()).isEqualTo(1);
        assertThat(subgrid[1].getValue()).isEqualTo(2);
        assertThat(subgrid[4].getValue()).isEqualTo(3);
    }

    @Test
    public void testIsFilled() {
        assertThat(grid.isFilled()).isFalse();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid.setCell(i, j, (i * 9 + j) % 9 + 1);
            }
        }

        assertThat(grid.isFilled()).isTrue();
    }

    @Test
    public void testInvalidValue() {
        assertThatThrownBy(() -> grid.setCell(0, 0, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Value must be between 1 and 9");

        assertThatThrownBy(() -> grid.setCell(0, 0, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Value must be between 1 and 9");
    }

    @Test
    public void testInvalidIndices() {
        assertThatThrownBy(() -> grid.getCell(-1, 0))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> grid.getCell(0, 9))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> grid.getCell(9, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testCopyGrid() {
        grid.setCell(0, 0, 5);
        grid.setPreFilledCell(1, 1, 7);

        SudokuGrid copy = grid.copy();
        assertThat(copy.getCell(0, 0).getValue()).isEqualTo(5);
        assertThat(copy.getCell(1, 1).getValue()).isEqualTo(7);
        assertThat(copy.getCell(1, 1).isPreFilled()).isTrue();

        copy.getCell(0, 0).setValue(9);
        assertThat(grid.getCell(0, 0).getValue()).isEqualTo(5);
    }
}

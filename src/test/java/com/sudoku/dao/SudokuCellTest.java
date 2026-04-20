package com.sudoku.dao;

import org.junit.jupiter.api.Test;

import com.sudoku.dao.SudokuCell;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for SudokuCell class.
 * Covers creation, modification, and pre-filled cell protection.
 */
public class SudokuCellTest {

    @Test
    public void testCreateEmptyCell() {
        SudokuCell cell = new SudokuCell(0, 0, null, false);
        assertThat(cell.isEmpty()).isTrue();
        assertThat(cell.getValue()).isNull();
        assertThat(cell.isPreFilled()).isFalse();
    }

    @Test
    public void testCreateFilledCell() {
        SudokuCell cell = new SudokuCell(0, 0, 5, false);
        assertThat(cell.isEmpty()).isFalse();
        assertThat(cell.getValue()).isEqualTo(5);
    }

    @Test
    public void testCreatePreFilledCell() {
        // Pre-filled cells are the original puzzle clues
        SudokuCell cell = new SudokuCell(0, 0, 5, true);
        assertThat(cell.isPreFilled()).isTrue();
    }

    @Test
    public void testSetValue() {
        SudokuCell cell = new SudokuCell(0, 0, null, false);
        cell.setValue(7);
        assertThat(cell.getValue()).isEqualTo(7);
        assertThat(cell.isEmpty()).isFalse();
    }

    @Test
    public void testCannotModifyPreFilledCell() {
        // This is a key feature: pre-filled cells should be immutable
        // to prevent the player from accidentally breaking the puzzle
        SudokuCell cell = new SudokuCell(0, 0, 5, true);
        assertThatThrownBy(() -> cell.setValue(7))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot modify a pre-filled cell");
    }

    @Test
    public void testClearCell() {
        SudokuCell cell = new SudokuCell(0, 0, 5, false);
        cell.clear();
        assertThat(cell.isEmpty()).isTrue();
        assertThat(cell.getValue()).isNull();
    }

    @Test
    public void testCannotClearPreFilledCell() {
        SudokuCell cell = new SudokuCell(0, 0, 5, true);
        assertThatThrownBy(cell::clear)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot clear a pre-filled cell");
    }

    @Test
    public void testCellPosition() {
        SudokuCell cell = new SudokuCell(3, 4, null, false);
        assertThat(cell.getRow()).isEqualTo(3);
        assertThat(cell.getColumn()).isEqualTo(4);
    }

    @Test
    public void testCopying() {
        SudokuCell original = new SudokuCell(2, 3, 5, false);
        SudokuCell copy = original.copy();

        assertThat(copy.getValue()).isEqualTo(5);
        assertThat(copy.getRow()).isEqualTo(2);
        assertThat(copy.getColumn()).isEqualTo(3);
        assertThat(copy.isPreFilled()).isFalse();

        copy.setValue(8);
        assertThat(original.getValue()).isEqualTo(5);
    }
}

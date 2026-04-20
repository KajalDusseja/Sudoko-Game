package com.sudoku.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Tests for Command parser.
 * Validates parsing of user input into structured commands.
 */
public class CommandTest {

    @Test
    public void testPlaceCommand() {
        // "A1 5" should place value 5 at row A (0), column 1 (0-indexed)
        Command command = Command.parseInput("A1 5");
        assertThat(command.getType()).isEqualTo(Command.CommandType.PLACE);
        assertThat(command.getRow()).isEqualTo(0);
        assertThat(command.getColumn()).isEqualTo(0);
        assertThat(command.getValue()).isEqualTo(5);
    }

    @Test
    public void testClearCommand() {
        // "B3 clear" should clear the cell at row B (1), column 3 (2)
        Command command = Command.parseInput("B3 clear");
        assertThat(command.getType()).isEqualTo(Command.CommandType.CLEAR);
        assertThat(command.getRow()).isEqualTo(1);
        assertThat(command.getColumn()).isEqualTo(2);
    }

    @Test
    public void testHintCommand() {
        Command command = Command.parseInput("hint");
        assertThat(command.getType()).isEqualTo(Command.CommandType.HINT);
    }

    @Test
    public void testCheckCommand() {
        Command command = Command.parseInput("check");
        assertThat(command.getType()).isEqualTo(Command.CommandType.CHECK);
    }

    @Test
    public void testQuitCommand() {
        Command command = Command.parseInput("quit");
        assertThat(command.getType()).isEqualTo(Command.CommandType.QUIT);
    }

    @Test
    public void testCaseInsensitivity() {
        // Make sure players can type commands in any case
        Command command1 = Command.parseInput("HINT");
        Command command2 = Command.parseInput("Hint");
        Command command3 = Command.parseInput("hint");

        assertThat(command1.getType()).isEqualTo(Command.CommandType.HINT);
        assertThat(command2.getType()).isEqualTo(Command.CommandType.HINT);
        assertThat(command3.getType()).isEqualTo(Command.CommandType.HINT);
    }

    @Test
    public void testInvalidPlace() {
        // Value out of range (1-9 only)
        Command command = Command.parseInput("A1 10");
        assertThat(command.getType()).isEqualTo(Command.CommandType.INVALID);
    }

    @Test
    public void testInvalidRowLabel() {
        // Rows should be A-I only
        Command command = Command.parseInput("J1 5");
        assertThat(command.getType()).isEqualTo(Command.CommandType.INVALID);
    }

    @Test
    public void testInvalidColumn() {
        // Columns should be 1-9 only
        Command command = Command.parseInput("A10 5");
        assertThat(command.getType()).isEqualTo(Command.CommandType.INVALID);
    }

    @Test
    public void testEmptyInput() {
        // Empty or whitespace input should be invalid
        Command command = Command.parseInput("");
        assertThat(command.getType()).isEqualTo(Command.CommandType.INVALID);
    }

    @Test
    public void testNullInput() {
        Command command = Command.parseInput(null);
        assertThat(command.getType()).isEqualTo(Command.CommandType.INVALID);
    }

    @Test
    public void testAllRowLabels() {
        for (int i = 0; i < 9; i++) {
            char label = (char) ('A' + i);
            Command command = Command.parseInput(label + "1 5");
            assertThat(command.getType()).isEqualTo(Command.CommandType.PLACE);
            assertThat(command.getRow()).isEqualTo(i);
        }
    }

    @Test
    public void testAllColumns() {
        for (int i = 1; i <= 9; i++) {
            Command command = Command.parseInput("A" + i + " 5");
            assertThat(command.getType()).isEqualTo(Command.CommandType.PLACE);
            assertThat(command.getColumn()).isEqualTo(i - 1);
        }
    }

    @Test
    public void testAllValidValues() {
        for (int i = 1; i <= 9; i++) {
            Command command = Command.parseInput("A1 " + i);
            assertThat(command.getType()).isEqualTo(Command.CommandType.PLACE);
            assertThat(command.getValue()).isEqualTo(i);
        }
    }

    @Test
    public void testClearWithLowercaseAndUppercase() {
        Command command1 = Command.parseInput("A1 CLEAR");
        Command command2 = Command.parseInput("A1 clear");
        Command command3 = Command.parseInput("A1 Clear");

        assertThat(command1.getType()).isEqualTo(Command.CommandType.CLEAR);
        assertThat(command2.getType()).isEqualTo(Command.CommandType.CLEAR);
        assertThat(command3.getType()).isEqualTo(Command.CommandType.CLEAR);
    }
}

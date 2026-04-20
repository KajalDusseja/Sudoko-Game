package com.sudoku.service;

//This class is responsible for parsing command inputs given by user. As per the rules, it handles all commands. 

public class Command {
    public enum CommandType {
        PLACE, 
        CLEAR,
        HINT,
        CHECK,
        QUIT,
        INVALID
    }

    private final CommandType type;
    private final Integer row;
    private final Integer column;
    private final Integer value;
    private final String rawInput;

    private Command(CommandType type, Integer row, Integer column, Integer value, String rawInput) {
        this.type = type;
        this.row = row;
        this.column = column;
        this.value = value;
        this.rawInput = rawInput;
    }

    public static Command parseInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new Command(CommandType.INVALID, null, null, null, input);
        }

        String normalized = input.trim();

        // Check for special commands first (they're single words)
        if (normalized.equalsIgnoreCase("hint")) {
            return new Command(CommandType.HINT, null, null, null, input);
        }
        if (normalized.equalsIgnoreCase("check")) {
            return new Command(CommandType.CHECK, null, null, null, input);
        }
        if (normalized.equalsIgnoreCase("quit")) {
            return new Command(CommandType.QUIT, null, null, null, input);
        }

        String[] parts = normalized.split("\\s+");
        if (parts.length < 2 || parts[0].length() != 2) {
            return new Command(CommandType.INVALID, null, null, null, input);
        }

        String cellRef = parts[0];
        String operation = parts[1];

        char rowChar = cellRef.toUpperCase().charAt(0);
        if (rowChar < 'A' || rowChar > 'I') {
            return new Command(CommandType.INVALID, null, null, null, input);
        }
        int row = rowChar - 'A';

        int col;
        try {
            col = Integer.parseInt(cellRef.substring(1)) - 1;  // Convert to 0-indexed
            if (col < 0 || col > 8) {
                return new Command(CommandType.INVALID, null, null, null, input);
            }
        } catch (NumberFormatException e) {
            return new Command(CommandType.INVALID, null, null, null, input);
        }

        // Parse operation type
        if (operation.equalsIgnoreCase("clear")) {
            return new Command(CommandType.CLEAR, row, col, null, input);
        }

        try {
            int value = Integer.parseInt(operation);
            if (value < 1 || value > 9) {
                return new Command(CommandType.INVALID, null, null, null, input);
            }
            return new Command(CommandType.PLACE, row, col, value, input);
        } catch (NumberFormatException e) {
            return new Command(CommandType.INVALID, null, null, null, input);
        }
    }

    public CommandType getType() {
        return type;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }

    public Integer getValue() {
        return value;
    }

    public String getRawInput() {
        return rawInput;
    }
}

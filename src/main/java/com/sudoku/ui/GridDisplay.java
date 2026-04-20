package com.sudoku.ui;

import com.sudoku.dao.SudokuGrid;

//This class displays the grid in a user-friendly readable format. 

public class GridDisplay {

    public static String formatGrid(SudokuGrid grid) {
        StringBuilder sb = new StringBuilder();

        // Column headers (1-9)
        sb.append("    1 2 3 4 5 6 7 8 9\n");

        // Grid rows
        for (int i = 0; i < 9; i++) {
            // Add horizontal separator every 3 rows
            if (i > 0 && i % 3 == 0) {
                sb.append("  ").append("------+-------+------\n");
            }

            // Row label (A-I)
            char rowLabel = (char) ('A' + i);
            sb.append("  ").append(rowLabel).append(" ");

            // Cell values with vertical separators
            for (int j = 0; j < 9; j++) {
                if (j > 0 && j % 3 == 0) {
                    sb.append("| ");
                }

                Integer value = grid.getCell(i, j).getValue();
                if (value == null) {
                    sb.append("_ ");
                } else {
                    sb.append(value).append(" ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public static void printGrid(SudokuGrid grid) {
        System.out.print(formatGrid(grid));
    }
}

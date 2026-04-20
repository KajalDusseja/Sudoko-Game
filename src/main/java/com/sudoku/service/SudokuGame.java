package com.sudoku.service;

import com.sudoku.ui.GridDisplay;

import java.util.Scanner;

//This class is the entry point for game. It handles the user interaction. 

public class SudokuGame {
    private final SudokuGameController controller;
    private final Scanner scanner;

    public SudokuGame() {
        this.controller = new SudokuGameController();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        SudokuGame game = new SudokuGame();
        game.run();
    }

    public void run() {
        System.out.println("Welcome to Sudoku!\n");

        boolean playAgain = true;
        while (playAgain) {
            controller.startNewGame();
            playGame();

            // Ask player if they want to continue
            System.out.print("Press any key to play again or type 'quit' to exit: ");
            String input = scanner.nextLine().trim().toLowerCase();
            playAgain = !input.equals("quit");
        }

        System.out.println("Thank you for playing Sudoku!");
        scanner.close();
    }

    private void playGame() {
        System.out.println("Here is your puzzle:");
        GridDisplay.printGrid(controller.getGrid());

        while (true) {
            System.out.print("Enter command (e.g., A3 4, C5 clear, hint, check): ");
            String input = scanner.nextLine();

            Command command = Command.parseInput(input);

            switch (command.getType()) {
                case PLACE:
                    handlePlace(command);
                    break;
                case CLEAR:
                    handleClear(command);
                    break;
                case HINT:
                    handleHint();
                    break;
                case CHECK:
                    handleCheck();
                    break;
                case QUIT:
                    System.out.println("Thanks for playing!");
                    return;
                case INVALID:
                    System.out.println("Invalid command. Please try again.");
                    continue; // Skip the rest of the loop
            }

            // After each move, check if puzzle is solved
            if (controller.isSolved()) {
                System.out.println("\nCurrent grid:");
                GridDisplay.printGrid(controller.getGrid());
                System.out.println("You have successfully completed the Sudoku puzzle!");
                return;
            }
        }
    }

    private void handlePlace(Command command) {
        String error = controller.placeNumber(command.getRow(), command.getColumn(), command.getValue());
        if (error != null) {
            System.out.println("Invalid move. " + error);
            GridDisplay.printGrid(controller.getGrid());
        } else {
            System.out.println("\nMove accepted.\n");
            System.out.println("Current grid:");
            GridDisplay.printGrid(controller.getGrid());
        }
    }

    private void handleClear(Command command) {
        String error = controller.clearCell(command.getRow(), command.getColumn());
        if (error != null) {
            System.out.println("Invalid move. " + error);
            GridDisplay.printGrid(controller.getGrid());
        } else {
            System.out.println("\nMove accepted.\n");
            System.out.println("Current grid:");
            GridDisplay.printGrid(controller.getGrid());
        }
    }

    private void handleHint() {
        String hint = controller.getHint();
        System.out.println("\n" + hint + "\n");
        System.out.println("Current grid:");
        GridDisplay.printGrid(controller.getGrid());
    }

    private void handleCheck() {
        String result = controller.checkGrid();
        System.out.println("\n" + result + "\n");
        System.out.println("Current grid:");
        GridDisplay.printGrid(controller.getGrid());
    }
}

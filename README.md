# Sudoku Game

A production-quality Sudoku puzzle game implemented in Java with comprehensive testing and clean architecture.

## Features

✅ Interactive command-line Sudoku gameplay  
✅ Fixed puzzle with 30 pre-filled cells and 51 empty cells 
✅ Move validation and Sudoku rule checking  
✅ Hint generation with backtracking solver (picks random empty cell for variety)  
✅ Pre-filled cell protection (locked to maintain puzzle integrity)  
✅ Game completion detection  
✅ User-friendly error messages with cell references 

## Quick Start

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

### Build the Project

```bash
cd /path/to/Sudoku-Game
mvn clean package
```

### Run the Game

```bash
java -cp target/sudoku-game-1.0.0.jar com.sudoku.service.SudokuGame
```

Or using Maven:

```bash
mvn exec:java -Dexec.mainClass="com.sudoku.service.SudokuGame"
```

### Run Tests

```bash
mvn test
```

View test report:
```bash
# Open target/surefire-reports/index.html in a browser
```

## Project Structure

```
Sudoku-Game/
├── src/
│   ├── main/java/com/sudoku/
│   │   ├── dao/
│   │   │   ├── SudokuCell.java          # Represents a single Sudoku cell
│   │   │   ├── SudokuGrid.java          # Manages the 9x9 game grid
│   │   │   ├── SudokuValidator.java     # Validates moves and game state
│   │   │   └── ValidationError.java     # Error data structure
│   │   ├── service/
│   │   │   ├── SudokuGame.java          # Main game entry point
│   │   │   ├── SudokuGameController.java # Game logic and flow control
│   │   │   ├── SudokuSolver.java        # Backtracking solver for hints
│   │   │   ├── PuzzleGenerator.java     # Generates Sudoku puzzles
│   │   │   └── Command.java             # Command parsing and execution
│   │   └── ui/
│   │       └── GridDisplay.java         # Command-line grid display
│   └── test/java/com/sudoku/
│       ├── dao/
│       │   ├── SudokuCellTest.java
│       │   ├── SudokuGridTest.java
│       │   └── SudokuValidatorTest.java
│       └── service/
│           ├── CommandTest.java
│           ├── PuzzleGeneratorTest.java
│           └── SudokuGameControllerTest.java
├── target/                  # Compiled classes and JAR file
├── pom.xml                  # Maven configuration
├── README.md               # This file
└── DESIGN.md               # Architecture and design documentation
```

## How to Play

1. Start the game and view the initial puzzle
2. Enter commands to make moves:
   - **Place a number**: `A3 4` (place 4 at row A, column 3)
   - **Clear a cell**: `C5 clear` (clear cell at row C, column 5)
   - **Get a hint**: `hint` (reveals one correct number)
   - **Check grid**: `check` (validates for Sudoku rule violations)
   - **Quit game**: `quit` (exit to main menu)

3. Complete the puzzle by filling all cells without violating Sudoku rules
4. Win message appears when puzzle is solved!

## Game play

### Success example
```
Welcome to Sudoku!

Here is your puzzle:
    1 2 3 4 5 6 7 8 9
  A 5 3 _ _ 7 _ _ _ _
  B 6 _ _ 1 9 5 _ _ _
  C _ 9 8 _ _ _ _ 6 _
  D 8 _ _ _ 6 _ _ _ 3
  E 4 _ _ 8 _ 3 _ _ 1
  F 7 _ _ _ 2 _ _ _ 6
  G _ 6 _ _ _ _ 2 8 _
  H _ _ _ 4 1 9 _ _ 5
  I _ _ _ _ 8 _ _ 7 9

Enter command (e.g., A3 4, C5 clear, hint, check):
A3 4

Move accepted.

Current grid:
    1 2 3 4 5 6 7 8 9
  A 5 3 4 _ 7 _ _ _ _
  B 6 _ _ 1 9 5 _ _ _
  C _ 9 8 _ _ _ _ 6 _
  D 8 _ _ _ 6 _ _ _ 3
  E 4 _ _ 8 _ 3 _ _ 1
  F 7 _ _ _ 2 _ _ _ 6
  G _ 6 _ _ _ _ 2 8 _
  H _ _ _ 4 1 9 _ _ 5
  I _ _ _ _ 8 _ _ 7 9

Enter command (e.g., A3 4, C5 clear, hint, check):
check
No rule violations detected.

Enter command (e.g., A3 4, C5 clear, hint, check):
hint
Hint: Cell E5 = 5

.
.
.

Enter command (e.g., A3 4, C5 clear, hint, check):
I4 2

Move accepted.

Current grid:
    1 2 3 4 5 6 7 8 9
  A 5 3 4 6 7 8 9 1 2
  B 6 7 2 1 9 5 3 4 8
  C 1 9 8 3 4 2 5 6 7
  D 8 5 9 7 6 1 4 2 3
  E 4 2 6 8 5 3 7 9 1
  F 7 1 3 9 2 4 8 5 6
  G 9 6 1 5 3 7 2 8 4
  H 2 8 7 4 1 9 6 3 5
  I 3 4 5 2 8 6 1 7 9

You have successfully completed the Sudoku puzzle!
Press any key to play again...
```

### Invalid move example
```
Here is your puzzle:
    1 2 3 4 5 6 7 8 9
  A 5 3 _ _ 7 _ _ _ _
  B 6 _ _ 1 9 5 _ _ _
  C _ 9 8 _ _ _ _ 6 _
  D 8 _ _ _ 6 _ _ _ 3
  E 4 _ _ 8 _ 3 _ _ 1
  F 7 _ _ _ 2 _ _ _ 6
  G _ 6 _ _ _ _ 2 8 _
  H _ _ _ 4 1 9 _ _ 5
  I _ _ _ _ 8 _ _ 7 9

Enter command (e.g., A3 4, C5 clear, hint, check, quit):
A1 6

Invalid move. A1 is pre-filled.

Current grid:
    1 2 3 4 5 6 7 8 9
  A 5 3 _ _ 7 _ _ _ _
  B 6 _ _ 1 9 5 _ _ _
  C _ 9 8 _ _ _ _ 6 _
  D 8 _ _ _ 6 _ _ _ 3
  E 4 _ _ 8 _ 3 _ _ 1
  F 7 _ _ _ 2 _ _ _ 6
  G _ 6 _ _ _ _ 2 8 _
  H _ _ _ 4 1 9 _ _ 5
  I _ _ _ _ 8 _ _ 7 9

Enter command (e.g., A3 4, C5 clear, hint, check):
  
```

### Violation example
```
Here is your puzzle:
    1 2 3 4 5 6 7 8 9
  A 5 3 _ _ 7 _ _ _ _
  B 6 _ _ 1 9 5 _ _ _
  C _ 9 8 _ _ _ _ 6 _
  D 8 _ _ _ 6 _ _ _ 3
  E 4 _ _ 8 _ 3 _ _ 1
  F 7 _ _ _ 2 _ _ _ 6
  G _ 6 _ _ _ _ 2 8 _
  H _ _ _ 4 1 9 _ _ 5
  I _ _ _ _ 8 _ _ 7 9

Enter command (e.g., A3 4, C5 clear, hint, check, quit):
A3 3

Move accepted.

Current grid:
    1 2 3 4 5 6 7 8 9
  A 5 3 3 _ 7 _ _ _ _
  B 6 _ _ 1 9 5 _ _ _
  C _ 9 8 _ _ _ _ 6 _
  D 8 _ _ _ 6 _ _ _ 3
  E 4 _ _ 8 _ 3 _ _ 1
  F 7 _ _ _ 2 _ _ _ 6
  G _ 6 _ _ _ _ 2 8 _
  H _ _ _ 4 1 9 _ _ 5
  I _ _ _ _ 8 _ _ 7 9

Enter command (e.g., A3 4, C5 clear, hint, check):
check
Number 3 already exists in Row A.
```

### Violation example 2
```
Here is your puzzle:
    1 2 3 4 5 6 7 8 9
  A 5 3 _ _ 7 _ _ _ _
  B 6 _ _ 1 9 5 _ _ _
  C _ 9 8 _ _ _ _ 6 _
  D 8 _ _ _ 6 _ _ _ 3
  E 4 _ _ 8 _ 3 _ _ 1
  F 7 _ _ _ 2 _ _ _ 6
  G _ 6 _ _ _ _ 2 8 _
  H _ _ _ 4 1 9 _ _ 5
  I _ _ _ _ 8 _ _ 7 9

Enter command (e.g., A3 4, C5 clear, hint, check, quit):
C1 5

Move accepted.

Current grid:
    1 2 3 4 5 6 7 8 9
  A 5 3 _ _ 7 _ _ _ _
  B 6 _ _ 1 9 5 _ _ _
  C 5 9 8 _ _ _ _ 6 _
  D 8 _ _ _ 6 _ _ _ 3
  E 4 _ _ 8 _ 3 _ _ 1
  F 7 _ _ _ 2 _ _ _ 6
  G _ 6 _ _ _ _ 2 8 _
  H _ _ _ 4 1 9 _ _ 5
  I _ _ _ _ 8 _ _ 7 9

Enter command (e.g., A3 4, C5 clear, hint, check):
check
Number 5 already exists in Column 1.
```

### Violation example 3
```
Here is your puzzle:
    1 2 3 4 5 6 7 8 9
  A 5 3 _ _ 7 _ _ _ _
  B 6 _ _ 1 9 5 _ _ _
  C _ 9 8 _ _ _ _ 6 _
  D 8 _ _ _ 6 _ _ _ 3
  E 4 _ _ 8 _ 3 _ _ 1
  F 7 _ _ _ 2 _ _ _ 6
  G _ 6 _ _ _ _ 2 8 _
  H _ _ _ 4 1 9 _ _ 5
  I _ _ _ _ 8 _ _ 7 9

Enter command (e.g., A3 4, C5 clear, hint, check, quit):
B3 8

Move accepted.

Current grid:
    1 2 3 4 5 6 7 8 9
  A 5 3 _ _ 7 _ _ _ _
  B 6 _ 8 1 9 5 _ _ _
  C _ 9 8 _ _ _ _ 6 _
  D 8 _ _ _ 6 _ _ _ 3
  E 4 _ _ 8 _ 3 _ _ 1
  F 7 _ _ _ 2 _ _ _ 6
  G _ 6 _ _ _ _ 2 8 _
  H _ _ _ 4 1 9 _ _ 5
  I _ _ _ _ 8 _ _ 7 9

Enter command (e.g., A3 4, C5 clear, hint, check):
check
Number 8 already exists in the same 3×3 subgrid.
```

## Known Limitations

1. **Fixed Puzzle**: Currently uses one predefined puzzle (can be randomized)
2. **No Save Feature**: Progress not persisted between sessions
3. **Single Player**: No multiplayer support
4. **No Difficulty Selection**: Single difficulty level (medium ~30 clues)

## Future Enhancements

- [ ] Random puzzle generation
- [ ] Multiple difficulty levels
- [ ] Game statistics and scoring
- [ ] Save/load functionality
- [ ] Graphical UI with mouse support
- [ ] Multiplayer support

## License

This project is provided as-is for educational and personal use.

---

**Happy Solving!** 🧩



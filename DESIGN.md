# Sudoku Game - Design and Implementation

## Overview
This is a production ready Sudoku game implemented in Java that allows users to play Sudoku puzzles interactively from the command line. The application follows SOLID principles, clean code practices, and test driven development (TDD).

## Design Principles

### Architecture
The application is structured using layered architecture with clear separation of concerns:

- **Model Layer** (`com.sudoku.dao`): Core business logic
  - `SudokuCell`: Represents a single cell in the grid
  - `SudokuGrid`: 9x9 sudoku grid 
  - `SudokuValidator`: Validates puzzle according to Sudoku rules
  - `ValidationError`: Error reporting

- **Service Layer** (`com.sudoku.service`): Game mechanics
  - `SudokuGameController`: Main game state and logic
  - `SudokuSolver`: Solves puzzles using backtracking algorithm
  - `PuzzleGenerator`: Generates initial puzzles
  - `Command`: Parses user input commands
  - `SudokuGame`: Main entry point and game loop

- **UI Layer** (`com.sudoku.ui`): Presentation
  - `GridDisplay`: Formats and displays the grid

### SOLID Principles Applied

1. **Single Responsibility Principle (SRP)**
   - Each class has one clear responsibility
   - `SudokuValidator` only validates, doesn't modify state
   - `PuzzleGenerator` only generates puzzles

2. **Open/Closed Principle (OCP)**
   - Designed to be extended without modification
   - New validators can be added to validation pipeline
   - New command types can be added to the command parser

3. **Liskov Substitution Principle (LSP)**
   - All dao objects follow consistent interfaces
   - Cells and grids have predictable behavior

4. **Interface Segregation Principle (ISP)**
   - Classes expose only necessary methods
   - Focused, cohesive interfaces

5. **Dependency Inversion Principle (DIP)**
   - High-level modules don't depend on low-level modules
   - Uses dependency injection in `SudokuGameController`

### Key Design Decisions

1. **Immutable Pre-filled Cells**
   - Pre-filled cells cannot be modified—enforced at the object level
   - This is safer than just validation checks; prevents bugs at the source
   - Trade-off: Slightly more complex grid initialization, but worth it for safety

2. **Backtracking Solver**
   - Used for hint generation and puzzle verification
   - Simple to understand and debug, even if not the fastest algorithm
   - Performance is fine for 9x9 Sudoku (well under 200ms for hints)

3. **Grid Abstraction**
   - `SudokuGrid` provides helper methods for rows, columns, and 3x3 subgrids
   - Tried storing them separately once—ended up in sync issues. Central calculation is cleaner
   - Makes validation logic much more readable

4. **Command Pattern for Input**
   - User input parsed into `Command` objects (type-safe, not string matching)
   - Why? Easier to test, less error-prone than string checks everywhere
   - Can easily add new command types without changing the game loop

5. **Copy-on-Demand for Solving**
   - Grid is copied when generating hints (solver works on copy, not original)
   - This way we can explore the puzzle state without affecting the player's game
   - Alternative: pass a flag to solve() method. This way is cleaner.

## Testing Strategy

### Test Coverage
- **Domain Unit Tests**: Grid structure, cell behavior, validation
- **Command Parsing Tests**: All command types, edge cases, invalid inputs
- **Game Logic Tests**: Moves, clearing, hints, game state
- **Puzzle Tests**: Generation, pre-filled count, consistency

### Testing Approach (TDD)
- Tests written before implementation
- Each feature has corresponding test suite
- Edge cases covered: invalid indices, boundary values, special states

**Total: 56 comprehensive tests**

## Validation Rules Implemented

1. **No duplicate numbers in rows**: Each row can only contain 1-9 once
2. **No duplicate numbers in columns**: Each column can only contain 1-9 once
3. **No duplicate numbers in 3×3 subgrids**: Each 3×3 subgrid can only contain 1-9 once
4. **Pre-filled cells cannot be modified**: Enforced at object level
5. **Only numbers 1-9 allowed**: Validated on placement

## User Commands

| Command | Example | Purpose |
|---------|---------|---------|
| Place | `A3 4` | Place number 4 at row A, column 3 |
| Clear | `C5 clear` | Clear cell at row C, column 5 |
| Hint | `hint` | Reveal one correct number in an empty cell |
| Check | `check` | Validate current grid for rule violations |
| Quit | `quit` | Exit the game |

## Error Handling

- Invalid cell references rejected at parse time
- Pre-filled cell modification rejected at object level
- Invalid values rejected with user-friendly messages
- Rule violations reported with specific locations

## Assumptions

1. **User input format**: Strict format for cell references (e.g., "A1" not "a1", though case-insensitive for commands)
2. **Single puzzle per game**: New game generates same puzzle (can be extended for random generation)
3. **Hint generation**: Random empty cell selection (fairness over strategic difficulty)
4. **Solver correctness**: Assumes all generated puzzles have valid solutions
5. **Command-line only**: No GUI, pure text-based interaction

## Future Enhancements

1. **Random puzzle generation**: Create variety of puzzles instead of fixed one
2. **Difficulty levels**: Different numbers of pre-filled cells
3. **Score tracking**: Track moves, time, hints used
4. **Save/Load games**: Persist game state to file
5. **Statistics**: Track win rates, average completion time
6. **GUI**: Graphical interface with mouse interaction
7. **Online multiplayer**: Competitive or cooperative play

## Running the Application

See `README.md` for build and execution instructions.

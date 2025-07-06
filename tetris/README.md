# Console Tetris Game

A command-line implementation of the classic Tetris game written in Java. This version features customizable grid dimensions and text-based controls for a unique console gaming experience.

## Features

- **Customizable Grid Size**: Set your own width and height for the playing field
- **All Classic Tetris Pieces**: Complete set of 7 Tetriminos (I, O, S, Z, L, J, T)
- **Full Piece Control**: Move and rotate pieces with simple commands
- **Line Clearing**: "Break" command removes completed rows
- **Game Over Detection**: Detects when the game ends
- **Real-time Grid Display**: Visual representation using ASCII characters

## Getting Started

### Prerequisites

- Java 8 or higher
- Command line terminal

### Installation

1. Clone the repository:
```bash
git clone https://github.com/v-dav/java_programming/tetris.git
cd tetris
```

2. Compile the Java files:
```bash
javac -d . tetris/*.java tetris/pieces/*.java
```

3. Run the game:
```bash
java tetris.Main
```

## How to Play

### Initial Setup

1. When you start the game, enter the grid dimensions on the same line, separated by space:
    - First number: Grid width (recommended: 10)
    - Second number: Grid height (recommended: 20)

Example:
```
10 20
```

### Game Commands

| Command | Description |
|---------|-------------|
| `piece` | Spawn a new piece (follow with piece type) |
| `left` | Move current piece left |
| `right` | Move current piece right |
| `down` | Move current piece down |
| `rotate` | Rotate current piece clockwise |
| `break` | Clear completed rows and remove current piece |
| `exit` | Quit the game |

### Piece Types

When using the `piece` command, enter one of these piece types on the next line:

- **I** - Line piece (4 blocks in a row)
- **O** - Square piece (2x2 square)
- **S** - S-shaped piece
- **Z** - Z-shaped piece
- **L** - L-shaped piece
- **J** - Reverse L-shaped piece
- **T** - T-shaped piece

### Example Gameplay

The greater-than symbol followed by a space (> ) represents the user input. Note that it's not part of the input.

```
> 10 10
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -

> piece
> T
- - - - 0 - - - - -
- - - - 0 0 - - - -
- - - - 0 - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -

> rotate
- - - - - - - - - -
- - - - 0 - - - - -
- - - 0 0 0 - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - 0 0 0 - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - 0 0 0 - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - 0 0 0 - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - 0 0 0 - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - 0 0 0 - - - -
- - - - - - - - - -
- - - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - 0 0 0 - - - -
- - - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - 0 0 0 - - - -

> piece
> S
- - - - 0 0 - - - -
- - - 0 0 - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - 0 0 0 - - - -

> rotate
- - - - - - - - - -
- - - - 0 - - - - -
- - - - 0 0 - - - -
- - - - - 0 - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - - 0 0 - - - -
- - - - - 0 - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - - 0 0 - - - -
- - - - - 0 - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - - 0 0 - - - -
- - - - - 0 - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - - 0 0 - - - -
- - - - - 0 - - - -
- - - - 0 - - - - -
- - - 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - - 0 0 - - - -
- - - - 0 0 - - - -
- - - 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - - 0 0 - - - -
- - - - 0 0 - - - -
- - - 0 0 0 - - - -

> piece
> I
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - - 0 0 - - - -
- - - - 0 0 - - - -
- - - 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - - 0 0 - - - -
- - - - 0 0 - - - -
- - - 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 0 - - - -
- - - - 0 0 - - - -
- - - 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 0 - - - -
- - - - 0 0 - - - -
- - - 0 0 0 - - - -

> piece
> O
- - - - 0 0 - - - -
- - - - 0 0 - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 0 - - - -
- - - - 0 0 - - - -
- - - 0 0 0 - - - -

> down
- - - - 0 0 - - - -
- - - - 0 0 - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 0 - - - -
- - - - 0 0 - - - -
- - - 0 0 0 - - - -

Game Over!

> 10 10
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -

> piece
> O
- - - - 0 0 - - - -
- - - - 0 0 - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -

> left
- - - - - - - - - -
- - - 0 0 - - - - -
- - - 0 0 - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -

> left
- - - - - - - - - -
- - - - - - - - - -
- - 0 0 - - - - - -
- - 0 0 - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -

> left
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- 0 0 - - - - - - -
- 0 0 - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -

> left
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 - - - - - - - -
- - - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 - - - - - - - -

> piece
> I
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 - - - - - - - -

> rotate
- - - - - - - - - -
- - - 0 0 0 0 - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 - - - - - - - -

> left
- - - - - - - - - -
- - - - - - - - - -
- - 0 0 0 0 - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - 0 0 0 0 - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - 0 0 0 0 - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - 0 0 0 0 - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - 0 0 0 0 - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - 0 0 0 0 - - - -
0 0 - - - - - - - -
0 0 - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 0 0 0 0 - - - -
0 0 - - - - - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 0 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 0 0 0 0 - - - -

> piece
> I
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - 0 - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 0 0 0 0 - - - -

> rotate
- - - - - - - - - -
- - - 0 0 0 0 - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 0 0 0 0 - - - -

> right
- - - - - - - - - -
- - - - - - - - - -
- - - - 0 0 0 0 - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 0 0 0 0 - - - -

> right
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - 0 0 0 0 -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 0 0 0 0 - - - -

> right
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - 0 0 0 0
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 0 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - 0 0 0 0
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 0 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - 0 0 0 0
- - - - - - - - - -
0 0 - - - - - - - -
0 0 0 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - 0 0 0 0
0 0 - - - - - - - -
0 0 0 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - 0 0 0 0
0 0 0 0 0 0 - - - -

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 0 0 0 0 0 0 0 0

> down
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -
0 0 0 0 0 0 0 0 0 0

> break
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
- - - - - - - - - -
0 0 - - - - - - - -

> exit
```

## Game Mechanics

### Movement
- After every movement command (`left`, `right`, `rotate`), the piece automatically moves down one position
- Pieces cannot move outside the grid boundaries
- Pieces stop when they hit the bottom or another piece

### Line Clearing
- When a horizontal row is completely filled, use the `break` command to clear it
- All rows above the cleared row shift down
- The top row becomes empty

### Game Over
- The game ends when any column becomes completely filled from bottom to top
- A "Game Over!" message will display

## Grid Representation

- **`- `** (dash + space): Empty cell
- **`0 `** (zero + space): Occupied cell

## Project Structure

```
tetris/
├── Main.java              # Entry point
├── GameController.java    # Main game logic and command handling
├── TetrisBoard.java       # Grid management and display
└── pieces/
    ├── Piece.java         # Abstract base class for all pieces
    ├── Ipiece.java        # Line piece
    ├── Opiece.java        # Square piece
    ├── Spiece.java        # S-piece
    ├── Zpiece.java        # Z-piece
    ├── Lpiece.java        # L-piece
    ├── Jpiece.java        # J-piece
    └── Tpiece.java        # T-piece
```

## Technical Details

### Coordinate System
- Grid positions are represented as single integers
- X-coordinate: `position % gridWidth`
- Y-coordinate: `position / gridWidth`
- Origin (0,0) is at the top-left corner

### Piece Rotation
- Each piece (except O-piece) has multiple rotation states
- Rotation cycles through states: 0° → 90° → 180° → 270° → 0°
- Some pieces have only 2 rotation states (I, S, Z pieces)

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Future Enhancements

- [ ] Automatic piece dropping with timer
- [ ] Score system
- [ ] Level progression with increased speed
- [ ] Preview of next piece
- [ ] Save/load game state
- [ ] Color support in terminal
- [ ] Sound effects
- [ ] High score tracking

## License

This project is licensed under the MIT License

## Acknowledgments

- Inspired by the classic Tetris game created by Alexey Pajitnov
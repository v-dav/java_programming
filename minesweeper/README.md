# 💣 Java Minesweeper

A classic Minesweeper game implementation in Java with a text-based interface that runs in the console.

## 📝 Description

Minesweeper is a single-player puzzle game. The objective is to clear a rectangular board containing hidden "mines" without detonating any of them, with help from clues about the number of neighboring mines in each field.

## ✨ Features

- Customizable mine count
- Two ways to win:
    - Mark all mines correctly
    - Explore all safe cells
- First move safety (first explored cell is never a mine)
- Auto-exploration of connected empty cells
- Mine marking system

## 🎮 How to Play

### Game Symbols
- `.` - Unexplored cell
- `*` - Marked cell (where you think a mine is)
- `/` - Explored empty cell with no mines around it
- `1-8` - Explored cell with 1-8 mines around it
- `X` - Mine (shown only when game is lost)

### Commands
- `x y free` - Explore the cell at coordinates (x, y)
- `x y mine` - Mark/unmark the cell at coordinates (x, y) as a mine

### Gameplay Example

Starting a new game:
```
How many mines do you want on the field? 10

 |123456789|
-|---------|
1|.........|
2|.........|
3|.........|
4|.........|
5|.........|
6|.........|
7|.........|
8|.........|
9|.........|
-|---------|
```

After exploring a cell (coordinates: 5 5 free):
```
 |123456789|
-|---------|
1|.1///1...|
2|.1//12...|
3|11//1....|
4|////1....|
5|11111....|
6|.........|
7|.........|
8|.........|
9|.........|
-|---------|
```

Marking a potential mine (coordinates: 1 2 mine):
```
 |123456789|
-|---------|
1|.1///1...|
2|*1//12...|
3|11//1....|
4|////1....|
5|11111....|
6|.........|
7|.........|
8|.........|
9|.........|
-|---------|
```

Winning the game:
```
 |123456789|
-|---------|
1|.1///1...|
2|*1//12.31|
3|11//1*1/.|
4|////111/.|
5|11111111.|
6|/111//1*.|
7|23*1//111|
8|**21/////|
9|..1//////|
-|---------|
Congratulations! You found all the mines!
```

Losing the game:
```
 |123456789|
-|---------|
1|.1///1X..|
2|X1//12...|
3|11//1X...|
4|////1....|
5|11111....|
6|.X..X....|
7|.3X...X..|
8|.X..X211.|
9|...X.....|
-|---------|
You stepped on a mine and failed!
```

## 🚀 Installation & Running

1. Clone the repository:
```bash
git clone https://github.com/yourusername/minesweeper.git
cd minesweeper
```

2. Compile the Java files:
```bash
javac -d out minesweeper/*.java
```

3. Run the game:
```bash
java -cp out minesweeper.Main
```

## 🏗️ Project Structure

```
minesweeper/
├── Main.java            # Entry point for the application
├── GameManager.java     # Game logic and field printing
└── Minefield.java       # Field management and builder pattern
```

### Key Components

- **Main.java**: Program entry point that initializes the game
- **GameManager.java**: Contains the game logic, user interaction, and rendering
- **Minefield.java**: Handles the game field, mines placement, and provides a builder pattern

## 🧠 Game Logic

1. Player sets number of mines
2. Game creates an empty field
3. Player makes the first move (guaranteed to be safe)
4. The game continues until:
    - Player marks all mines correctly
    - Player explores all safe cells
    - Player steps on a mine and loses

## 🧩 Technical Details

- **Builder Pattern**: Used in the Minefield class for flexible initialization
- **Recursive Exploration**: When opening empty cells, a recursive algorithm explores all adjacent safe cells
- **First Move Safety**: The first move is always guaranteed to be safe
- **Field Representation**: 2D arrays represent the field state

## 🔮 Future Improvements

- Graphical user interface (GUI)
- Difficulty levels (Beginner, Intermediate, Expert)
- Timer and high score system
- Themes and customizable field sizes
- Save/load game functionality

## 📚 Lessons Learned

- Recursive algorithms for cell exploration
- Builder design pattern
- Input validation and processing
- Game state management

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.
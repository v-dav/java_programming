# Tic Tac Toe
![Java](https://img.shields.io/badge/Java-F80000?style=for-the-badge&logo=java&logoColor=white)

## Project Overview

This Java application is a simple console-based Tic Tac Toe game. It allows two players to alternately place their markers ('X' and 'O') on a 3x3 grid. The game checks for a winner or a draw after every move and displays the game state on the console.

## Technologies Used

**Java:** The entire project is implemented in Java, leveraging basic control structures, arrays, and the standard IO library to manage game dynamics and user interactions.


## How to Use

To play the game, compile and run the `Main` class. Players will take turns entering the coordinates for their moves. The game will prompt for coordinates, validate the input, and update the game board accordingly. 

### Compilation and Execution

Ensure you have Java installed on your system. Compile the Java files using `javac` and then run the `Main` class using `java`:

```bash
javac Main.java
java Main.java
```

### Gameplay

1. The game starts with an empty 3x3 grid.
2. Player 1 (X) is prompted to enter coordinates for their move.
3. Player 2 (O) then does the same.
4. This process repeats until the game detects a win or a draw.

The board coordinates start from 1 to 3, both horizontally and vertically:

```
(1,1) (1,2) (1,3)
(2,1) (2,2) (2,3)
(3,1) (3,2) (3,3)
```

Players must enter coordinates in the `x y` format, where `x` is the row number and `y` is the column number.
![Skärmavbild 2024-06-10 kl  19 03 48](https://github.com/v-dav/java_programming/assets/115344057/23390eea-1a6b-403d-8433-0f70f800c5f0)

## Game Features

- **Input Validation:** Checks for valid numeric input and ensures that chosen cells are unoccupied.
- **Win Detection:** The game automatically detects and announces a winner if three of their symbols are aligned horizontally, vertically, or diagonally.
- **Draw Detection:** Declares a draw if all cells are filled and there is no winner.

## Contribution

Contributions to this project are welcome. Please adhere to the following guidelines:
- Fork the repository and create a new branch for your feature or fix.
- Write clean, commented, and readable Java code.
- Ensure your changes do not break existing functionality.
- Update or add new tests as necessary.
- Submit a pull request with a comprehensive description of changes.

## License

This project is open-sourced under the MIT license. See the [LICENSE](LICENSE) file for details.

## Author

- [Vladimir Davidov](https://github.com/v-dav)

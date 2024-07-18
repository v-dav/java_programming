# Last Pencil Game

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

![image](https://github.com/user-attachments/assets/dee7fcc5-52f7-4039-ae23-033c419b35ed)


## Project Overview

The Last Pencil Game is a simple interactive game where two players (**one of them is Jack witch is always the bot**) take turns removing 1 to 3 pencils from a pile. The player forced to take the last pencil loses the game. This project is implemented in Java and showcases basic concepts such as user input handling, control flow, and basic AI pattern for the bot's moves.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed on your machine. You can download it from [Oracle's official website](https://www.oracle.com/java/technologies/javase-downloads.html).

### Running the Game

1. Clone the repository or download the code file.
2. Navigate to the project directory.
3. Compile the Java code using the following command:

    ```sh
    javac Main.java
    ```

4. Run the game using the command:

    ```sh
    java Main
    ```

## Game Rules

1. The game starts by asking the player how many pencils to use.
2. Players take turns removing 1, 2, or 3 pencils from the pile.
3. The player forced to take the last pencil loses the game.
4. One of the players (Jack) is a bot, which makes calculated moves based on the number of remaining pencils.

## Code Explanation

### Main Game Loop

The main game loop continuously asks the current player to remove pencils until there are no pencils left. If the player is a bot, the bot calculates the best move based on the number of pencils remaining.

### Player and Bot Turns

- **Player Turn**: The player is prompted to enter a number between 1 and 3. If the input is invalid, the player is prompted again.
- **Bot Turn**: The bot uses a simple strategy to decide how many pencils to take, aiming to leave a number of pencils that is a multiple of 4 for the opponent.

### Example Output

```
How many pencils would you like to use:
10
Who will be the first (John, Jack):
John
||||||||||
John's turn
2
|||||||||
Jack's turn
Jack is thinking..........
3
||||||
John's turn
2
||||
Jack's turn
Jack is thinking..........
1
|||
John's turn
1
||
Jack's turn
Jack is thinking..........
1
|
John's turn
1
Jack won!
```

## Author

- [Vladimir Davidov](https://github.com/v-dav)

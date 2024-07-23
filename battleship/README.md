# Battleship Game

![Java Badge](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

![image](https://github.com/user-attachments/assets/a32b6012-f1cd-44d7-94c9-a12bc99b905d)

<br>
Welcome to the Battleship game! This project implements the classic Battleship game on local CLI using Java. The game allows two players to place their fleets on their respective boards and take turns trying to sink each other's ships.

## Features

1. **Different Ship Types**: 
    - Aircraft Carrier
    - Battleship
    - Cruiser
    - Destroyer
    - Submarine

2. **Game Mechanics**:
    - Players place their ships on the board.
    - Players take turns guessing the location of the opponent's ships. The console is cleared between each turn to prevent cheating
    - The game indicates hits, misses, and sinks.

3. **Object-Oriented Design**:
    - Use of classes and objects to represent ships, the game field, and the fleet.
    - Encapsulation of ship properties and behaviors in their respective classes.
    - Inheritance used to create specific ship types from a general `Ship` class.

4. **User Input Handling**:
    - Scanner for reading user input.
    - Input validation to ensure correct coordinates and ship placements.

## Classes and Concepts

1. **Main.java**:
    - Entry point of the game.
    - Handles the game loop and user interactions.

2. **Field.java**:
    - Represents the game board.
    - Handles placement of ships and recording of hits and misses.

3. **Fleet.java**:
    - Manages a collection of ships.
    - Checks the status of the fleet (whether all ships are sunk).

4. **Ship.java**:
    - Base class for all ship types.
    - Encapsulates common properties and methods for ships.

5. **Specific Ship Classes** (e.g., `AircraftCarrier.java`, `Battleship.java`, etc.):
    - Each class inherits from `Ship`.
    - Defines specific properties like size and name.

## Getting Started

1. **Compile the Code**:
    ```bash
    javac *.java
    ```

2. **Run the Game**:
    ```bash
    java battleship.Main
    ```

## Detailed Class Description

#### Main.java
- **Description**: The main class orchestrates the game flow. It initializes the game, handles player turns, and determines the game state.
- **Key Methods**:
  - `public static void main(String[] args)`: The entry point of the application.
  - `startGame()`: Initializes the game and manages the main game loop.

#### Field.java
- **Description**: Represents the game board where ships are placed and guesses are recorded.
- **Key Methods**:
  - `placeShip(Ship ship, int x, int y, boolean horizontal)`: Places a ship on the board.
  - `takeShot(int x, int y)`: Records a shot at the given coordinates and returns the result.

#### Fleet.java
- **Description**: Manages the collection of ships for a player.
- **Key Methods**:
  - `addShip(Ship ship)`: Adds a ship to the fleet.
  - `allShipsSunk()`: Checks if all ships in the fleet are sunk.

#### Ship.java
- **Description**: Abstract base class for all ship types.
- **Key Properties**:
  - `int size`: The size of the ship.
  - `String name`: The name of the ship.
  - `int[] position`: The position of the ship on the board.
  - `boolean[] hit`: Tracks which parts of the ship have been hit.

#### Specific Ship Classes (e.g., `AircraftCarrier.java`, `Battleship.java`)
- **Description**: Each class extends `Ship` and defines specific properties.
- **Key Properties**:
  - Each class has a predefined size and name, appropriate to its type.

### Concepts Implemented

- **Object-Oriented Programming**: Encapsulation, inheritance, and polymorphism.
- **Exception Handling**: Handling invalid inputs and game state errors.
- **Data Structures**: Use of arrays and collections to manage game state.
- **Algorithm Design**: Logic for ship placement, shot handling, and win condition checking.

### Usage

1. **Starting the Game**: Run the `Main` class to start the game. The game will prompt you to place your ships and take turns guessing the opponent's ship positions.
2. **Placing Ships**: Input coordinates to place your ships on the board.
3. **Taking Shots**: Input coordinates to guess the location of the opponent's ships.
4. **Winning the Game**: The game continues until all ships of one player are sunk.

## Screenshot

![Skärmavbild 2024-07-23 kl  14 40 56](https://github.com/user-attachments/assets/2dafc1da-8019-483b-bc2f-415740e08aff)


## Example

```
mac:src Vlad$ javac battleship/*.java
mac:src Vlad$ java battleship.Main

Player 1, place your ships on the game field

  1 2 3 4 5 6 7 8 9 10
A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~

Enter the coordinates of the Aircraft Carrier (5 cells):
A1 A5

  1 2 3 4 5 6 7 8 9 10
A O O O O O ~ ~ ~ ~ ~
B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~

Enter the coordinates of the Battleship (4 cells):
A10 C3

Error! Wrong ship location! Try again:
A10 c10

Error! Wrong ship location! Try again:
A10 C10

Error! Wrong length of the Battleship! Try again:
B1 B4

Error! You placed it too close to another one. Try again:
A10 D10

  1 2 3 4 5 6 7 8 9 10
A O O O O O ~ ~ ~ ~ O
B ~ ~ ~ ~ ~ ~ ~ ~ ~ O
C ~ ~ ~ ~ ~ ~ ~ ~ ~ O
D ~ ~ ~ ~ ~ ~ ~ ~ ~ O
E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~

Enter the coordinates of the Submarine (3 cells):
E1 E3

...

  1 2 3 4 5 6 7 8 9 10
A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
---------------------
  1 2 3 4 5 6 7 8 9 10
A O O O O O ~ ~ ~ ~ O
B ~ ~ ~ ~ ~ ~ ~ ~ ~ O
C ~ ~ ~ ~ ~ ~ ~ ~ ~ O
D ~ ~ ~ ~ ~ ~ ~ ~ ~ O
E O O O ~ ~ ~ ~ ~ ~ ~
F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
G O O O ~ ~ ~ ~ ~ ~ ~
H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
I ~ O ~ ~ ~ ~ ~ ~ ~ ~
J ~ O ~ ~ ~ ~ ~ ~ ~ ~

Player 1, it's your turn:

E5
You missed!
Press Enter and pass the move to another player

...

  1 2 3 4 5 6 7 8 9 10
A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
---------------------
  1 2 3 4 5 6 7 8 9 10
A O O O O O ~ ~ ~ ~ O
B ~ ~ ~ ~ ~ ~ ~ ~ ~ O
C ~ ~ ~ ~ ~ ~ ~ ~ ~ O
D ~ ~ ~ ~ ~ ~ ~ ~ ~ O
E ~ ~ ~ ~ M ~ ~ ~ ~ ~
F ~ ~ ~ ~ ~ ~ ~ ~ ~ O
G ~ ~ ~ ~ ~ ~ ~ ~ ~ O
H ~ ~ ~ ~ ~ ~ ~ ~ ~ O
I O O O ~ ~ ~ ~ ~ ~ ~
J ~ ~ ~ ~ ~ O O ~ ~ ~

Player 2, it's your turn:

A1
You hit a ship!
Press Enter and pass the move to another player

```

## Author

- Vladimir Davidov


# Bulls and Cows Game

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

![image](https://github.com/user-attachments/assets/5493bc31-9bf4-4ed1-a5b4-fa5e540838f4)


## Project Description

This project implements the Bulls and Cows game, a code-breaking game for two players. The goal is to guess a secret code within a certain number of tries. The game provides feedback in the form of "bulls" and "cows" for each guess. A "bull" indicates a correct digit in the correct position, while a "cow" indicates a correct digit in the wrong position.

## Features

- Generate a secret code with a customizable length and symbol range.
- Play the game with real-time feedback on the number of bulls and cows.
- Handle user input and provide meaningful error messages for invalid inputs.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed on your machine.

### Running the Game

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/bulls-and-cows-game.git
   cd bulls-and-cows-game
   ```

2. **Compile the Java code:**
   ```bash
   javac bullscows/Main.java
   ```

3. **Run the game:**
   ```bash
   java bullscows.Main
   ```

## How to Play

1. **Input the length of the secret code:**
   The length should be a positive integer.

2. **Input the number of possible symbols in the code:**
   The number of symbols should be a positive integer and at most 36 (digits 0-9 and letters a-z).

3. **Guess the secret code:**
   Enter your guess for the secret code. The game will provide feedback on the number of bulls and cows.

4. **Repeat until you guess the secret code:**
   The game continues until you guess the secret code correctly.

### Example

```plaintext
Input the length of the secret code:
5
Input the number of possible symbols in the code:
10
The secret is prepared: ***** (0-9).
Okay, let's start a game!
Turn 1:
ab123
Grade: 1 bull and 1 cow
Turn 2:
bb123
Grade: 1 bull and 1 cow
Turn 3:
aa123
Grade: 1 bull and 1 cow
Turn 4:
ab234
Grade: 1 cow
Turn 5:
ab124
Grade: 1 bull and 1 cow
Turn 6:
ab334
Grade: None.
Turn 7:
ab125   
Grade: 1 bull and 1 cow
Turn 8:
ab129
Grade: 1 bull and 2 cows
///......
Turn 48:
90928
Grade: 4 bulls
Turn 49:
9092a
Grade: 3 bulls
Turn 50:
90928
Grade: 4 bulls
Turn 51:
909a8
Grade: 3 bulls
Turn 52:
90928
Grade: 4 bulls
Turn 53:
90a28
Grade: 3 bulls and 1 cow
Turn 54:
90928
Grade: 4 bulls
Turn 55:
9a928
Grade: 3 bulls
Turn 56:
90928
Grade: 4 bulls
Turn 57:
a0928
Grade: 4 bulls
Turn 58:
10928
Grade: 5 bulls
Congratulations! You guessed the secret code.
V bulls_and_cows_game %
```

## Author

- Vladimir Davidov

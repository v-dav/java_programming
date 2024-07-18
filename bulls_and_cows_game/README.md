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
4
Input the number of possible symbols in the code:
6
The secret is prepared: **** (0-9, a-f).
Okay, let's start a game!
Turn 1:
1234
Grade: 1 bull and 1 cow
Turn 2:
5678
Grade: None
Turn 3:
1235
Grade: 1 bull and 2 cows
Turn 4:
2345
Grade: 2 bulls and 1 cow
Turn 5:
3456
Grade: 4 bulls
Congratulations! You guessed the secret code.
```

## Author

- Vladimir Davidov

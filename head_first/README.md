# Beer Song, Guessing Game and PhrasOMatic

This repository contains three different Java projects, each showcasing fundamental concepts of Java programming. Below are the descriptions and usage instructions for each project.
1.	**Beer Song:** Demonstrates the use of loops, conditional statements, and string manipulation.
2.	**Guess Game:** Showcases object-oriented programming principles by defining classes, creating instances, and using methods. It also incorporates random number generation and basic game logic.
3.	**PhraseOMatic:** Illustrates array handling, random number generation, and string concatenation to generate random phrases.

## Projects

### 1. Beer Song

![image](https://github.com/user-attachments/assets/8c7f2927-1744-4d48-a51e-a47b5db62d25)


This project prints the lyrics to the classic "99 Bottles of Beer" song. It starts from 99 bottles and counts down to 1, printing appropriate lines for each number of bottles.

```
99 bottles of beer on the wall
99 bottles of beer
Take one down.
Pass it around.

98 bottles of beer on the wall
98 bottles of beer
Take one down.
Pass it around.

97 bottles of beer on the wall
97 bottles of beer
Take one down.
Pass it around.

...

2 bottles of beer on the wall
2 bottles of beer
Take one down.
Pass it around.

1 bottle of beer on the wall
1 bottle of beer
Take one down.
Pass it around.

No more bottles of beer on the wall
```
### 2. Guess Game

This project implements a simple number-guessing game. The game generates a random number between 0 and 9, and three players attempt to guess the number. The game continues until one of the players guesses the number correctly.

```
I'm thinking of a number between 0 and 9...

The number to guess is 5

I'm guessing 3
I'm guessing 7
I'm guessing 5

Player one guessed 3
Player two guessed 7
Player three guessed 5

We have a winner!
Player one got it right? false
Player two got it right? false
Player three got it right? true
Game is over
```

### 3. PhraseOMatic

![image](https://github.com/user-attachments/assets/dc02726d-a74e-4d4a-8bcb-52ae7d24f5b7)


This project generates a random phrase by selecting words from three different lists and combining them. The result is a phrase that can be used as a business jargon or a buzzword.

## Usage

1. **Compile the Programs**:
    ```bash
    javac BeerSong.java
    javac GuessingGameLauncher.java
    javac PhraseOMatic.java
    ```

2. **Run the Programs**:
    - To run the Beer Song program:
      ```bash
      java BeerSong
      ```
    - To run the Guessing Game program:
      ```bash
      java GuessingGameLauncher
      ```
    - To run the PhraseOMatic program:
      ```bash
      java PhraseOMatic
      ```

## Author

- Vladimir Davidov

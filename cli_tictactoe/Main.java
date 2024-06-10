package tictactoe;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize and fill the grid
        char[][] board = new char[3][3];
        for (char[] row : board) {
            Arrays.fill(row, '_');
        }

        // Draw the empty grid
        printBoard(board);
        char player = 'X';

        // Prompt the user to make a move
        while (true) {
            int y, x;

            // Type validation
            if (scanner.hasNextInt() && scanner.hasNextInt()) {
                y = scanner.nextInt();
                x = scanner.nextInt();
            } else {
                System.out.println("You should enter numbers!");
                continue;
            }

            // Range validation
            boolean yOnBoard = y >= 1 && y <= 3;
            boolean xOnBoard = x >= 1 && x <= 3;
            if (!yOnBoard || !xOnBoard) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            // Occupation validation
            if (board[y - 1][x - 1] != '_') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            // Place the player on the board and print updated board
            board[y - 1][x - 1] = player;
            printBoard(board);

            // Switch the player
            if(player == 'X'){
                player = 'O';
            } else {
                player = 'X';
            }

            // Check the game state
            String winner = analyzeGameState(board);
            if (!Objects.equals(winner, "Game not finished")) {
                System.out.println(winner);
                break;
            }
        }
    }

    public static String analyzeGameState(char[][] board) {
        boolean hasEmptyCells = false;
        int xCount = 0;
        int oCount = 0;

        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '_') {
                    hasEmptyCells = true;
                } else if (cell == 'X') {
                    xCount++;
                } else if (cell == 'O') {
                    oCount++;
                }
            }
        }

        boolean xHasThreeInRow = checkWinner(board, 3, 'X');
        boolean oHasThreeInRow = checkWinner(board, 3, 'O');

        boolean xWins = xHasThreeInRow;
        boolean oWins = oHasThreeInRow;

        boolean draw = !xHasThreeInRow && !oHasThreeInRow && !hasEmptyCells;

        if (xWins) {
            return "X wins";
        } else if (oWins) {
            return "O wins";
        } else if (draw) {
            return "Draw";
        }

        return "Game not finished";
    }

    // Check if X or O has 3 consecutive characters
    public static boolean checkWinner(char[][] board, int size, char player) {
        // Check rows
        for (int i = 0; i < size; i++) {
            if (allEqual(board[i], size, player)) {
                return true;
            }
        }

        // Check columns
        for (int j = 0; j < size; j++) {
            if (allEqualColumn(board, size, j, player)) {
                return true;
            }
        }

        // Check main diagonal
        if (allEqualMainDiagonal(board, size, player)) {
            return true;
        }

        // Check anti-diagonal
        if (allEqualAntiDiagonal(board, size, player)) {
            return true;
        }

        // No winner found
        return false;
    }

    public static boolean allEqual(char[] array, int size, char player) {
        for (int i = 0; i < size; i++) {
            if (array[i] != player) {
                return false;
            }
        }
        return true;
    }

    public static boolean allEqualColumn(char[][] board, int size, int col, char player) {
        for (int i = 0; i < size; i++) {
            if (board[i][col] != player) {
                return false;
            }
        }
        return true;
    }

    public static boolean allEqualMainDiagonal(char[][] board, int size, char player) {
        for (int i = 0; i < size; i++) {
            if (board[i][i] != player) {
                return false;
            }
        }
        return true;
    }

    public static boolean allEqualAntiDiagonal(char[][] board, int size, char player) {
        for (int i = 0; i < size; i++) {
            if (board[i][size - 1 - i] != player) {
                return false;
            }
        }
        return true;
    }

    public static void printBoard(char[][] board) {
        System.out.println("---------");

        for (char[] chars : board) {
            System.out.print("| ");
            for (char aChar : chars) {
                System.out.print(aChar + " ");
            }
            System.out.println("|");
        }

        System.out.println("---------");
    }
}
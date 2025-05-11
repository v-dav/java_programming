package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class GameManager {

    Scanner scanner = new Scanner(System.in);

    public Minefield initializeGame() {
        System.out.print("How many mines do you want on the field? ");
        int minesNumber = scanner.nextInt();

        Minefield minefield = new Minefield.Builder()
                .setHeight(9)
                .setLength(9)
                .setMineCharacter("X")
                .setSafeCell(".")
                .setMinesNumber(minesNumber)
                .build();

        setHints(minefield); // numbers and mines are set
        return minefield;
    }

    public void startGameV2(Minefield minefield) {
        String[][] originalField = minefield.getField();
        String[][] hiddenField = minefield.getUnexploredCopy();
        String mineChar = minefield.getMineCharacter();

        String markMineCharacter = "*";
        String exploredFreeCellWithoutMinesAround = "/";

        int totalMines = minefield.getMinesNumber();
        int totalSafeCells = minefield.getSafeCellNumber();

        boolean firstMove = true;
        boolean gameWon = false;
        boolean gameLost = false;

        while (!gameWon && !gameLost) {
            printField(hiddenField);
            System.out.print("Set/unset mine marks or claim a cell as free: ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            String command = scanner.next();

            int j = x - 1;
            int i = y - 1;

            // Check if coordinates are valid
            if (i < 0 || i >= originalField.length || j < 0 || j >= originalField[0].length) {
                System.out.println("Invalid coordinates! Try again.");
                continue;
            }

            // Handle command
            if ("mine".equals(command)) {
                if (".".equals(hiddenField[i][j])) {
                    hiddenField[i][j] = markMineCharacter;
                } else if (hiddenField[i][j].equals(markMineCharacter)) {
                    hiddenField[i][j] = ".";
                } else {
                    System.out.println("Cannot mark/unmark explored cells!");
                }
            } else if ("free".equals(command)) {
                if (firstMove) {
                    if (originalField[i][j].equals(mineChar)) {
                        regenerateField(minefield, i, j);
                        originalField = minefield.getField();
                    }
                    firstMove = false;
                }

                if (originalField[i][j].equals(mineChar)) {
                    revealAllMines(hiddenField, originalField);
                    printField(hiddenField);
                    System.out.println("You stepped on a mine and failed!");
                    gameLost = true;
                } else {
                    exploreCells(hiddenField, originalField, i, j, exploredFreeCellWithoutMinesAround);
                }
            } else {
                System.out.println("Invalid command! Use 'mine' to mark/unmark or 'free' to explore.");
                continue;
            }

            // Check win conditions
            if (!gameLost) {
                int markedMines = 0;
                int totalMarkedCells = 0;
                int openedSafeCells = 0;

                for (int row = 0; row < hiddenField.length; row++) {
                    for (int col = 0; col < hiddenField[row].length; col++) {
                        if (hiddenField[row][col].equals(markMineCharacter)) {
                            totalMarkedCells++;
                            if (originalField[row][col].equals(mineChar)) {
                                markedMines++;
                            }
                        } else if (!hiddenField[row][col].equals(".")) {
                            openedSafeCells++;
                        }
                    }
                }

                if (markedMines == totalMines && totalMarkedCells == markedMines) {
                    gameWon = true;
                }

                if (openedSafeCells == totalSafeCells) {
                    gameWon = true;
                }

                if (gameWon) {
                    printField(hiddenField);
                    System.out.println("Congratulations! You found all the mines!");
                }
            }
        }
    }

    private void regenerateField(Minefield minefield, int safeI, int safeJ) {
        String[][] newField = new String[minefield.getHeight()][minefield.getLength()];
        String safeCell = minefield.getSafeCell();
        String mineCell = minefield.getMineCharacter();

        for (int i = 0; i < minefield.getHeight(); i++) {
            for (int j = 0; j < minefield.getLength(); j++) {
                newField[i][j] = safeCell;
            }
        }

        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < minefield.getMinesNumber()) {
            int i = random.nextInt(minefield.getHeight());
            int j = random.nextInt(minefield.getLength());

            if (i == safeI && j == safeJ) {
                continue;
            }

            if (newField[i][j].equals(safeCell)) {
                newField[i][j] = mineCell;
                minesPlaced++;
            }
        }
        minefield.setField(newField);
        setHints(minefield);
    }

    public void setHints(Minefield minefield) {
        String[][] field = minefield.getField();
        String safeCell = minefield.getSafeCell();
        String mineCell = minefield.getMineCharacter();

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                boolean upExists = i - 1 >= 0;
                boolean downExists = i + 1 < field.length;
                boolean leftExists = j - 1 >= 0;
                boolean rightExists = j + 1 < field[i].length;

                boolean upLeftDiagonalExists = upExists && leftExists;
                boolean upRightDiagonalExists = upExists && rightExists;
                boolean downLeftDiagonalExists = downExists && leftExists;
                boolean downRightDiagonalExists = downExists && rightExists;

                boolean isSafeCell = safeCell.equals(field[i][j]);

                if (isSafeCell) {
                    int minesAround = 0;
                    if (upExists) {
                        String upCell = field[i - 1][j];
                        if (upCell.equals(mineCell)) {
                            minesAround++;
                        }
                    }

                    if (downExists) {
                        String downCell = field[i + 1][j];
                        if (downCell.equals(mineCell)) {
                            minesAround++;
                        }
                    }

                    if (leftExists) {
                        String leftCell = field[i][j - 1];
                        if (leftCell.equals(mineCell)) {
                            minesAround++;
                        }
                    }

                    if (rightExists) {
                        String rightCell = field[i][j + 1];
                        if (rightCell.equals(mineCell)) {
                            minesAround++;
                        }
                    }

                    if (upLeftDiagonalExists) {
                        String upLeftDiagonalCell = field[i - 1][j - 1];
                        if (upLeftDiagonalCell.equals(mineCell)) {
                            minesAround++;
                        }
                    }

                    if(upRightDiagonalExists) {
                        String upRightDiagonalCell = field[i - 1][j + 1];
                        if (upRightDiagonalCell.equals(mineCell)) {
                            minesAround++;
                        }
                    }

                    if (downLeftDiagonalExists) {
                        String downLeftDiagonalCell = field[i + 1][j - 1];
                        if (downLeftDiagonalCell.equals(mineCell)) {
                            minesAround++;
                        }
                    }

                    if (downRightDiagonalExists) {
                        String downRightDiagonalCell = field[i + 1][j + 1];
                        if (downRightDiagonalCell.equals(mineCell)) {
                            minesAround++;
                        }
                    }

                    if (minesAround > 0) {
                        field[i][j] = String.valueOf(minesAround);
                    }
                }
            }
        }
    }

    public void printField(String[][] field) {
        printTop(field);
        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1 + "|");
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println("|");
        }
        printBottom(field);
    }

    private void printTop(String[][] field) {
        System.out.print(" |");
        for (int i = 0; i < field[0].length; i++) {
            System.out.print(i + 1);
        }
        System.out.println("|");

        printBottom(field);
    }

    private void printBottom(String[][] field) {
        System.out.print("-|");
        for (int i = 0; i < field[0].length; i++) {
            System.out.print("-");
        }
        System.out.println("|");
    }

    private void revealAllMines(String[][] hiddenField, String[][] originalField) {
        for (int i = 0; i < hiddenField.length; i++) {
            for (int j = 0; j < hiddenField[i].length; j++) {
                if (originalField[i][j].equals("X")) {
                    hiddenField[i][j] = "X";
                }
            }
        }
    }

    private void exploreCells(String[][] hiddenField, String[][] originalField, int i, int j, String emptyChar) {
        if (i < 0 || i >= originalField.length || j < 0 || j >= originalField[0].length) {
            return; // Out of bounds
        }

        if (!hiddenField[i][j].equals(".") && !hiddenField[i][j].equals("*")) {
            return; // Already explored
        }

        // If the cell was marked as a mine, unmark it first
        if (hiddenField[i][j].equals("*")) {
            hiddenField[i][j] = ".";
        }

        try {
            // Check if the cell contains a number (has mines around it)
            int minesAround = Integer.parseInt(originalField[i][j]);
            hiddenField[i][j] = String.valueOf(minesAround);
        } catch (NumberFormatException e) {
            // Not a number
            if (originalField[i][j].equals("X")) {
                // This is a mine, should not be explored
                return;
            }

            // Empty cell with no mines around, auto-explore
            hiddenField[i][j] = emptyChar;

            // Auto-explore adjacent cells
            exploreCells(hiddenField, originalField, i - 1, j, emptyChar); // Up
            exploreCells(hiddenField, originalField, i + 1, j, emptyChar); // Down
            exploreCells(hiddenField, originalField, i, j - 1, emptyChar); // Left
            exploreCells(hiddenField, originalField, i, j + 1, emptyChar); // Right

            exploreCells(hiddenField, originalField, i - 1, j - 1, emptyChar); // Up-Left
            exploreCells(hiddenField, originalField, i - 1, j + 1, emptyChar); // Up-Right
            exploreCells(hiddenField, originalField, i + 1, j - 1, emptyChar); // Down-Left
            exploreCells(hiddenField, originalField, i + 1, j + 1, emptyChar); // Down-Right
        }
    }
}

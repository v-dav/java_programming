package tetris;

import tetris.pieces.*;

import java.util.Arrays;
import java.util.Scanner;

public class GameController {

    private static final String EXIT = "exit";
    private static final String ROTATE = "rotate";
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String DOWN = "down";
    private static final String PIECE = "piece";
    private static final String BREAK = "break";

    public void start() {
        Scanner scanner = new Scanner(System.in);
        Piece piece = null;

        // take grid size input
        int gridWidth = scanner.nextInt();
        int gridHeight = scanner.nextInt();

        // consume the leftover newline
        scanner.nextLine();

        // print empty tetrisBoard
        TetrisBoard tetrisBoard = new TetrisBoard(gridWidth, gridHeight);
        tetrisBoard.printGrid();

        // play
        gameLoop:
        while (true) {
            String command = scanner.nextLine().toLowerCase();
            switch (command) {
                case PIECE:
                    String inputPiece = scanner.nextLine().toUpperCase();
                    piece = getRequestedPiece(inputPiece);
                    tetrisBoard.placePieceStateOnGrid(piece.getAllStates()[0]);
                    break;
                case ROTATE:
                    if (piece != null) {
                        rotate(piece, tetrisBoard);
                        moveDown(piece, tetrisBoard);
                    }
                    break;
                case LEFT:
                    if (piece != null) {
                        moveLeft(piece, tetrisBoard);
                        moveDown(piece, tetrisBoard);
                    }
                    break;
                case RIGHT:
                    if (piece != null) {
                        moveRight(piece, tetrisBoard);
                        moveDown(piece, tetrisBoard);
                    }
                    break;
                case DOWN:
                    if (piece != null) {
                        moveDown(piece, tetrisBoard);
                    }
                    break;
                case BREAK:
                    removeFilledRows(tetrisBoard);
                    piece = null;
                    break;
                case EXIT:
                    break gameLoop;
                default:
                    System.out.println("Unknown command");
                    break;
            }
            tetrisBoard.printGrid();
            if (isGameOver(tetrisBoard)) {
                System.out.println("Game Over!");
                break gameLoop;
            }
        }
    }

    private boolean isGameOver(TetrisBoard tetrisBoard) {
        String[][] matrix = tetrisBoard.getMatrix();
        int width = tetrisBoard.getWidth();

        for (int col = 0; col < width; col++) {
            boolean columnFilled = true;
            for (String[] row : matrix) {
                if (row[col].equals("- ")) {
                    columnFilled = false;
                    break;
                }
            }
            if (columnFilled) {
                return true;
            }
        }
        return false;
    }

    private void removeFilledRows(TetrisBoard tetrisBoard) {
        String[][] matrix = tetrisBoard.getMatrix();
        for (int i = 0; i < matrix.length; i++) {
            boolean rowFilled = true;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].equals("- ")) {
                    rowFilled = false;
                    break;
                }
            }
            if (rowFilled) {
                // Shift all rows above down by one position
                for (int row = i; row > 0; row--) {
                    System.arraycopy(matrix[row - 1], 0, matrix[row], 0, matrix[row].length);
                }
                // Fill the top row with empty cells
                Arrays.fill(matrix[0], "- ");
            }
        }
    }

    private void moveRight(Piece piece, TetrisBoard tetrisBoard) {
        if (canMove(piece, tetrisBoard, RIGHT)) {
            for (int i = 0; i < piece.getAllStates().length; i++) {
                for (int j = 0; j < piece.getAllStates()[i].length; j++) {
                    piece.getAllStates()[i][j] += 1;
                }
            }
            tetrisBoard.placePieceStateOnGrid(piece.getCurrentState());
        }
    }

    private boolean canMove(Piece piece, TetrisBoard tetrisBoard, String direction) {
        tetrisBoard.removePieceStateFromGrid(piece.getCurrentState());

        // First check all positions for floor and borders
        for (int position : piece.getCurrentState()) {
            int x = tetrisBoard.getXCoordinate(position);
            int y = tetrisBoard.getYCoordinate(position);

            if (isOnFloor(tetrisBoard, y)) {
                tetrisBoard.placePieceStateOnGrid(piece.getCurrentState());
                return false;
            }
            
            if (direction.equals(RIGHT) && x >= tetrisBoard.getRightBorder()) {
                tetrisBoard.placePieceStateOnGrid(piece.getCurrentState());
                return false;
            }

            if (direction.equals(LEFT) && x <= tetrisBoard.getLeftBorder()) {
                tetrisBoard.placePieceStateOnGrid(piece.getCurrentState());
                return false;
            }
        }

        // Only if no positions are on floor/borders, check for touching pieces
        if (touchesAnotherPiece(piece, tetrisBoard)) {
            tetrisBoard.placePieceStateOnGrid(piece.getCurrentState());
            return false;
        }

        return true;
    }

    private static boolean isOnFloor(TetrisBoard tetrisBoard, int y) {
        return y >= tetrisBoard.getFloor();
    }

    private static boolean touchesAnotherPiece(Piece piece, TetrisBoard tetrisBoard) {
        String blockSign = "0 ";

        for (int position : piece.getCurrentState()) {
            int x = tetrisBoard.getXCoordinate(position);
            int y = tetrisBoard.getYCoordinate(position);

            // Check below (y+1)
            if (y + 1 < tetrisBoard.getMatrix().length &&
                    blockSign.equals(tetrisBoard.getMatrix()[y + 1][x])) {
                return true;
            }

//            // Check right (x+1)
//            if (x + 1 < tetrisBoard.getMatrix()[0].length &&
//                    blockSign.equals(tetrisBoard.getMatrix()[y][x + 1])) {
//                return true;
//            }
//
//            // Check left (x-1)
//            if (x - 1 >= 0 &&
//                    blockSign.equals(tetrisBoard.getMatrix()[y][x - 1])) {
//                return true;
//            }

        }

        return false;
    }

    private void moveLeft(Piece piece, TetrisBoard tetrisBoard) {
        if (canMove(piece, tetrisBoard, LEFT)) {
            for (int i = 0; i < piece.getAllStates().length; i++) {
                for (int j = 0; j < piece.getAllStates()[i].length; j++) {
                    piece.getAllStates()[i][j] -= 1;
                }
            }
            tetrisBoard.placePieceStateOnGrid(piece.getCurrentState());
        }
    }

    private void rotate(Piece piece, TetrisBoard tetrisBoard) {
        if (canMove(piece, tetrisBoard, ROTATE)) {
            piece.rotate();
            tetrisBoard.placePieceStateOnGrid(piece.getCurrentState());
        }
    }

    private void moveDown(Piece piece, TetrisBoard tetrisBoard) {
        if (canMove(piece, tetrisBoard, DOWN)) {
            for (int i = 0; i < piece.getAllStates().length; i++) {
                for (int j = 0; j < piece.getAllStates()[i].length; j++) {
                    piece.getAllStates()[i][j] += 10;
                }
            }
            tetrisBoard.placePieceStateOnGrid(piece.getCurrentState());
        }
    }

    private Piece getRequestedPiece(String inputPiece) {
        return switch (inputPiece) {
            case "O" -> new Opiece();
            case "I" -> new Ipiece();
            case "S" -> new Spiece();
            case "Z" -> new Zpiece();
            case "L" -> new Lpiece();
            case "J" -> new Jpiece();
            case "T" -> new Tpiece();
            default -> throw new IllegalStateException("Unexpected value: " + inputPiece);
        };
    }
}

package four;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ConnectFour extends JFrame {
    private boolean isPlayerX = true;
    private boolean gameFinished = false;
    private final Cell[][] board = new Cell[7][6];

    public ConnectFour() {
        setTitle("Connect Four");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new BorderLayout());

        JPanel gamePanel = new JPanel(new GridLayout(6, 7, 0, 0));

        for (int row = 6; row >= 1; row--) {
            for (int col = 0; col < 7; col++) {
                char colLetter = (char)('A' + col);
                String position = colLetter + String.valueOf(row);
                Cell cell = new Cell(" ");
                cell.setName("Button" + position);

                final int column = col;
                cell.addActionListener(e -> dropPiece(column));

                board[col][6-row] = cell;
                gamePanel.add(cell);
            }
        }

        ButtonReset resetButton = new ButtonReset();
        resetButton.addActionListener(e -> resetGame());

        add(gamePanel, BorderLayout.CENTER);
        add(resetButton, BorderLayout.SOUTH);
        setVisible(true);
    }


    private void dropPiece(int column) {
        if (gameFinished) return;

        for (int row = 5; row >= 0; row--) {
            if (board[column][row].getText().equals(" ")) {
                board[column][row].setText(isPlayerX ? "X" : "O");
                if (checkWin(column, row)) {
                    gameFinished = true;
                } else {
                    isPlayerX = !isPlayerX;
                }
                break;
            }
        }
    }

    private boolean checkWin(int col, int row) {
        String player = board[col][row].getText();

        // Check all directions
        return checkDirection(col, row, player, 1, 0) ||  // Horizontal
                checkDirection(col, row, player, 0, 1) ||  // Vertical
                checkDirection(col, row, player, 1, 1) ||  // Diagonal \
                checkDirection(col, row, player, 1, -1);   // Diagonal /
    }

    private boolean checkDirection(int col, int row, String player, int deltaCol, int deltaRow) {
        int count = 1;
        int[][] winCells = new int[4][2];
        winCells[0] = new int[]{col, row};

        // Check positive direction
        for (int i = 1; i < 4; i++) {
            int newCol = col + i * deltaCol;
            int newRow = row + i * deltaRow;
            if (newCol >= 0 && newCol < 7 && newRow >= 0 && newRow < 6 &&
                    board[newCol][newRow].getText().equals(player)) {
                winCells[count++] = new int[]{newCol, newRow};
            } else break;
        }

        // Check negative direction
        for (int i = 1; count < 4 && i < 4; i++) {
            int newCol = col - i * deltaCol;
            int newRow = row - i * deltaRow;
            if (newCol >= 0 && newCol < 7 && newRow >= 0 && newRow < 6 &&
                    board[newCol][newRow].getText().equals(player)) {
                // Insert at beginning and shift others
                for (int j = count; j > 0; j--) {
                    winCells[j] = winCells[j-1];
                }
                winCells[0] = new int[]{newCol, newRow};
                count++;
            } else break;
        }

        if (count >= 4) {
            for (int i = 0; i < 4; i++) {
                board[winCells[i][0]][winCells[i][1]].setBackground(Color.CYAN);
            }
            return true;
        }
        return false;
    }

    private void resetGame() {
        gameFinished = false;
        isPlayerX = true;
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row < 6; row++) {
                board[col][row].setText(" ");
                board[col][row].setBackground(new Color(144, 188, 144));
            }
        }
    }
}
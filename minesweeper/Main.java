package minesweeper;

public class Main {
    static GameManager gameManager = new GameManager();

    public static void main(String[] args) {
        Minefield minefield = gameManager.initializeGame();
        gameManager.startGameV2(minefield);
    }
}

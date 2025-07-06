package tetris;

public class TetrisBoard {

    private final int width;
    private final int height;
    private String[][] matrix;
    private int leftBorder;
    private int rightBorder;
    private int floor;

    public TetrisBoard() {
        this.width = 10;
        this.height = 20;
        fillMatrix();
        setUpEdges();
    }

    private void setUpEdges() {
        leftBorder = 0;
        rightBorder = width - 1;
        floor = height - 1;
    }

    public TetrisBoard(int width, int height) {
        this.width = width;
        this.height = height;
        fillMatrix();
        setUpEdges();
    }

    public int getWidth() {
        return width;
    }

    private void fillMatrix() {
        matrix = new String[height][width];
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                matrix[i][j] = "- ";
            }
        }
    }

    public void printGrid() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void placePieceStateOnGrid(int[] state) {
        for (int number : state) {
            int y = getYCoordinate(number);
            int x = getXCoordinate(number);
            try {
                this.matrix[y][x] = "0 ";
            } catch (ArrayIndexOutOfBoundsException ignored) {
                // do nothing
            }
        }
    }

    int getXCoordinate(int number) {
        return number % this.width;
    }

    int getYCoordinate(int number) {
        return number / this.width;
    }

    public int getLeftBorder() {
        return leftBorder;
    }

    public int getRightBorder() {
        return rightBorder;
    }

    public int getFloor() {
        return floor;
    }

    public void removePieceStateFromGrid(int[] state) {
        for (int number : state) {
            int y = getYCoordinate(number);
            int x = getXCoordinate(number);
            try {
                this.matrix[y][x] = "- ";
            } catch (ArrayIndexOutOfBoundsException ignored) {
                // do nothing
            }
        }
    }

    public String[][] getMatrix() {
        return matrix;
    }
}

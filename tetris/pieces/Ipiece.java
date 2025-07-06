package tetris.pieces;

public class Ipiece extends Piece {

    public Ipiece() {
        this.initialState = new int[]{4, 14, 24, 34};
        this.rotation90 = new int[]{3, 4, 5, 6};
        this.allStates = new int[][] {initialState, rotation90};
        this.currentState = allStates[currentStateIndex];
    }
}

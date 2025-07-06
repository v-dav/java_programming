package tetris.pieces;

public class Spiece extends Piece {

    public Spiece() {
        this.initialState = new int[]{5, 4, 14, 13};
        this.rotation90 = new int[]{4, 14, 15, 25};
        this.allStates = new int[][] {initialState, rotation90};
        this.currentState = allStates[currentStateIndex];
    }
}

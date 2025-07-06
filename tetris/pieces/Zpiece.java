package tetris.pieces;

public class Zpiece extends Piece{

    public Zpiece() {
        this.initialState = new int[]{4, 5, 15, 16};
        this.rotation90 = new int[]{5, 15, 14, 24};
        this.allStates = new int[][] {initialState, rotation90};
        this.currentState = allStates[currentStateIndex];
    }
}

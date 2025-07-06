package tetris.pieces;

public class Opiece extends Piece {

    public Opiece() {
        this.initialState = new int[]{4, 14, 15, 5};
        this.allStates = new int[][] {initialState};
        this.currentState = allStates[currentStateIndex];
    }
}

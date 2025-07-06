package tetris.pieces;

public class Lpiece extends Piece {

    public Lpiece() {
        this.initialState = new int[]{4, 14, 24, 25};
        this.rotation90 = new int[]{5, 15, 14, 13};
        this.rotation180 = new int[]{4, 5, 15, 25};
        this.rotation270 = new int[]{6, 5, 4, 14};
        initializeAllStates();
        this.currentState = allStates[currentStateIndex];
    }
}

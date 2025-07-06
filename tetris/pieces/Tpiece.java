package tetris.pieces;

public class Tpiece extends Piece{

    public Tpiece() {
        this.initialState = new int[]{4, 14, 24, 15};
        this.rotation90 = new int[]{4, 13, 14, 15};
        this.rotation180 = new int[]{5, 15, 25, 14};
        this.rotation270 = new int[]{4, 5, 6, 15};
        initializeAllStates();
        this.currentState = allStates[currentStateIndex];
    }
}

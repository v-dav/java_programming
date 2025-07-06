package tetris.pieces;

public class Jpiece extends Piece{

    public Jpiece() {
        this.initialState = new int[]{5, 15, 25, 24};
        this.rotation90 = new int[]{15, 5, 4, 3};
        this.rotation180 = new int[]{5, 4, 14, 24};
        this.rotation270 = new int[]{4, 14, 15, 16};
        initializeAllStates();
        this.currentState = allStates[currentStateIndex];
    }
}

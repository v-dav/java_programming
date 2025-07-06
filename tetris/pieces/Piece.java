package tetris.pieces;

public abstract class Piece {

    protected int[] initialState;
    protected int[] rotation90;
    protected int[] rotation180;
    protected int[] rotation270;
    protected int[][] allStates;
    protected int currentStateIndex = 0;
    protected int[] currentState;

    protected void initializeAllStates() {
        this.allStates = new int[][] {
                initialState,
                rotation90,
                rotation180,
                rotation270
        };
    }

    public int[][] getAllStates() {
        return allStates;
    }

    public int[] getCurrentState() {
        return currentState;
    }

    public void rotate() {
        if (currentStateIndex < allStates.length - 1) {
            currentStateIndex++;
        } else {
            currentStateIndex = 0;
        }
        currentState = allStates[currentStateIndex];
    }
}

package minesweeper;

import java.util.Random;

public class Minefield {

    private int length;
    private int height;
    private int minesNumber;
    private String mineCharacter;
    private String safeCell;
    private String[][] field;
    private int safeCellNumber;
    private String[][] unexploredCopy;

    private Minefield(int length, int height, int minesNumber, String mineCharacter, String safeCell) {
        this.length = length;
        this.height = height;
        this.minesNumber = minesNumber;
        this.mineCharacter = mineCharacter;
        this.safeCell = safeCell;
        field = setField();
        safeCellNumber = (length * height) - minesNumber;
        unexploredCopy = createUnexploredCopy();
    }

    private String[][] createUnexploredCopy() {
        String[][] clone = new String[height][length];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                clone[i][j] = ".";
            }
        }
        return clone;
    }

    static class Builder {
        int length;
        int height;
        int minesNumber;
        String mineCharacter;
        String safeCell;

        Builder() {}

        Builder setLength(int length) {
            this.length = length;
            return this;
        }

        Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        Builder setMinesNumber(int minesNumber) {
            this.minesNumber = minesNumber;
            return this;
        }

        Builder setMineCharacter(String mineCharacter) {
            this.mineCharacter = mineCharacter;
            return this;
        }

        Builder setSafeCell(String safeCell) {
            this.safeCell = safeCell;
            return this;
        }

        Minefield build() {
            return new Minefield(length, height, minesNumber, mineCharacter, safeCell);
        }
    }

    private String[][] setField() {
        String[][] newField = new String[height][length];
        Random random = new Random();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                newField[i][j] = ".";
            }
        }

        int landedMines = 0;
        int xPosition;
        int yPosition;

        while (landedMines < minesNumber) {
            xPosition = random.nextInt(length);
            yPosition = random.nextInt(height);
            if (!mineCharacter.equals(newField[yPosition][xPosition])) {
                newField[yPosition][xPosition] = mineCharacter;
                landedMines++;
            }
        }
        return newField;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMinesNumber() {
        return minesNumber;
    }

    public void setMinesNumber(int minesNumber) {
        this.minesNumber = minesNumber;
    }

    public String getMineCharacter() {
        return mineCharacter;
    }

    public void setMineCharacter(String mineCharacter) {
        this.mineCharacter = mineCharacter;
    }

    public String getSafeCell() {
        return safeCell;
    }

    public void setSafeCell(String safeCell) {
        this.safeCell = safeCell;
    }

    public String[][] getField() {
        return field;
    }

    public void setField(String[][] field) {
        this.field = field;
    }

    public int getSafeCellNumber() {
        return safeCellNumber;
    }

    public void setSafeCellNumber(int safeCellNumber) {
        this.safeCellNumber = safeCellNumber;
    }

    public String[][] getUnexploredCopy() {
        return unexploredCopy;
    }

    public void setUnexploredCopy(String[][] unexploredCopy) {
        this.unexploredCopy = unexploredCopy;
    }
}

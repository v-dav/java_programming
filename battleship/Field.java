package battleship;

public class Field {
    private String[][] field;
    private String[][] fogOfWar;

    public Field(int row, int col) {
        field = new String[row][col];
        fogOfWar = new String[row][col];
        populateField(field);
        populateField(fogOfWar);
    }

    public void printField(String[][] field) {
        for (String[] strings : field) {
            for (int j = 0; j < strings.length; j++) {
                System.out.print(strings[j]);
                if (j != strings.length - 1) {
                    System.out.print(" ");
                } else {
                    System.out.println();
                }
            }
        }
    }

    public String[][] getField() {
        return this.field;
    }

    public String[][] getFogOfWar() {
        return this.fogOfWar;
    }

    private void populateField(String [][] field) {
        String alphabet = "ABCDEFGHIJ";

        // Populate programmatically the field
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (i == 0 && j == 0) {
                    field[i][j] = " ";
                } else if (i == 0) {
                    field[i][j] = Integer.toString(j);
                } else if (i > 0 && j == 0) {
                    field[i][j] = Character.toString(alphabet.charAt(i - 1));
                } else {
                    field[i][j] = "~";
                }
            }
        }
    }
}

package battleship;

import java.util.Scanner;

public class Ship {
    protected int expectedLength;
    protected String shipName = "";

    protected int length;
    protected String[] coordinatesStart;
    protected String[] coordinatesEnd;
    protected String parts;

    protected int health;
    protected boolean alive = true;

    public Ship(String shipName, int expectedLength, Field field) {
        // Request ship coordinates
        Scanner scanner = new Scanner(System.in);
        System.out.println("\033[0;33m" + "\nEnter the coordinates of the " + shipName + " (" + expectedLength + " cells):" + "\033[0m");
        String[] start = null;
        String[] end = null;

        while (true) {
            String coordinatesInput = scanner.nextLine();
            boolean falseInput = false;

            // Initialize instance
            this.expectedLength = expectedLength;
            this.shipName = shipName;

            // Process ship coordinates
            String[] coordinatesArray = coordinatesInput.split(" ");
            try {
                start = coordinatesArray[0].split("", 2);
                end = coordinatesArray[1].split("", 2);
            } catch (ArrayIndexOutOfBoundsException e) {
                falseInput = true;
            }

            if (!falseInput && validCoordinates(start, end)) {
                this.coordinatesStart = start;
                this.coordinatesEnd = end;
                this.length = calculateLength(coordinatesStart, coordinatesEnd);
                this.health = this.length;
                if (this.length != this.expectedLength) {
                    System.out.println("\033[0;31m" + "\nError! Wrong length of the " + this.shipName + "! Try again:" + "\033[0m");
                    continue;
                }
                this.parts = computeParts(coordinatesStart, coordinatesEnd, length);
                if (placedOnField(field, parts)) {
                    System.out.println();
                    field.printField(field.getField());
                    break;
                } else {
                    System.out.println("\033[0;31m" + "\nError! You placed it too close to another one. Try again:" + "\033[0m");
                }
            } else {
                System.out.println("\033[0;31m" + "\nError! Wrong ship location! Try again:" +"\033[0m");
            }
        }
    }

    private boolean validCoordinates(String[] start, String[] end) {
        String alphabet = "ABCDEFGHIJ";
        String startY = start[0];
        String endY = end[0];
        int startX;
        int endX;

        // Y-axis bounds
        if (!alphabet.contains(startY) || !alphabet.contains(endY)) {
            return false;
        }

        // X-axis bounds
        try {
            startX = Integer.parseInt(start[1]);
            endX = Integer.parseInt(end[1]);
            if (startX <= 0 || startX > 10 || endX <= 0 || endX > 10) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        // Diagonal verification
        boolean horizontal = startY.equals(endY);
        boolean vertical = startX == endX;
        boolean point = horizontal && vertical;

        if (!horizontal && !vertical && !point) {
            return false;
        }
        return true;
    }

    private int calculateLength(String[] start, String[] end) {
        String alphabet = "ABCDEFGHIJ";
        String startY = start[0];
        String endY = end[0];
        int startX = Integer.parseInt(start[1]);
        int endX = Integer.parseInt(end[1]);
        int length = 0;

        boolean horizontal = startY.equals(endY);
        boolean vertical = startX == endX;
        boolean point = horizontal && vertical;

        if (point) {
            length = 1;
        } else if (horizontal) {
            int max = Math.max(startX, endX);
            int min = Math.min(startX, endX);
            length = max - min + 1;
        } else if (vertical) {
            int startYint = alphabet.indexOf(startY);
            int endYint = alphabet.indexOf(endY);
            int max = Math.max(startYint, endYint);
            int min = Math.min(startYint, endYint);
            length = max - min + 1;
        }

        return length;
    }

    private String computeParts(String[] start, String[] end, int length) {
        String alphabet = "ABCDEFGHIJ";
        String startY = start[0];
        String endY = end[0];
        int startX = Integer.parseInt(start[1]);
        int endX = Integer.parseInt(end[1]);
        int max = Math.max(startX, endX);
        int min = Math.min(startX, endX);

        StringBuilder parts = new StringBuilder();

        boolean horizontal = startY.equals(endY);
        boolean vertical = startX == endX;
        boolean point = horizontal && vertical;

        if (horizontal) {
            for (int i = min; i <= max; i++) {
                parts.append(startY).append(i);
                if (i != max) {
                    parts.append(" ");
                }
            }
        } else if (vertical) {
            int indexStartY = alphabet.indexOf(startY);
            int indexEndY = alphabet.indexOf(endY);
            int minY = Math.min(indexStartY, indexEndY);

            for (int i = startX, j = 0; i <= length + startX - 1 && j < alphabet.length(); i++, j++) {
                parts.append(alphabet.charAt(minY + j)).append(startX);
                if (i != length + startX) {
                    parts.append(" ");
                }
            }

        } else {
            parts.append(startY).append(startX);
        }

        return parts.toString();
    }

    private boolean placedOnField(Field field, String parts) {
        String[][] fieldMatrix = field.getField();
        String alphabet = "ABCDEFGHIJ";
        String[] partsArray = parts.split(" ");
        String firstChar = String.valueOf(partsArray[0].charAt(0));
        boolean horizontal = true;
        for (String part : partsArray) {
            if (!String.valueOf(part.charAt(0)).equals(firstChar)) {
                horizontal = false;
                break;
            }
        }
        int Xcoord;
        if (!horizontal && partsArray[partsArray.length - 1].length() == 3) {
            Xcoord = 10;
        } else {
            Xcoord = Character.getNumericValue(partsArray[0].charAt(1));
        }

        boolean headOfTheShip = false;

        // Place verification
        if (horizontal) {
            for (int j = Xcoord; j < partsArray.length + Xcoord; j++) {
                headOfTheShip = j == Xcoord;
                if (isValidPlace(alphabet.indexOf(firstChar) + 1, j, fieldMatrix, headOfTheShip, true)) {
                    continue;
                } else {
                    return false;
                }
            }
        } else {
            for (int i = alphabet.indexOf(firstChar) + 1; i < partsArray.length + alphabet.indexOf(firstChar) + 1; i++) {
                headOfTheShip = i == alphabet.indexOf(firstChar) + 1;
                if (isValidPlace(i, Xcoord, fieldMatrix, headOfTheShip, false)) {
                    continue;
                } else {
                    return false;
                }
            }
        }

        // Core placement
        if (horizontal) {
            for (int j = Xcoord; j < partsArray.length + Xcoord; j++) {
                fieldMatrix[alphabet.indexOf(firstChar) + 1][j] = "O";
            }
        } else {
            for (int i = alphabet.indexOf(firstChar) + 1; i < partsArray.length + alphabet.indexOf(firstChar) + 1; i++) {
                fieldMatrix[i][Xcoord] = "O";
            }
        }

        return true;
    }

    private boolean isValidPlace(int i, int j, String[][] fieldMatrix, boolean headOfTheShip, boolean horizontal) {
        boolean center0 = fieldMatrix[i][j].equals("O");
        boolean above0 = fieldMatrix[i - 1][j].equals("O");
        boolean right0;
        boolean below0;

        if (j == 10) {
            right0 = false;
        } else {
            right0 = fieldMatrix[i][j + 1].equals("O");
        }
        if (i == 10) {
            below0 = false;
        } else {
            below0 = fieldMatrix[i + 1][j].equals("O");
        }

        boolean left0 = fieldMatrix[i][j - 1].equals("O");

        if (horizontal && headOfTheShip && (center0 || above0 || right0 || below0 || left0)) {
            return false;
        }

        if (horizontal && !headOfTheShip && (center0 || above0 || right0 || below0)) {
            return false;
        }

        if (!horizontal && headOfTheShip && (center0 || above0 || right0 || below0 || left0)) {
            return false;
        }

        if (!horizontal && !headOfTheShip && (center0 || right0 || below0)) {
            return false;
        }
//
//        if () {
//            System.out.println(i + " " + j + " coordinate");
//            System.out.println("Adjacent! Center %b, Above %b, Right0 %b, Below %b, Left0 %b".formatted(center0, above0, right0, below0, left0));
//            return false;
//        }
        return true;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String[] getParts() {
        return this.parts.split(" ");
    }

    public void diminishHealth() {
        this.health--;
    }
}


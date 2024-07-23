package battleship;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // First players turn
        System.out.println("\n\033[0;32m" + "Player 1, place your ships on the game field\n" + "\033[0m");
        Field field1 = new Field(11, 11);
        field1.printField(field1.getField());

        // Create new ships and place on field1
        AircraftCarrier aircraftCarrier = new AircraftCarrier(field1);
        Battleship battleship = new Battleship(field1);
        Submarine submarine = new Submarine(field1);
        Cruiser cruiser = new Cruiser(field1);
        Destroyer destroyer = new Destroyer(field1);

        // Initialize fleet 1
        Fleet fleet1 = new Fleet(aircraftCarrier, battleship, submarine, cruiser, destroyer);

        askTakeTurn();

        // Second players turn
        System.out.println("\n\033[0;32m" + "Player 2, place your ships on the game field\n" + "\033[0m");

        Field field2 = new Field(11, 11);
        field2.printField(field2.getField());

        AircraftCarrier aircraftCarrier2 = new AircraftCarrier(field2);
        Battleship battleship2 = new Battleship(field2);
        Submarine submarine2 = new Submarine(field2);
        Cruiser cruiser2 = new Cruiser(field2);
        Destroyer destroyer2 = new Destroyer(field2);

        // Initialize fleet
        Fleet fleet2 = new Fleet(aircraftCarrier2, battleship2, submarine2, cruiser2, destroyer2);

        askTakeTurn();

        // Shooting
        shoot(field1, fleet1, field2, fleet2);
    }

    private static void shoot(Field field1, Fleet fleet1, Field field2, Fleet fleet2) {
        String[][] fieldMap1 = field1.getField();
        String[][] fieldMap2 = field2.getField();

        boolean fleet1IsAlive = true;
        boolean fleet2IsAlive = true;

        String player;
        boolean firstPlayersTurn = true;

        String alphabet = "ABCDEFGHIJ";
        Scanner scanner = new Scanner(System.in);
        int x;
        String y;

        // Game logic here
        while (fleet1IsAlive && fleet2IsAlive) {
            // Print non modified fog of war, separator and players open field
            field1.printField(field1.getFogOfWar());
            System.out.println("---------------------");
            if (firstPlayersTurn) {
                field1.printField(fieldMap1);
            } else {
                field2.printField(fieldMap2);
            }
            System.out.println();

            // Display which player must play
            player = firstPlayersTurn ? "Player 1" : "Player 2";
            System.out.println("\033[0;32m" + player + ", it's your turn:\n" + "\033[0m");

            // Coordinates validation
            while (true) {
                String shootCoordinate = scanner.nextLine();
                y = String.valueOf(shootCoordinate.charAt(0));
                String xString = shootCoordinate.substring(1);

                try {
                    x = Integer.parseInt(xString);
                    if (!alphabet.contains(y) || x > 10 || x < 1) {
                        throw new NumberFormatException();
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error! You entered the wrong coordinates! Try again:");
                }
            }

            // Shoot validation and modification of the opponent field
            if (firstPlayersTurn) {
                validateHitAndModifyFieldMap(fieldMap2, fleet2, y, x);
            } else {
                validateHitAndModifyFieldMap(fieldMap1, fleet1, y, x);
            }
            fleet1IsAlive = fleet1.isAlive();
            fleet2IsAlive = fleet2.isAlive();
            if (fleet1IsAlive && fleet2IsAlive) {
                askTakeTurn();
            }
            firstPlayersTurn = !firstPlayersTurn;
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    private static void askTakeTurn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter and pass the move to another player\n");
        scanner.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void validateHitAndModifyFieldMap(String[][] fieldMap, Fleet fleet, String y, int x) {
        String alphabet = "ABCDEFGHIJ";

        // Convert shoot to field coordinates
        int i = alphabet.indexOf(y) + 1;
        int j = x;

        if (fieldMap[i][j].equals("~") || fieldMap[i][j].equals("M")) {
            fieldMap[i][j] = "M";
//                fogOfWar[i][j] = "M";
            System.out.println("You missed!");
        } else if (fieldMap[i][j].equals("O") || fieldMap[i][j].equals("X")) {
            fieldMap[i][j] = "X";
//                fogOfWar[i][j] = "X";
            Ship hitShip = fleet.hitShip(y, x);
            if (hitShip.isAlive()) {
                System.out.println("You hit a ship!");
            } else {
                System.out.println("You sank a ship!");
            }
        }
    }
}

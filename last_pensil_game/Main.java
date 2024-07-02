import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String onePencil = "|";
        String player = "John";
        String bot = "Jack";
        String botPosition = "";

        int numberOfPencils;
        String enteredName;
        int takeOut;

        numberOfPencils = getNumberOfPencils();
        enteredName = getFirstPlayerName(player, bot);

        // Print first turn
        String playerToPlay = enteredName.equals(player) ? player : bot;
        System.out.println(onePencil.repeat(numberOfPencils));
        System.out.println(playerToPlay + "'s" + " turn");
        botPosition = playerToPlay.equals(bot) ? "first" : "second";

        // Pencils validation and main game
        while (true) {
            try {
                takeOut = playerToPlay.equals(player) ? Integer.parseInt(scanner.nextLine()) :
                        botMove(botPosition, numberOfPencils);
                if (playerToPlay.equals(bot)) {
			thinkingAnimation();
                    System.out.println(takeOut);
                }
                if (takeOut < 1 || takeOut > 3) {
                    System.out.println("Possible values: '1', '2' or '3'");
                } else if (takeOut > numberOfPencils) {
                    System.out.println("Too many pencils were taken");
                } else {
                    numberOfPencils -= takeOut;
                    if (numberOfPencils > 0) {
                        System.out.println(onePencil.repeat(numberOfPencils));
                        playerToPlay = playerToPlay.equals(player) ? bot : player;
                        System.out.println(playerToPlay + "'s" + " turn");
                    } else {
                        String winner = playerToPlay.equals(player) ? bot : player;
                        System.out.println(winner + " won!");
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Possible values: '1', '2' or '3'");
            }
        }
        scanner.close();
    }

    public static int getNumberOfPencils() {
        Scanner scanner = new Scanner(System.in);
        int numberOfPencils;

        // Prompt the player about pencils
        System.out.println("How many pencils would you like to use:");
        while (true) {
            try {
                numberOfPencils = Integer.parseInt(scanner.nextLine());
                if (numberOfPencils == 0) {
                    System.out.println("The number of pencils should be positive");
                } else if (numberOfPencils < 0) {
                    System.out.println("The number of pencils should be numeric");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("The number of pencils should be numeric");
            }
        }
        return numberOfPencils;
    }

    public static String getFirstPlayerName(String player, String bot) {
        Scanner scanner = new Scanner(System.in);
        String enteredName;
        // Prompt about players names
        System.out.println("Who will be the first (John, Jack):");
        while (true) {
            enteredName = scanner.nextLine();
            if (!enteredName.equals(player) && !enteredName.equals(bot)) {
                System.out.println("Choose between " + player + " and " + bot);
            } else {
                break;
            }
        }
        return enteredName;
    }

    public static int botMove(String position, int numberOfPencils) {
        Random random = new Random();
        if (numberOfPencils >= 3) {
            return numberOfPencils % 4 == 0 ? 3 : numberOfPencils % 2 == 0 ? 1 : 2;
        } else {
            return random.nextInt(1) + 1;
        }
    }

    public static void thinkingAnimation() {
        System.out.print("Jack is thinking");
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500); // Wait for 500 milliseconds
                System.out.print(".");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(); // Move to the next line after animation
    }
}

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String codeLengthInput = "";
        String symbolLengthInput = "";
        try {
            System.out.println("Input the length of the secret code:");
            codeLengthInput = scanner.nextLine();
            int codeLength = Integer.parseInt(codeLengthInput);

            System.out.println("Input the number of possible symbols in the code:");
            symbolLengthInput = scanner.nextLine();
            int symbolLength = Integer.parseInt(symbolLengthInput);

            if (codeLength > symbolLength || codeLength <= 0 || symbolLength <= 0) {
                System.out.println("Error: it's not possible to generate a code with a length of "+
                        codeLength + " with " + symbolLength + " unique symbols.");
                return;
            }

            String secretCode = generateSecretCode(codeLength, symbolLength);

            if (secretCode.isEmpty()) {
                return;
            }

            mainGame(secretCode, symbolLength);

        }  catch (NumberFormatException e) {
            // Determine which input caused the exception
            if (!isInteger(codeLengthInput)) {
                System.out.println("Error: \"" + codeLengthInput + "\" isn't a valid number.");
            } else if (!isInteger(symbolLengthInput)) {
                System.out.println("Error: \"" + symbolLengthInput + "\" isn't a valid number.");
            }
        } finally {
            scanner.close();
        }
    }

    // Helper method to check if a string can be parsed as an integer
    private static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void printOutput(int bulls, int cows) {
        // Format and output result
        String bullsString = bulls == 1 ? bulls + " bull" : bulls > 0 ? bulls + " bulls" : "";
        String cowsString = cows == 1 ? cows + " cow" : cows > 0 ? cows + " cows" : "";
        String formattedString = bulls == 0 && cows == 0 ? "None." :
                (bulls > 0 && cows == 0 ? bullsString :
                        (cows > 0 && bulls == 0 ? cowsString : bullsString + " and " + cowsString));
        String responseString = "Grade: " + formattedString;
        System.out.println(responseString);
    }

    public static int[] countBullsAndCows(char[] inputArray, char[] secretCodeArray) {
        // Create game variables
        int bulls = 0;
        int cows = 0;
        boolean[] bullsMatched = new boolean[inputArray.length];
        boolean[] cowsMatched = new boolean[inputArray.length];

        // Count bulls
        for (int i = 0; i < inputArray.length; i++) {
            if (inputArray[i] == secretCodeArray[i]) {
                bulls++;
                bullsMatched[i] = true;
                cowsMatched[i] = true;
            }
        }

        // Count cows
        for (int i = 0; i < inputArray.length; i++) {
            if (!bullsMatched[i]) {
                for (int j = 0; j < secretCodeArray.length; j++) {
                    if (!cowsMatched[j] && inputArray[i] == secretCodeArray[j]) {
                        cowsMatched[j] = true;
                        cows++;
                        break;
                    }
                }
            }
        }

        return new int[]{bulls, cows};
    }

    public static String generateSecretCode(int codeLength, int symbolLength) {
        StringBuilder secretCode = new StringBuilder();

        if (symbolLength > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
        } else {
            //Big random long
            Random random = new Random();
            long min = 1_000_000_000_000_000_000L;
            long max = Long.MAX_VALUE;
            long pseudoRandomNumber = random.nextLong(max - min) + min;
            String pseudoRandomNumberString = Long.toString(pseudoRandomNumber);

            // Alphabet part
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            String alphabetSubstring = alphabet.substring(0, symbolLength - 10);
            StringBuilder secretCodeBuilder = new StringBuilder(pseudoRandomNumberString);
            secretCodeBuilder.append(alphabetSubstring);

            int secretCodeBuilderLength = secretCodeBuilder.length();
            Random randomIndex = new Random();

            for (int i = 0; secretCode.length() < codeLength && i < secretCodeBuilderLength; i++) {
                String character = Character.toString(secretCodeBuilder.charAt(randomIndex.nextInt(secretCodeBuilderLength)));
                if (!secretCode.toString().contains(character)) {
                    secretCode.append(character);
                }
            }
        }

        return secretCode.toString();
    }

    public static void mainGame(String secretCode, int symbolLength) {
        // Create game variables
        Scanner scanner = new Scanner(System.in);
        char[] secretCodeArray = secretCode.toCharArray();
        int bulls = 0;
        int cows = 0;
        int turn = 1;
        StringBuilder hiddenCode = new StringBuilder();
        StringBuilder letters = new StringBuilder();
        int lastCharIndex = symbolLength <= 10 ? -1 : symbolLength - 10 - 1;
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        if (lastCharIndex > 0) {
            String lastLetter = Character.toString(alphabet.charAt(lastCharIndex));
            letters.append(", a-").append(lastLetter).append(").");
        }

        // Start the game
        String alphabetPart = lastCharIndex < 0 ? ")." : lastCharIndex == 0 ? ", a)." : letters.toString();
        hiddenCode.append("*".repeat(secretCodeArray.length));
        System.out.println("The secret is prepared: " + hiddenCode + " (0-9" + alphabetPart);
        System.out.println("Okay, let's start a game!");

        // Game loop
        while (bulls != secretCodeArray.length) {
            System.out.println("Turn " + turn + ":");
            String input = scanner.nextLine();
            char[] inputArray = input.toCharArray();
            int[] turnResults = countBullsAndCows(inputArray, secretCodeArray);
            // Count bulls and cows
            bulls = turnResults[0];
            cows = turnResults[1];
            printOutput(bulls, cows);
            if (bulls == secretCodeArray.length) {
                System.out.println("Congratulations! You guessed the secret code.");
            }
            turn++;
        }
    }
}

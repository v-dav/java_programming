import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        label:
        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            String operation = scanner.nextLine();
            switch (operation) {
                case "exit":
                    System.out.println("Bye!");
                    break label;
                case "encode":
                    encode();
                    break;
                case "decode":
                    decode();
                    break;
                default:
                    System.out.println("There is no '" + operation + "' operation");
                    break;
            }
        }
    }

    public static String fromChuckToBinary(String input) {
        StringBuilder output = new StringBuilder();
        String[] array = input.split(" ");
        boolean ones = false;

        for (int i = 0; i < array.length; i++) {
            if (i % 2 == 0) { // prefix string
                if (array[i].equals("0")) {
                    ones = true;
                } else if (array[i].equals("00")) {
                    ones = false;
                }
            } else {
                String zerosOrOnes = array[i]; // counter string
                if (ones) {
                    output.append("1".repeat(zerosOrOnes.length()));
                } else {
                    output.append("0".repeat(zerosOrOnes.length()));
                }
            }
        }
        return output.toString();
    }

    public static String[] fromBinaryToBinary7bits(String input) {
        int length = input.length();
        int numOfSubstrings = (int) Math.ceil((double) length / 7);
        String[] parts = new String[numOfSubstrings];

        for (int i = 0; i < numOfSubstrings; i++) {
            int start = i * 7;
            int end = Math.min(start + 7, length);
            parts[i] = input.substring(start, end);
        }

        return parts;
    }

    public static int fromBinaryToInteger(String input) {
        return Integer.parseInt(input, 2);
    }

    public static char fromIntegerToChar(int input) {
        return (char) (input);
    }

    public static void decode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input encoded string:");
        String input = scanner.nextLine();

        if (!isValidInput(input)) {
            System.out.println("Encoded string is not valid.");
            return;
        }

        String binaryString = fromChuckToBinary(input);

        String[] binaryChars = fromBinaryToBinary7bits(binaryString);

        int[] asciiInts = new int[binaryChars.length];
        for (int i = 0; i < binaryChars.length; i++) {
            asciiInts[i] = fromBinaryToInteger(binaryChars[i]);
        }

        System.out.println("Decoded string:");
        for (int ascii : asciiInts) {
            System.out.print(fromIntegerToChar(ascii));
        }
        System.out.println();
    }

    public static void encode() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input string:");
        String input = scanner.nextLine();


        String binary7EncodedInput = encodeStringToBinary7(input);
        System.out.println("Encoded string:\n" + encodeToChuckNorris(binary7EncodedInput + "\n"));
    }

    public static String encodeStringToBinary7(String inputString) {
        StringBuilder binaryEncodedString7 = new StringBuilder();

        for (int i = 0; i < inputString.length(); i++) {
            char ch = inputString.charAt(i);
            binaryEncodedString7.append(convertToBinary7(ch));
        }
        return binaryEncodedString7.toString();
    }

    public static String convertToBinary7(char letter) {
        String binary8 = Integer.toBinaryString((int) letter);
        String binary7 = String.format("%7s", binary8).replace(" ", "0");
        return binary7;
    }

    public static String encodeToChuckNorris(String binaryString) {
        StringBuilder chuckString = new StringBuilder();
        boolean inSameGroup = false;

        for (int i = 0; i < binaryString.length() -1; i++) {
            char ch = binaryString.charAt(i);

            if (ch == '1') {
                // Check if we are in the same group of 1s
                if (i - 1 >= 0 && binaryString.charAt(i - 1) == '1') {
                    inSameGroup = true;
                } else {
                    inSameGroup = false;
                }

                // If we are starting a new group of 1s
                if (!inSameGroup) {
                    if (chuckString.length() > 0) {
                        chuckString.append(" ");
                    }
                    chuckString.append("0 ");
                }

                chuckString.append("0");
            } else {
                // Check if we are in the same group of 0s
                if (i - 1 >= 0 && binaryString.charAt(i - 1) == '0') {
                    inSameGroup = true;
                } else {
                    inSameGroup = false;
                }

                // If we are starting a new group of 0s
                if (!inSameGroup) {
                    if (chuckString.length() > 0) {
                        chuckString.append(" ");
                    }
                    chuckString.append("00 ");
                }

                chuckString.append("0");
            }
        }
        return chuckString.toString().trim();
    }

    public static boolean isValidInput(String input) {
        String[] array = input.split(" ");

        // Check if contains other things
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) != '0' && input.charAt(i) != ' ') {
                return false;
            }
        }

        // First block check
        if (!array[0].equals("0") && !array[0].equals("00")) {
            return false;
        }

        // Number of blocks is odd
        if (array.length % 2 != 0) {
            return false;
        }

        for (int i = 0; i < array.length; i++) {
            if (i % 2 == 0) {
                if (!array[i].equals("0") && !array[i].equals("00")) {
                    return false;
                }
            }
        }

        String binaryString = fromChuckToBinary(input);
        String[] binaryChars = fromBinaryToBinary7bits(binaryString);
        for (String binaryChar : binaryChars) {
            if (binaryChar.length() % 7 != 0) {
                return false;
            }
        }
        return true;
    }
}


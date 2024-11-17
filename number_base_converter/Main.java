package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            String inputLine = sc.nextLine();

            if ("/exit".equals(inputLine.trim())) {
                System.out.println();
                break;
            }

            try {
                String[] inputs = inputLine.split(" ");
                int sourceBase = Integer.parseInt(inputs[0]);
                int targetBase = Integer.parseInt(inputs[1]);

                while (true) {
                    System.out.printf("Enter number in base %d to convert to base %d (To go back type /back) ", sourceBase, targetBase);
                    String userInput = sc.nextLine().toLowerCase();

                    if ("/back".equals(userInput)) {
                        System.out.println();
                        break;
                    }

                    if (userInput.contains(".")) {
                        String[] fractionalInputs = userInput.split("\\.");
                        String integerPart = fractionalInputs[0];
                        String fractionalPart = fractionalInputs[1];

                        // integer part
                        BigInteger fromSourceToDecimal = getConvertedValueFromSourceToDecimal(integerPart, sourceBase);
                        String fromDecimalToTarget = getConvertedValueFromDecimalToTarget(fromSourceToDecimal, targetBase);

                        // fractional part
                        BigDecimal fractionalFromSourceToDecimal = getFractionalConvertedValueFromSourceToDecimal(fractionalPart, sourceBase);
                        String fractionalFromDecimalToTarget = getFractionalConvertedValueFromDecimalToTarget(fractionalFromSourceToDecimal, targetBase, 5);
                        System.out.println("Conversion result: " + fromDecimalToTarget + "." + fractionalFromDecimalToTarget + "\n");
                    } else {
                        BigInteger fromSourceToDecimal = getConvertedValueFromSourceToDecimal(userInput, sourceBase);
                        String fromDecimalToTarget = getConvertedValueFromDecimalToTarget(fromSourceToDecimal, targetBase);
                        System.out.println("Conversion result: " + fromDecimalToTarget + "\n");
                    }
                }

            } catch (Exception e) {
                System.out.println("Invalid input. Please enter two integers or type /exit to quit. Message: " + e);
            }
        }
        sc.close();
    }

    private static String getFractionalConvertedValueFromDecimalToTarget(BigDecimal fractionalInput, int targetBase, int precision) {
        if (targetBase < 2 || targetBase > 36) {
            throw new IllegalArgumentException("Base must be in the range 2 to 36");
        }

        StringBuilder sb = new StringBuilder();
        BigDecimal base = BigDecimal.valueOf(targetBase);

        // Get only the fractional part if the number is greater than 1
        fractionalInput = fractionalInput.subtract(new BigDecimal(fractionalInput.intValue()));

        // Always generate exactly 'precision' digits
        for (int i = 0; i < precision; i++) {
            fractionalInput = fractionalInput.multiply(base);
            int integerPart = fractionalInput.intValue();

            if (integerPart < 10) {
                sb.append((char) ('0' + integerPart));
            } else {
                sb.append((char) ('a' + (integerPart - 10)));
            }

            fractionalInput = fractionalInput.subtract(BigDecimal.valueOf(integerPart));
        }

        // Ensure exactly 5 digits
        String result = sb.toString();
        if (result.length() < precision) {
            result = result + "0".repeat(precision - result.length());
        } else if (result.length() > precision) {
            result = result.substring(0, precision);
        }

        return result;
    }

    private static BigDecimal getFractionalConvertedValueFromSourceToDecimal(String userInput, int sourceBase) {
        if (sourceBase < 2 || sourceBase > 36) {
            throw new IllegalArgumentException("Base must be in the range 2 to 36");
        }

        BigDecimal result = BigDecimal.ZERO;
        BigDecimal base = BigDecimal.valueOf(sourceBase);
        BigDecimal power = BigDecimal.ONE.divide(base, 32, RoundingMode.HALF_UP); // Increased precision significantly

        for (int i = 0; i < userInput.length(); i++) {
            int digit = charToDecimal(userInput.charAt(i));
            if (digit < 0 || digit >= sourceBase) {
                throw new IllegalArgumentException("Invalid digit in the input number for the given base.");
            }
            result = result.add(BigDecimal.valueOf(digit).multiply(power));
            power = power.divide(base, 32, RoundingMode.HALF_UP);
        }

        return result;
    }

    private static BigInteger getConvertedValueFromSourceToDecimal(String userInput, int sourceBase) {
        if (sourceBase < 2 || sourceBase > 36) {
            throw new IllegalArgumentException("Base must be in the range 2 to 36");
        }

        BigInteger result = BigInteger.ZERO;
        BigInteger power = BigInteger.ONE;
        BigInteger base = BigInteger.valueOf(sourceBase);

        for (int i = userInput.length() - 1; i >= 0; i--) {
            int digit = charToDecimal(userInput.charAt(i));
            if (digit < 0 || digit >= sourceBase) {
                throw new IllegalArgumentException("Invalid digit in the input number for the given base.");
            }
            result = result.add(BigInteger.valueOf(digit).multiply(power));
            power = power.multiply(base);
        }
        return result;
    }

    private static int charToDecimal(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 10;
        } else if (c >= 'a' && c <= 'z') {
            return c - 'a' + 10;
        } else {
            return -1;
        }
    }

    private static String getConvertedValueFromDecimalToTarget(BigInteger decimalInput, int targetBase) {
        if (targetBase < 2 || targetBase > 36) {
            throw new IllegalArgumentException("Base must be in the range 2 to 36");
        }

        StringBuilder sb = new StringBuilder();
        BigInteger base = BigInteger.valueOf(targetBase);
        BigInteger zero = BigInteger.ZERO;
        BigInteger divisionResult = decimalInput;

        while (divisionResult.compareTo(zero) > 0) {
            BigInteger[] divMod = divisionResult.divideAndRemainder(base);
            int remainder = divMod[1].intValue();

            if (remainder < 10) {
                sb.append((char) ('0' + remainder));
            } else {
                sb.append((char) ('A' + (remainder - 10)));
            }

            divisionResult = divMod[0];
        }

        return sb.reverse().toString();
    }
}

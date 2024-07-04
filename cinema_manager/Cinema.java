import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {
        // Get input to build cinema
        int[] input = getInputToBuildCinema();
        int rows = input[0];
        int seats = input[1];
        int currentIncome = 0;

        // Build cinema
        char[][] cinema = buildCinema(rows, seats);

        // Menu loop
        while (true) {
            int selection = menu();
            if (selection == 1) {
                printCinema(cinema);
            } else if (selection == 2) {
                currentIncome += buyATicket(cinema);
            } else if (selection == 3) {
                statistics(cinema, currentIncome);
            } else if (selection == 0) {
                break;
            } else {
                System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    public static int CalculateIncome(char[][] cinema) {
        int rows = cinema.length;
        int seats = cinema[0].length;
        int totalSeats = rows * seats;
        int profit;

        if (totalSeats <= 60) {
            profit = 10 * totalSeats;
        } else {
            profit = (((rows / 2) * 10) + ((rows - rows / 2) * 8)) * seats;
        }
        return profit;
    }

    public static void printCinema(char[][] cinema) {
        int[] colNumbers = new int[cinema[0].length];

        for (int i = 0; i < colNumbers.length; i++) {
            colNumbers[i] = i + 1;
        }

        System.out.println();
        System.out.println("Cinema:");
        System.out.print(" ");

        for (int number : colNumbers) {
            System.out.print(number + " ");
        }
        System.out.println();
        for (int i = 0; i < cinema.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < cinema[i].length; j++) {
                System.out.print(cinema[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int getPlacePrice(int row, char[][] cinema) {
        int totalSeats = cinema.length * cinema[0].length;
        int frontRows = cinema.length / 2;
        int price = totalSeats <= 60 || row <= frontRows ? 10 : 8;
        return price;
    }

    public static char[][] buildCinema(int rows, int seats) {
        char[][] cinema = new char[rows][seats];
        for (char[] row : cinema) {
            Arrays.fill(row, 'S');
        }
        return cinema;
    }

    public static int[] getInputToBuildCinema() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        return new int[]{rows, seats};
    }

    public static int[] getInputToGetPlace(char[][] cinema) {
        Scanner scanner = new Scanner(System.in);
        String row;
        String seat;
        while (true) {
            System.out.println("Enter a row number:");
            row = scanner.nextLine();
            System.out.println("Enter a seat number in that row:");
            seat = scanner.nextLine();
            if (row.matches("\\d+")
                    && seat.matches("\\d+")
                    && Integer.parseInt(row) > 0
                    && Integer.parseInt(seat) > 0
                    && Integer.parseInt(row) <= cinema.length
                    && Integer.parseInt(seat) <= cinema[0].length) {
                break;
            }
            System.out.println("Wrong input!");
        }
        return new int[]{Integer.parseInt(row), Integer.parseInt(seat)};
    }

    public static int menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit""");
        return scanner.nextInt();
    }

    public static int buyATicket(char[][] cinema) {
        int placePrice;
        while (true) {
            int[] placeCoordinates = getInputToGetPlace(cinema);
            int row = placeCoordinates[0];
            int seat = placeCoordinates[1];
            char thePlace = cinema[row - 1][seat - 1];
            if (thePlace == 'S') {
                placePrice = getPlacePrice(row, cinema);
                System.out.println("\nTicket price: $" + placePrice);
                cinema[row - 1][seat - 1] = 'B';
                break;
            } else {
                System.out.println("That ticket has already been purchased!");
            }
        }
        return placePrice;
    }

    public static void statistics(char[][] cinema, int currentIncome) {
        int totalSeats = cinema.length * cinema[0].length;
        int nbPurchasedTickets = 0;

        // Calculate purchased tickets
        for (char[] row : cinema) {
            for (char seat : row) {
                if (seat == 'B') {
                    nbPurchasedTickets += 1;
                }
            }
        }

        // Get totalIncome
        int totalIncome = CalculateIncome(cinema);

        // Calculate percentage
        float percentage = ((float) nbPurchasedTickets / totalSeats) * 100;

        // Print results
        String message = """
                Number of purchased tickets: %d
                Percentage: %.2f%%
                Current income: $%d
                Total income: $%d
                """;
        System.out.println(String.format(message, nbPurchasedTickets, percentage, currentIncome, totalIncome));
    }
}

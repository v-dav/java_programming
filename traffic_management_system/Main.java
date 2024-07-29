package traffic;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Welcome to the traffic management system!");
        int roads = getInput("Input the number of roads:");
        int intervals = getInput("Input the interval:");

        // Implement system state
        SystemState queueThread = new SystemState(roads, intervals);
        queueThread.setName("QueueThread");
        queueThread.start();
        menuMenu(queueThread);
    }

    public static void clearConsole() {
        try {
            var clearCommand = System.getProperty("os.name").contains("Windows")
                    ? new ProcessBuilder("cmd", "/c", "cls")
                    : new ProcessBuilder("clear");
            clearCommand.inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.getMessage();
        }
    }

    public static int getInput(String prompt) {
        // Validate integer and positive
        Scanner scanner = new Scanner(System.in);
        int value;
        System.out.println(prompt);
        while (true) {
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value <= 0) {
                    throw new Exception();
                }
                clearConsole();
                break;
            } catch (Exception e) {
                System.out.println("Error! Incorrect input. Try again:");
            }
        }
        return value;
    }

    public static void menuMenu(SystemState queueThread) throws InterruptedException, IOException {
        Scanner scanner = new Scanner(System.in);
        int choice;
        int roads, intervals;
        menuLoop:
        while (true) {
            System.out.println("""
                    Menu:
                    1. Add road
                    2. Delete road
                    3. System
                    0. Quit""");

            while (true) {
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice < 0 || choice > 3) {
                        throw new Exception();
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Incorrect option");
                    scanner.nextLine();
                    clearConsole();
                    continue menuLoop;
                }
            }

            clearConsole();
            boolean systemState = false;

            switch (choice) {
                case 1:
                    System.out.print("Input road name: ");
                    String roadName = scanner.nextLine();
                    if (roadName == null || roadName.isEmpty()) {
                        System.out.println("Error! Please enter a valid road name.");
                    } else {
                        queueThread.roadsQueue.enqueue(new Road(roadName));
                    }
                    break;
                case 2:
                    queueThread.roadsQueue.dequeue();
                    break;
                case 3:
                    systemState = true;
                    queueThread.setSystemState(true);
                    scanner.nextLine();
                    queueThread.setSystemState(false);
                    clearConsole();
                    break;
                default:
                    System.out.println("Bye!");
                    queueThread.stopRunning();
                    break menuLoop;
            }
            if (!systemState) {
                scanner.nextLine();
            }
        }
    }
}

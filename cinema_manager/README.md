# Cinema Booking System

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

## Project Overview

The Cinema Booking System is a Java-based application that simulates a simple cinema ticket booking system. Users can view the seating arrangement, buy tickets, and view statistics such as the number of purchased tickets, the percentage of occupancy, current income, and potential total income.

## Features

- **View Seating Arrangement**: Display the current seating arrangement of the cinema.
- **Buy a Ticket**: Allows users to purchase a ticket for a specific seat, updating the seating arrangement.
- **View Statistics**: Provides statistics on the number of purchased tickets, percentage of occupancy, current income, and total possible income.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed on your machine. You can download it from [Oracle's official website](https://www.oracle.com/java/technologies/javase-downloads.html).

### Running the Application

1. Clone the repository or download the code files.
2. Navigate to the project directory.
3. Compile the Java code using the following command:

    ```sh
    javac Cinema.java
    ```

4. Run the application using the command:

    ```sh
    java Cinema
    ```

## Usage

### Menu Options

1. **Show the seats**: Displays the current seating arrangement of the cinema.
2. **Buy a ticket**: Prompts the user to select a seat to purchase a ticket.
3. **Statistics**: Displays the statistics including number of purchased tickets, percentage of occupancy, current income, and total income.
0. **Exit**: Exits the application.

### Example Output

```plaintext
Enter the number of rows:
5
Enter the number of seats in each row:
5

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
2

Enter a row number:
2
Enter a seat number in that row:
3

Ticket price: $10

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
1

Cinema:
  1 2 3 4 5 
1 S S S S S 
2 S S B S S 
3 S S S S S 
4 S S S S S 
5 S S S S S 

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
3

Number of purchased tickets: 1
Percentage: 4.00%
Current income: $10
Total income: $200

1. Show the seats
2. Buy a ticket
3. Statistics
0. Exit
0
```

## Program Structure

### Main Class: Cinema

- **main**: The main method initializes the cinema, handles the menu selection, and executes the corresponding functions.
- **CalculateIncome**: Calculates the total potential income based on the seating arrangement.
- **printCinema**: Displays the current seating arrangement of the cinema.
- **getPlacePrice**: Determines the price of a ticket based on the row number.
- **buildCinema**: Initializes the seating arrangement with all seats available.
- **getInputToBuildCinema**: Prompts the user for the number of rows and seats.
- **getInputToGetPlace**: Prompts the user for a specific seat to purchase.
- **menu**: Displays the menu options and returns the user's selection.
- **buyATicket**: Handles the process of purchasing a ticket and updates the seating arrangement.
- **statistics**: Displays statistics about the cinema.

## Author

- [Vladimir Davidov](https://github.com/v-dav)

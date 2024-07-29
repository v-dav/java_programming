# Traffic Management System

![Java Badge](https://img.shields.io/badge/Java-Programming-green)

## Project Description

This project is a Traffic Management System implemented in Java. The system uses a circular queue data structure and threading to manage multiple roads, allowing users to add and remove roads and visualize the state of the system. The primary goal is to simulate the management of traffic on multiple roads by using an interval-based system.

## Features

1. **Circular Queue Data Structure**: Used to manage the roads in a cyclic manner, ensuring efficient use of space and time.
2. **Multithreading**: Utilized to update the system state periodically and manage user interactions concurrently.
3. **Interactive Console Menu**: Allows users to add and remove roads, view the system state, and quit the application.
4. **Real-time State Updates**: The system updates the state of the roads (open/closed) and their respective counters in real-time.

## Concepts Implemented

### Circular Queue Data Structure

A circular queue, also known as a ring buffer, is a data structure that uses a single, fixed-size buffer as if it were connected end-to-end. This project uses a circular queue to manage the roads, allowing efficient addition and removal of roads while maintaining their order.

- **Enqueue**: Adding a new road to the queue. If the queue is full, it prevents further additions.
- **Dequeue**: Removing a road from the queue. If the queue is empty, it prevents further removals.
- **Update Counters and State**: Periodically updating the state and counters of the roads in the queue.

### Multithreading

The project uses Java's threading capabilities to manage real-time updates to the system state and handle user interactions simultaneously.

- **SystemState Thread**: This thread continuously updates the state of the roads and prints the system status if required.
- **Main Thread**: Handles user inputs and interactions through the console menu.

## Code Structure

- **Main.java**: The entry point of the application. Manages user input and initiates the system state.
- **SystemState.java**: Extends `Thread` and handles the periodic updates to the road states.
- **CircularQueue.java**: Implements the circular queue data structure to manage the roads.
- **Road.java**: Represents a road with attributes like name, state, and counter.

## How to Clone and Run the Project

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/traffic-management-system.git
   cd traffic-management-system
   ```

2. **Compile the Code**:
   ```bash
   javac traffic/Main.java
   ```

3. **Run the Application**:
   ```bash
   java traffic.Main
   ```

## Detailed Explanation of Key Classes

### Main.java

The `Main` class is the entry point of the application. It initializes the system state and provides an interactive console menu for user interactions. The menu allows users to add and remove roads and view the current system state.

### SystemState.java

The `SystemState` class extends `Thread` and is responsible for updating the system state periodically. It keeps track of the time elapsed since the system startup and updates the state of the roads in the circular queue.

### CircularQueue.java

The `CircularQueue` class implements the circular queue data structure. It provides methods to add (`enqueue`) and remove (`dequeue`) roads, check if the queue is empty or full, print the current state of the roads, and update the state and counters of the roads periodically.

### Road.java

The `Road` class represents a road with attributes like name, state (open/closed), and counter. It provides getter and setter methods to access and modify these attributes.

## Example Usage

Upon running the application, you will be presented with a menu to interact with the system. You can add roads, remove roads, and view the current system state. The system state updates in real-time, displaying the status of each road and the time elapsed since the system startup.

![stage6JV](https://github.com/user-attachments/assets/67b86f09-eb39-4047-af93-3757f0ab098f)


This will display the current state of the system, including the number of roads and their respective states (open/closed).

## Conclusion

This Traffic Management System project demonstrates the use of circular queue data structures and multithreading in Java to manage and simulate real-time traffic on multiple roads. The interactive console menu provides a user-friendly interface for managing the system.

## Author
Vladimir Davidov


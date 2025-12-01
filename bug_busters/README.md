
# Memorizing Tool

A command-line Java application for managing and manipulating lists of different data types with comprehensive operations and file persistence.

## Overview

Memorizing Tool is an educational project that provides an interactive memory management system for three data types: booleans, integers, and strings. The application offers a rich set of list operations, type-specific functionality, and file I/O capabilities through an intuitive command-line interface.

## Features

### Core Functionality

**Common Operations** (available for all data types):
- Add, remove, replace elements
- Sort in ascending/descending order
- Search by value and index
- Count occurrences and calculate frequency
- Get random elements
- Mirror (reverse) list order
- Extract unique elements
- Import/export data from files
- Clear all elements

### Boolean-Specific Operations

- **Flip**: Invert boolean values at specific indices
- **Negate All**: Invert all boolean values
- **Logical Operations**: Perform AND/OR operations on elements
- **Logical Shift**: Rotate elements in the list
- **Conversion**: Convert boolean sequences to numbers or strings
- **Morse Code**: Convert boolean sequences to Morse code representation

### Number-Specific Operations

- **Arithmetic**: Sum, subtract, multiply, divide elements
- **Power**: Calculate exponentiation
- **Factorial**: Calculate factorial of stored numbers
- **Aggregate**: Sum all elements, calculate average

### Word-Specific Operations

- **Concatenation**: Join two strings
- **Case Manipulation**: Convert to upper/lower case, swap case
- **Reverse**: Reverse string characters
- **Length**: Get string length
- **Join**: Join all strings with a delimiter
- **Regex**: Filter strings by regular expression pattern

## Installation

### Prerequisites

- Java Development Kit (JDK) 11 or higher
- Java compiler (javac)

### Building the Project

```bash
# Navigate to the project directory
cd memorizingtool

# Compile all Java files
javac *.java

# Run the application
java Memory
```

## Usage

### Starting the Application

When you launch the application, you'll see a menu:

```
Welcome to Data Memory!
Possible actions:
1. Memorize booleans
2. Memorize numbers
3. Memorize words
0. Quit
```

Select the data type you want to work with by entering the corresponding number.

### Command Syntax

Once inside a memorization mode, use commands with the following format:

```
/command [parameter1] [parameter2] ...
```

### Common Commands

```
/help                           - Display help message with all commands
/menu                           - Return to main menu
/add [ELEMENT]                  - Add element to the list
/remove [INDEX]                 - Remove element at index
/replace [INDEX] [ELEMENT]      - Replace element at index
/print [INDEX]                  - Print element at index
/printAll [asList/lineByLine/oneLine] - Print all elements
/sort [ascending/descending]    - Sort the list
/getRandom                      - Get a random element
/size                           - Display number of elements
/clear                          - Remove all elements
/readFile [FILENAME]            - Import data from file
/writeFile [FILENAME]           - Export data to file
```

### Example Session

```
Perform action:
/add true
Element true added

Perform action:
/add false
Element false added

Perform action:
/printAll oneLine
[true, false]

Perform action:
/flip 0
Element on 0 position flipped

Perform action:
/printAll oneLine
[false, false]
```

## File Format

Data files should contain one element per line:

**Boolean files:**
```
true
false
true
```

**Number files:**
```
42
-17
100
```

**String files:**
```
Hello
World
Java
```

## Project Structure

```
memorizingtool/
├── Memory.java           - Main entry point and menu system
├── BooleanMemorize.java  - Boolean list management
├── NumberMemorize.java   - Integer list management
├── WordMemorize.java     - String list management
├── Commons.java          - Shared utilities and help text
└── FileProcessor.java    - File I/O operations
```

## Educational Context

This project was developed as part of the [JetBrains Academy (Hyperskill)](https://hyperskill.org/) learning platform. It demonstrates fundamental Java programming concepts including:

- Object-oriented programming
- Collections framework (ArrayList, HashMap)
- File I/O with proper encoding (UTF-8)
- Exception handling
- Reflection API
- Command pattern implementation
- Generic programming concepts

## License

This is an educational project developed for learning purposes.

## Acknowledgments

- **JetBrains Academy (Hyperskill)** for providing the educational framework and project specifications
- Developed as part of Java programming curriculum

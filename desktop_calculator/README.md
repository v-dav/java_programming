# 🧮 Java Desktop Calculator
A calculator application built with Java Swing.


<div align="center">
  <img width="334" alt="Skärmavbild 2025-06-09 kl  18 05 12" src="https://github.com/user-attachments/assets/18d5e1ae-5c15-442d-b87b-2c85b445d51b" />
</div>

<div align="center">
  
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-GUI-blue?style=for-the-badge)
![MIT License](https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge)

</div>

## ✨ Features

### 🎨 **Modern User Interface**
- **Dark Theme**: Elegant dark color scheme with professional styling
- **Round Buttons**: Custom circular buttons with smooth hover effects
- **Responsive Design**: Intuitive layout optimized for desktop use
- **Visual Feedback**: Color-coded buttons and real-time expression validation

### 🔢 **Core Operations**
- ➕ **Addition** (`+`)
- ➖ **Subtraction** (`−`)
- ✖️ **Multiplication** (`×`)
- ➗ **Division** (`÷`)
- 🔢 **Decimal Numbers** (`.`)

### 🚀 **Advanced Functions**
- 🔋 **Power Operations**: x², x^y
- √ **Square Root** with validation
- 📐 **Parentheses** for complex expressions
- ± **Plus/Minus Toggle** for number negation
- 🧹 **Clear** and **Delete** functions

### 🛡️ **Error Handling**
- Division by zero protection
- Invalid expression detection
- Balanced parentheses validation
- Square root of negative numbers prevention
- Visual error indication with color feedback

### 🎯 **Smart Input Processing**
- Automatic number formatting
- Operator precedence handling
- Unary minus support
- Expression validation in real-time

## 🖼️ User Interface

The calculator features a clean, modern interface with:

- **Display Panel**: Shows both the current equation and result
- **Number Pad**: Circular buttons (0-9) with tactile feedback
- **Operator Section**: Clearly distinguished orange buttons for operations
- **Function Buttons**: Gray buttons for special operations and utilities
- **Easter Egg**: ☕ button linking to the developer's profile

### Color Scheme
- **Background**: Deep charcoal (`#161616`)
- **Display**: Dark gray (`#202020`)
- **Numbers**: Medium gray (`#323232`)
- **Operators**: Orange (`#FF9500`)
- **Functions**: Light gray (`#A5A5A5`)

## 🚀 Getting Started

### Prerequisites
- **Java 8** or higher
- **Java Development Kit (JDK)**
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

### Installation

1. **Clone or Download** the project files
2. **Compile** the Java files:
   ```bash
   javac *.java
   ```
3. **Run** the application:
   ```bash
   java ApplicationRunner
   ```

### Alternative IDE Setup
1. Create a new Java project in your IDE
2. Add the three Java files to your project
3. Run `ApplicationRunner.java` as the main class

## 📱 Usage Guide

### Basic Operations
1. **Numbers**: Click digit buttons (0-9) to input numbers
2. **Decimals**: Use the `.` button for decimal points
3. **Operations**: Click `+`, `−`, `×`, or `÷` for basic math
4. **Calculate**: Press `=` to evaluate the expression
5. **Clear**: Use `C` to clear everything or `⌫` to delete the last character

### Advanced Features

#### Power Functions
- **x²**: Squares the current number
- **x^y**: Raises x to the power of y (enter base, press x^y, enter exponent)

#### Square Root
- **√**: Click before entering a number or expression in parentheses
- Example: `√(25)` = 5

#### Parentheses
- **( )**: Smart parentheses insertion
    - Opens `(` when starting a group
    - Closes `)` when completing a group
    - Automatically balances expressions

#### Plus/Minus Toggle
- **±**: Toggles the sign of numbers
    - At start: Creates negative number
    - On existing number: Toggles positive/negative

### Expression Examples
```
Simple: 5 + 3 = 8
Complex: (5 + 3) × 2 = 16
Power: 2^3 = 8
Square: 5² = 25
Root: √(16) = 4
Negative: ±5 + 3 = -2
Mixed: √(25) + 2² = 9
```

## 🏗️ Technical Architecture

### File Structure
```
📦 Calculator Application
├── 📄 ApplicationRunner.java    # Entry point and UI setup
├── 📄 Calculator.java          # Main GUI and user interaction
└── 📄 InfixToPostfix.java     # Mathematical expression parser
```

### Core Components

#### **ApplicationRunner.java**
- Application entry point
- Sets system look and feel
- Initializes the calculator on Event Dispatch Thread

#### **Calculator.java**
- **RoundButton Class**: Custom circular buttons with hover effects
- **UI Layout**: Precise positioning and styling
- **Event Handling**: Button clicks and user interactions
- **Expression Building**: Smart input processing and validation

#### **InfixToPostfix.java**
- **Expression Parsing**: Converts user input to mathematical tokens
- **Validation Engine**: Comprehensive syntax and logic checking
- **Postfix Conversion**: Uses Shunting Yard algorithm
- **Evaluation Engine**: Stack-based expression calculation

### Algorithms Used

1. **Shunting Yard Algorithm**: Converts infix notation to postfix
2. **Stack-Based Evaluation**: Processes postfix expressions
3. **Recursive Descent Parsing**: Validates expression syntax
4. **Precedence Handling**: Manages operator order of operations

### Design Patterns
- **Observer Pattern**: Event-driven button interactions
- **Strategy Pattern**: Different operation handlers
- **Builder Pattern**: Expression construction
- **Template Method**: Consistent button creation

## 🔧 Customization

### Modifying Colors
Edit the color constants in `Calculator.java`:
```java
private static final Color BACKGROUND_COLOR = new Color(22, 22, 22);
private static final Color OPERATOR_BUTTON_COLOR = new Color(255, 149, 0);
// ... other color definitions
```

### Adding New Operations
1. Add button in `addButtons()` method
2. Implement operation logic in `InfixToPostfix.java`
3. Update precedence rules if needed

### Changing Button Layout
Modify the `setBounds()` calls in the `addButtons()` method to reposition elements.

## 🐛 Error Handling

The calculator includes comprehensive error handling:

- **Syntax Errors**: Invalid expressions turn the display red
- **Mathematical Errors**: Division by zero, negative square roots
- **Input Validation**: Prevents invalid character sequences
- **Parentheses Matching**: Ensures balanced expressions

## 🤝 Contributing

Feel free to contribute to this project! Areas for enhancement:
- Additional mathematical functions (sin, cos, tan, log)
- Scientific notation support
- History/memory functions
- Keyboard input support
- Unit tests

## 👨‍💻 Author

[Vladimir Davidov](https://www.linkedin.com/in/v-dav/)

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

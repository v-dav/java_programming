# Connect Four Game 🔴🟡

A classic Connect Four game implemented in Java Swing.

<img width="421" height="435" alt="Skärmavbild 2025-07-12 kl  23 44 16" src="https://github.com/user-attachments/assets/029afd99-6321-46dc-82a0-01b6ba523c7e" />


## 🎮 Game Description

Connect Four is a two-player strategy game where players take turns dropping colored pieces into a 7-column, 6-row grid. 
The objective is to be the first to form a horizontal, vertical, or diagonal line of four pieces.

## ✨ Features

- **Turn-based Gameplay**: Players alternate between X and O pieces
- **Gravity Physics**: Pieces automatically drop to the lowest available position in each column
- **Win Detection**: Automatic detection of 4-in-a-row connections in all directions:
  - Horizontal
  - Vertical
  - Diagonal (both directions)
- **Visual Win Highlighting**: Winning pieces are highlighted in cyan
- **Reset Functionality**: Clear the board and start a new game anytime
- **Input Validation**: 
  - Prevents moves in full columns
  - Blocks input after game completion

## 🚀 How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VSCode) or command line

### Running the Game

1. **Clone or download the source code**
2. **Compile the Java files:**
   ```bash
   javac connect_four/*.java
   ```
3. **Run the application:**
   ```bash
   java connect_four.ApplicationRunner
   ```

### Alternative: Using an IDE
1. Import the project into your preferred Java IDE
2. Run the `ApplicationRunner.java` file

## 🎯 How to Play

1. **Starting**: Player X always goes first
2. **Making a Move**: Click on any cell in a column to drop your piece
3. **Objective**: Connect four of your pieces in a row (horizontally, vertically, or diagonally)
4. **Winning**: When a player achieves 4-in-a-row, the winning pieces are highlighted
5. **New Game**: Click the yellow "Reset" button to clear the board and start over

## 🏗️ Project Structure

```
four/
├── ApplicationRunner.java    # Main entry point
├── ConnectFour.java         # Core game logic and UI setup
├── Cell.java               # Custom button for game cells
└── ButtonReset.java        # Custom reset button
```

## 🔧 Technical Details

### Architecture
- **MVC Pattern**: Clean separation between game logic and UI components
- **Event-Driven**: Uses ActionListeners for user interactions
- **Object-Oriented**: Modular design with separate classes for different components

### Key Components
- **ConnectFour**: Main game class extending JFrame
- **Cell**: Custom JButton representing each game cell
- **ButtonReset**: Custom JButton for game reset functionality

### Game Logic
- **Board Representation**: 2D array `Cell[7][6]` (columns × rows)
- **Win Algorithm**: Efficient directional checking using delta coordinates
- **State Management**: Boolean flags for turn tracking and game completion

## 🎨 Customization

### Colors
- **Default Cells**: Light green (`RGB(144, 188, 144)`)
- **Winning Cells**: Cyan highlight
- **Reset Button**: Yellow background

### Modifying Game Rules
- **Board Size**: Adjust grid dimensions in `GridLayout(6, 7, 0, 0)`
- **Win Condition**: Modify the win checking logic in `checkDirection()`
- **Colors**: Update color values in respective constructors

## 🐛 Known Issues

- Button styling may vary slightly across different operating systems due to Look and Feel differences

## 📋 Requirements

- **Java Version**: JDK 8+
- **Libraries**: Java Swing (included in standard JDK)
- **OS**: Cross-platform (Windows, macOS, Linux)

## 🤝 Contributing

Feel free to fork this project and submit pull requests for improvements:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

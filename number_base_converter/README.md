# 🔢 Universal Number Base Converter

A powerful and flexible Java application that converts numbers between different bases, supporting both integer and fractional numbers.
This converter handles bases from 2 to 36, making it perfect for working with binary, octal, decimal, hexadecimal, and beyond!

## ✨ Features

- Convert between any base (2-36)
- Support for fractional numbers
- High precision calculations using `BigInteger` and `BigDecimal`
- Interactive command-line interface
- Error handling and input validation
- 5-digit precision for fractional conversions
- User-friendly navigation commands

## 🚀 Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Any Java IDE (optional)

### Running the Program

1. Compile the Java file:
```bash
javac converter/Main.java
```

2. Run the program:
```bash
java converter.Main
```

## 💡 How to Use

1. **Enter Source and Target Bases**
   - When prompted, enter two numbers in the format: `{source base} {target base}`
   - Example: `16 2` (to convert from hexadecimal to binary)

2. **Enter Numbers to Convert**
   - Input your number in the source base
   - For fractional numbers, use a decimal point (e.g., `FF.A` in base 16)
   - The program will display the converted result

3. **Navigation Commands**
   - Type `/back` to return to base selection
   - Type `/exit` to quit the program

## 📝 Examples

```
Enter two numbers in format: {source base} {target base} (To quit type /exit) 16 2
Enter number in base 16 to convert to base 2 (To go back type /back) FF
Conversion result: 11111111

Enter number in base 16 to convert to base 2 (To go back type /back) FF.A
Conversion result: 11111111.10100

Enter number in base 16 to convert to base 2 (To go back type /back) /back

Enter two numbers in format: {source base} {target base} (To quit type /exit) /exit
```

## 🔍 Technical Details

### Key Components

- **BigInteger/BigDecimal**: Handles arbitrary-precision arithmetic
- **Custom Base Conversion**: Supports bases 2 through 36 (0-9, A-Z)
- **Fractional Handling**: Maintains 5-digit precision for fractional parts

### Supported Bases

- Binary (Base 2)
- Octal (Base 8)
- Decimal (Base 10)
- Hexadecimal (Base 16)
- And any other base up to 36!

### Input Format

- Digits 0-9 for values 0-9
- Letters a-z or A-Z for values 10-35
- Decimal point (.) for fractional numbers

## ⚠️ Error Handling

The program includes robust error handling for:
- Invalid base inputs
- Invalid digit inputs for the given base
- Malformed number formats
- Out-of-range bases

## 🤝 Contributing

Feel free to fork this project and submit pull requests with improvements! Some areas for potential enhancement:

- Adding GUI interface
- Supporting negative numbers
- Extending base support beyond 36
- Adding unit tests
- Implementing different rounding modes for fractional conversions

## 📄 License

This project is open source and available under the MIT License.

---

Built with ❤️ for number system enthusiasts and computer science students

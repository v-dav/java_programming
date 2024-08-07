# Chuck Norris Encoding and Decoding

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

<p align="center">
   <img src="https://github.com/user-attachments/assets/cf6694f0-8bc9-4f3c-9b3d-9f0db487e07f" alt="image">
</p>


## Project Description

This project is a Java implementation of the Chuck Norris encoding and decoding technique. Chuck Norris encoding is a unary encoding where sequences of binary digits are converted into a series of `0`s and spaces. The project provides functionality to encode a string into Chuck Norris encoded format and decode a Chuck Norris encoded string back to its original form.

## Features

- **Encode:** Convert a string into Chuck Norris encoded format.
- **Decode:** Convert a Chuck Norris encoded string back to its original form.
- **User Interaction:** The program interacts with the user through the console, allowing them to choose between encoding, decoding, and exiting the application.

## How It Works

### Encoding

The encoding process involves converting each character of the input string into a 7-bit binary representation and then transforming this binary string into Chuck Norris encoding. The steps are as follows:

1. Convert each character of the string to its 7-bit binary form.
2. Transform the binary string to Chuck Norris encoding.

### Decoding

The decoding process involves converting a Chuck Norris encoded string back into its original form by reversing the encoding steps. The steps are as follows:

1. Transform the Chuck Norris encoded string into a binary string.
2. Convert the binary string back into characters.

## Usage Instructions

1. **Compile the Program:**
   ```bash
   javac Main.java
   ```

2. **Run the Program:**
   ```bash
   java Main
   ```

3. **Follow the On-Screen Prompts:**
   - **Encode:** Input a string to encode it into Chuck Norris encoding.
   - **Decode:** Input a Chuck Norris encoded string to decode it back to the original string.
   - **Exit:** Exit the program.

## Example
```
V chuck_norris_cypher_encoder %java Main
Please input operation (encode/decode/exit):
encode
Input string:
Chuck Norris does not sleep. He waits.
Encoded string:
0 0 00 0000 0 0000 00 0 0 0 00 000 0 000 00 0 0 0 00 0 0 000 00 000 0 0000 00 0 0 0 00 0 0 00 00 0 0 0 00 00000 0 0 00 00 0 000 00 0 0 00 00 0 0 0000000 00 00 0 0 00 0 0 000 00 00 0 0 00 0 0 00 00 0 0 0 00 00 0 0000 00 00 0 00 00 0 0 0 00 00000 0 00 00 00 0 0 00 00 0 00 00 0 0 000000 00 00 0 0 00 0 0 0000 00 00 0 00 00 0 0 0 00 00000 0 00 00 0 0 000 00 0 0 00 00 0 0 0000000 00 0 0 0 00 000 0 0 00 00000 0 000 00 00 0 0000 00 0 0 00 00 00 0 00 00 00 0 0 00 0 0 000 00 00 0 0 00 0 0 0000 00 00000 0 0 00 0 0 000 00 00 0 0 00 00000 0 0 00 00 0 0 00 000 0 00 00 00 0 0 00 0 0 0 00 0 0 0 00 00000 0 000 00 0 0 00000 00 0000 0 000 00 0 0 0 00 00 0 0000 00 0 0 0 00 00 0 000 00 00 0 00 00 0 0 0 00 0 0 000 00 0
Please input operation (encode/decode/exit):
decode
Input encoded string:
0 0 00 0000 0 0000 00 0 0 0 00 000 0 000 00 0 0 0 00 0 0 000 00 000 0 0000 00 0 0 0 00 0 0 00 00 0 0 0 00 00000 0 0 00 00 0 000 00 0 0 00 00 0 0 0000000 00 00 0 0 00 0 0 000 00 00 0 0 00 0 0 00 00 0 0 0 00 00 0 0000 00 00 0 00 00 0 0 0 00 00000 0 00 00 00 0 0 00 00 0 00 00 0 0 000000 00 00 0 0 00 0 0 0000 00 00 0 00 00 0 0 0 00 00000 0 00 00 0 0 000 00 0 0 00 00 0 0 0000000 00 0 0 0 00 000 0 0 00 00000 0 000 00 00 0 0000 00 0 0 00 00 00 0 00 00 00 0 0 00 0 0 000 00 00 0 0 00 0 0 0000 00 00000 0 0 00 0 0 000 00 00 0 0 00 00000 0 0 00 00 0 0 00 000 0 00 00 00 0 0 00 0 0 0 00 0 0 0 00 00000 0 000 00 0 0 00000 00 0000 0 000 00 0 0 0 00 00 0 0000 00 0 0 0 00 00 0 000 00 00 0 00 00 0 0 0 00 0 0 000 00 0
Decoded string:
Chuck Norris does not sleep. He waits.
Please input operation (encode/decode/exit):
exit
Bye!
V chuck_norris_cypher_encoder %
```

## Code Overview

- **Main Class:** The main class contains the primary logic to handle user input and call encoding/decoding functions.
- **Encoding Function:** Converts a string into its 7-bit binary representation and then to Chuck Norris encoding.
- **Decoding Function:** Converts a Chuck Norris encoded string back to its binary representation and then to the original string.
- **Validation Function:** Validates the input Chuck Norris encoded string.

## Author

- Vladimir Davidov

# Zoo Habitat Viewer

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

## Project Description

The Zoo Habitat Viewer is a simple Java application that allows users to view different animal habitats by entering the corresponding habitat number. Each habitat displays a unique ASCII art representation of an animal and a brief description of what the animal is doing. The application continues to prompt for input until the user types "exit".

## Features

- View different animal habitats by entering a number.
- ASCII art representation of animals.
- Descriptions of the animals' activities.
- Continuous prompt for habitat numbers until "exit" is typed.

## How It Works

### Animal Habitats

The application includes six habitats:
1. Camel
2. Lion
3. Deer
4. Goose
5. Bat
6. Rabbit

Each habitat is represented by a string containing ASCII art and a description.

### User Interaction

- The user is prompted to enter the number of the habitat they would like to view.
- Entering a valid habitat number (0-5) will display the corresponding habitat.
- Entering "exit" will terminate the program with a farewell message.

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
   - Enter a number (0-5) to view the corresponding habitat.
   - Enter "exit" to terminate the program.

### Example

**Sample Interaction:**
```
Please enter the number of the habitat you would like to view:0

Switching on the camera in the camel habitat...
 ___.-''''-.
 /___  @    |
 ',,,,.     |         _.'''''''._
      '     |        /           \
      |     \    _.-'             \
      |      '.-'                  '-.
      |                               ',
      |                                '',
       ',,-,                           ':;
            ',,| ;,,                 ,' ;;
               ! ; !'',,,',',,,,'!  ;   ;:
              : ;  ! !       ! ! ;  ;   :;
              ; ;   ! !      ! !  ; ;   ;,
             ; ;    ! !     ! !   ; ;
             ; ;    ! !    ! !     ; ;
            ;,,      !,!   !,!     ;,;
            /_I      L_I   L_I     /_I
Look at that! Our little camel is sunbathing!

Please enter the number of the habitat you would like to view:exit
See you later!
```

## Author

- Vladimir Davidov

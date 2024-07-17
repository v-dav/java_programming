# Coffee Machine

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

## Project Description

The Coffee Machine project simulates the functioning of a basic coffee machine. The program allows users to perform various actions such as buying coffee, filling the machine with supplies, taking money from the machine, and viewing the remaining supplies. It also handles different types of coffee, including Espresso, Latte, and Cappuccino, each with its own specific requirements for water, milk, and coffee beans.

## Features

- **Buy Coffee:** Choose from Espresso, Latte, or Cappuccino.
- **Fill Supplies:** Add water, milk, coffee beans, and disposable cups to the machine.
- **Take Money:** Retrieve the money accumulated in the machine.
- **Remaining Supplies:** Display the current state of the machine's supplies.
- **Exit:** Terminate the program.

## How It Works

### Coffee Types

The program supports three types of coffee:
- **Espresso:** Requires 250 ml of water, 0 ml of milk, 16 grams of coffee beans, and costs $4.
- **Latte:** Requires 350 ml of water, 75 ml of milk, 20 grams of coffee beans, and costs $7.
- **Cappuccino:** Requires 200 ml of water, 100 ml of milk, 12 grams of coffee beans, and costs $6.

### Supplies

The machine maintains a stock of the following supplies:
- Water (ml)
- Milk (ml)
- Coffee Beans (grams)
- Disposable Cups (count)
- Money ($)

### Actions

Users can perform the following actions:
- **Buy:** Select the type of coffee to purchase. The machine checks if it has enough supplies to make the selected coffee and updates the supplies accordingly.
- **Fill:** Add a specified amount of water, milk, coffee beans, and disposable cups to the machine.
- **Take:** Retrieve all the money accumulated in the machine.
- **Remaining:** Display the current state of the machine's supplies.
- **Exit:** End the program.

## Usage Instructions

1. **Compile the Program:**
   ```bash
   javac CoffeeMachine.java
   ```

2. **Run the Program:**
   ```bash
   java machine.CoffeeMachine
   ```

3. **Follow the On-Screen Prompts:**
   - **buy:** Select the type of coffee (1 for Espresso, 2 for Latte, 3 for Cappuccino, back to return to the main menu).
   - **fill:** Add the required amounts of water, milk, coffee beans, and disposable cups.
   - **take:** Retrieve the money from the machine.
   - **remaining:** View the current state of the machine's supplies.
   - **exit:** Terminate the program.

## Example

**Sample Interaction:**
```
Write action (buy, fill, take, remaining, exit):
> buy
What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:
> 1
I have enough resources, making you a coffee!

Write action (buy, fill, take, remaining, exit):
> remaining

The coffee machine has:
150 ml of water
540 ml of milk
104 g of coffee beans
8 disposable cups
$554 of money
```

## Author

- Vladimir Davidov

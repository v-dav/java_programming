package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public CoffeeMachine() {
        Supplies supplies = new Supplies();
        launch(supplies);
    }

    public static void main(String[] args) {
       CoffeeMachine coffeeMachine = new CoffeeMachine();
    }

    public static void fill(Supplies supplies) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water you want to add:");
        int amountOfWater = scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        int amountOfMilk = scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        int amountOfCoffeeBeans = scanner.nextInt();
        System.out.println("Write how many disposable cups you want to add:");
        int amountOfCups = scanner.nextInt();

        supplies.fill(amountOfWater, amountOfMilk, amountOfCoffeeBeans, amountOfCups);
    }

    static class Cup {
        protected int water;
        protected int milk;
        protected int coffeeBeans;
        protected int cost;

        public int getWaterForOneCup() {
            return water;
        }

        public int getMilkForOneCup() {
            return milk;
        }

        public int getCoffeeBeansForOneCup() {
            return coffeeBeans;
        }

        public int getCost() {
            return cost;
        }
    }

    static class Espresso extends Cup {
        public Espresso() {
            this.water = 250;
            this.milk = 0;
            this.coffeeBeans = 16;
            this.cost = 4;
        }
    }

    static class Latte extends Cup {
        public Latte() {
            this.water = 350;
            this.milk = 75;
            this.coffeeBeans = 20;
            this.cost = 7;
        }
    }

    static class Cappuccino extends Cup {
        public Cappuccino() {
            this.water = 200;
            this.milk = 100;
            this.coffeeBeans = 12;
            this.cost = 6;
        }

    }

    static class Supplies {
        int money = 550;
        int water = 400;
        int milk = 540;
        int beans = 120;
        int cups = 9;

        public void printState() {
            System.out.printf("""
                    
                    The coffee machine has:
                    %d ml of water
                    %d ml of milk
                    %d g of coffee beans
                    %d disposable cups
                    $%d of money
                                    
                    """, this.water, this.milk, this.beans, this.cups, this.money);
        }

        public void buy(Cup coffee) {
            boolean enoughWater = this.water - coffee.getWaterForOneCup() >= 0;
            boolean enoughMilk = this.milk - coffee.getMilkForOneCup() >= 0;
            boolean enoughBeans = this.beans - coffee.getCoffeeBeansForOneCup() >= 0;
            boolean enoughCups = this.cups - 1 >= 0;

            if (enoughWater && enoughMilk && enoughBeans && enoughCups) {
                System.out.println("I have enough resources, making you a coffee!\n");
                this.money += coffee.getCost();
                this.water -= coffee.getWaterForOneCup();
                this.milk -= coffee.getMilkForOneCup();
                this.beans -= coffee.getCoffeeBeansForOneCup();
                this.cups--;
            } else {
                String lacking = !enoughWater ? "water" : !enoughMilk ? "milk" : !enoughBeans ? "beans" : "cups";
                System.out.println("Sorry, not enough " + lacking + "!\n");
            }
        }

        public void fill(int water, int milk, int coffeeBeans, int cups) {
            this.water += water;
            this.milk += milk;
            this.beans += coffeeBeans;
            this.cups += cups;
            System.out.println();
        }

        public void takeMoney() {
            System.out.println("I gave you $" + this.money + "\n");
            this.money = 0;
        }
    }

    void launch(Supplies supplies) {
        Scanner scanner = new Scanner(System.in);
        menuLoop:
        while (true) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String action = scanner.nextLine();

            actionLoop:
            switch (action) {
                case "buy":
                    System.out.println("What do you want to buy?" +
                            " 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                    String choice = scanner.nextLine();
                    if (choice.equals("back")) {
                        continue menuLoop;
                    } else {
                        try {
                            int coffeeChoice = Integer.parseInt(choice);
                            switch (coffeeChoice) {
                                case 1:
                                    supplies.buy(new Espresso());
                                    break actionLoop;
                                case 2:
                                    supplies.buy(new Latte());
                                    break actionLoop;
                                case 3:
                                    supplies.buy(new Cappuccino());
                                    break actionLoop;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Unknown command");
                            break;
                        }
                    }

                case "fill":
                    fill(supplies);
                    break;
                case "take":
                    supplies.takeMoney();
                    break;
                case "remaining":
                    supplies.printState();
                    break;
                case "exit":
                    break menuLoop;
                default:
                    break;
            }
        }
    }
}

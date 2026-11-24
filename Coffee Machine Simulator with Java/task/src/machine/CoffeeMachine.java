package machine;

import java.util.Scanner;

public class CoffeeMachine {
    private int water = 400;
    private int milk = 540;
    private int coffeeBeans = 120;
    private int cups = 9;
    private int cash = 550;

    private int coffeesMade = 0;
    private boolean needsCleaning = false;

    private final Coffee espresso = new Coffee(250, 0, 16, 4);
    private final Coffee latte = new Coffee(350, 75, 20, 7);
    private final Coffee cappuccino = new Coffee(200, 100, 12, 6);

    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine();
        machine.run();
    }

    private void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Write action (buy, fill, take, clean, remaining, exit):");
            String action = sc.next();

            switch (action) {
                case "buy":
                    buy(sc);
                    break;
                case "fill":
                    fill(sc);
                    break;
                case "take":
                    take();
                    break;
                case "clean":
                    clean();
                    break;
                case "remaining":
                    printState();
                    break;
                case "exit":
                    return;
                default:
                    break;
            }
        }
    }

    private void buy(Scanner sc) {
        if (needsCleaning) {
            System.out.println("I need cleaning!");
            return;
        }

        System.out.println(
                "What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:"
        );

        String choice = sc.next();

        if (choice.equals("back")) {
            return;
        }

        Coffee selectedCoffee;

        switch (choice) {
            case "1":
                selectedCoffee = espresso;
                break;
            case "2":
                selectedCoffee = latte;
                break;
            case "3":
                selectedCoffee = cappuccino;
                break;
            default:
                return;
        }

        if (!hasEnoughResources(selectedCoffee)) {
            return;
        }

        System.out.println("I have enough resources, making you a coffee!");

        water -= selectedCoffee.getWater();
        milk -= selectedCoffee.getMilk();
        coffeeBeans -= selectedCoffee.getCoffeeBeans();
        cups--;
        cash += selectedCoffee.getPrice();

        coffeesMade++;

        if (coffeesMade >= 10) {
            needsCleaning = true;
        }
    }

    private boolean hasEnoughResources(Coffee coffee) {
        if (water < coffee.getWater()) {
            System.out.println("Sorry, not enough water!");
            return false;
        }

        if (milk < coffee.getMilk()) {
            System.out.println("Sorry, not enough milk!");
            return false;
        }

        if (coffeeBeans < coffee.getCoffeeBeans()) {
            System.out.println("Sorry, not enough coffee!");
            return false;
        }

        if (cups < 1) {
            System.out.println("Sorry, not enough disposable cups!");
            return false;
        }

        return true;
    }

    private void fill(Scanner sc) {
        System.out.println("Write how many ml of water you want to add:");
        water += sc.nextInt();

        System.out.println("Write how many ml of milk you want to add:");
        milk += sc.nextInt();

        System.out.println("Write how many grams of coffee beans you want to add:");
        coffeeBeans += sc.nextInt();

        System.out.println("Write how many disposable cups you want to add:");
        cups += sc.nextInt();
    }

    private void take() {
        System.out.println("I gave you $" + cash);
        cash = 0;
    }

    private void clean() {
        coffeesMade = 0;
        needsCleaning = false;
        System.out.println("I have been cleaned!");
    }

    private void printState() {
        System.out.println("\nThe coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(coffeeBeans + " g of coffee beans");
        System.out.println(cups + " disposable cups");
        System.out.println("$" + cash + " of money");
    }
}
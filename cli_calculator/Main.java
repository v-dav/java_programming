package calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Double> items = new HashMap<String, Double>();
        items.put("Bubblegum", 202.0);
        items.put("Toffee", 118.0);
        items.put("Ice Cream", 2250.0);
        items.put("Milk chocolate", 1680.0);
        items.put("Doughnut", 1075.0);
        items.put("Pancake", 80.0);

        System.out.println("Earned amount:");
        items.forEach((k, v) -> System.out.println(k + ": " + "$" + v));
        System.out.println("\n");

        double total = 0;
        for (Map.Entry<String, Double> entry : items.entrySet()) {
            total += entry.getValue();
        }
        System.out.println("Income: " + "$" + total);

        Scanner sc = new Scanner(System.in);
        System.out.println("Staff expenses:");
        double staffExpenses = sc.nextDouble();

        System.out.println("Other expenses:");
        double otherExpenses = sc.nextDouble();

        double netIncome = total - staffExpenses - otherExpenses;

        System.out.println("Net income: " + "$" + netIncome);
    }
}
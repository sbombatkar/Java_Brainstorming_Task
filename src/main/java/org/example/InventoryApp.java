package org.example;

import org.example.inventory.InventoryManager;
import org.example.order.Order;

import java.util.*;

public class InventoryApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InventoryManager manager = new InventoryManager();

        System.out.println("=== Welcome to Inventory System ===");

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. View Inventory Items");
            System.out.println("2. View Wallet Balance");
            System.out.println("3. Buy an Item by ID");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Inventory Items ---");
                    manager.listItems();
                    break;

                case 2:
                    System.out.println("\nCurrent Balance: ₹" + manager.getBalance());
                    break;




                case 3:
//                    System.out.print("Enter Item ID to buy: ");
                    Order.startProcessing(manager);
                    break;

                case 4:
                    System.out.println("Exiting Inventory System. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

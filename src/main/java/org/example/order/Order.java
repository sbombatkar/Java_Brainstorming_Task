package org.example.order;

import org.example.inventory.InventoryManager;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Order implements Runnable {
    private InventoryManager manager;
    private String itemId;
    private int quantity;

    public Order(InventoryManager manager, String itemId, int quantity) {
        this.manager = manager;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        System.out.println("[" + threadName + "] Processing order: Item ID = " + itemId + ", Quantity = " + quantity);

        synchronized (manager) {
            if (!manager.itemExists(itemId)) {
                System.out.println("[" + threadName + "] Item ID not found. Order cancelled.");
                return;
            }

            if (!manager.isItemAvailable(itemId, quantity)) {
                System.out.println("[" + threadName + "] Requested quantity not available. Order cancelled.");
                return;
            }

            double price = manager.getItemPrice(itemId);
            double totalAmount = price * quantity;

            if (!manager.deductFromWallet(totalAmount)) {
                System.out.println("[" + threadName + "] Insufficient balance. Order cancelled.");
                return;
            }

            if (manager.updateInventory(itemId, quantity)) {
                System.out.println("[" + threadName + "] Order placed successfully.");
            } else {
                System.out.println("[" + threadName + "] Failed to update inventory. Rolling back payment.");
                // Rollback payment
                synchronized (manager) {
                    manager.deductFromWallet(-totalAmount);
                }
            }
        }
    }

    public static void startProcessing(InventoryManager manager) {
        Scanner scanner = new Scanner(System.in);

        int numberOfOrders = 0;
        while (true) {
            try {
                System.out.print("Enter number of orders to place: ");
                numberOfOrders = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (numberOfOrders <= 0) {
                    System.out.println("Please enter a number greater than 0.");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid number. Please enter a valid integer.");
                scanner.nextLine(); // clear invalid input
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(3);

//        for (int i = 1; i <= numberOfOrders; i++) {
            String itemId;
            int quantity = 0;

            System.out.print("Enter Item ID for order " + ": ");
            itemId = scanner.nextLine().trim();

            while (true) {
                try {
                    System.out.print("Enter Quantity for order " + ": ");
                    quantity = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    if (quantity <= 0) {
                        System.out.println("Quantity must be greater than 0.");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid quantity. Please enter a valid integer.");
                    scanner.nextLine(); // clear invalid input
                }
            }

            executor.submit(new Order(manager, itemId, quantity));
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executor.shutdown();
//        }
//
//        executor.execute(()-> System.out.println("j"));
    }
}
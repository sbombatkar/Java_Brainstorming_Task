package org.example.inventory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryManager {

    private final Map<String, InventoryItem> inventoryMap = new ConcurrentHashMap<>();
    private double walletBalance;

    public InventoryManager() {
        this.walletBalance = 100000.0; // Initial balance
        preloadItems();
    }

    private void preloadItems() {
        addItem(new InventoryItem("I001", "Laptop", 10, 75000.0));
        addItem(new InventoryItem("I002", "Phone", 20, 25000.0));
        addItem(new InventoryItem("I003", "Monitor", 15, 12000.0));
        addItem(new InventoryItem("I004", "Keyboard", 50, 1500.0));
        addItem(new InventoryItem("I005", "Mouse", 60, 800.0));
        addItem(new InventoryItem("I006", "Printer", 5, 18000.0));
        addItem(new InventoryItem("I007", "Webcam", 25, 3500.0));
        addItem(new InventoryItem("I008", "Headphones", 40, 2000.0));
        addItem(new InventoryItem("I009", "Tablet", 12, 30000.0));
        addItem(new InventoryItem("I010", "Smartwatch", 18, 10000.0));
    }

    private void addItem(InventoryItem item) {
        inventoryMap.put(item.getItemId(), item);
    }

    public double getBalance() {
        return walletBalance;
    }

    public boolean itemExists(String itemId) {
        return inventoryMap.containsKey(itemId);
    }

    public boolean isItemAvailable(String itemId, int quantity) {
        InventoryItem item = inventoryMap.get(itemId);
        return item != null && item.getQuantity() >= quantity;
    }

    public double getItemPrice(String itemId) {
        InventoryItem item = inventoryMap.get(itemId);
        if (item != null) {
            return item.getPrice();
        }
        throw new IllegalArgumentException("Item with ID " + itemId + " not found.");
    }

    public synchronized boolean deductFromWallet(double amount) {
        if (walletBalance >= amount) {
            walletBalance -= amount;
            return true;
        }
        return false;
    }

    public synchronized boolean updateInventory(String itemId, int quantity) {
        InventoryItem item = inventoryMap.get(itemId);
        if (item != null && item.getQuantity() >= quantity) {
            item.setQuantity(item.getQuantity() - quantity);
            return true;
        }
        return false;
    }

    public void listItems() {
        for (InventoryItem item : inventoryMap.values()) {
            System.out.println(item);
        }
    }
}

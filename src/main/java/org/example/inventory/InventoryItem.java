package org.example.inventory;

public class InventoryItem {
    private final String itemId;
    private final String itemName;
    private int quantity;
    private final double price;

    public InventoryItem(String itemId, String itemName, int quantity, double price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public synchronized int getQuantity() {
        return quantity;
    }

    public synchronized void increaseQuantity(int amount) {
        if (amount > 0) {
            this.quantity += amount;
        }
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Item: " + itemName + " | ID: " + itemId + " | Qty: " + quantity + " | Price: " + price;
    }
}
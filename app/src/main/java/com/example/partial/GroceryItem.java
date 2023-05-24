package com.example.partial;

public class GroceryItem {
    String groceryItem;
    int groceryQuantity;

    public GroceryItem(String groceryItem, int groceryQuantity) {
        this.groceryItem = groceryItem;
        this.groceryQuantity = groceryQuantity;
    }

    public String getGroceryItem() {
        return groceryItem;
    }

    public void setGroceryItem(String groceryItem) {
        this.groceryItem = groceryItem;
    }

    public int getGroceryQuantity() {
        return groceryQuantity;
    }

    public void setGroceryQuantity(int groceryQuantity) {
        this.groceryQuantity = groceryQuantity;
    }
}

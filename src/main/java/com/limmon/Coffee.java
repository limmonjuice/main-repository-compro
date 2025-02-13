package com.limmon;

import java.util.ArrayList;
import java.util.Arrays;

public class Coffee {

    //attributes of Coffee
    String name;
    String type;
    String size;
    double price;
    String roastLevel;
    String origin;
    boolean isDecaf;
    int stock;
    public ArrayList<String> flavorNotes;
    String brewMethod;

    /**
     * constructor
     *
     * @param name         The name of the coffee.
     * @param type         The type of coffee (e.g., espresso, latte).
     * @param size         The size of the coffee (small, medium, large).
     * @param price        The base price of the coffee.
     * @param roastLevel   The roast level of the coffee (e.g., light, medium, dark).
     * @param origin       The origin of the coffee beans.
     * @param isDecaf      Flag indicating whether the coffee is decaffeinated.
     * @param stock        The current stock of the coffee.
     * @param flavorNotes  Array of flavor notes for the coffee.
     * @param brewMethod   The method used to brew the coffee (e.g., drip, French press).
     */
    public Coffee(String name, String type, String size, double price, String roastLevel, String origin,
                   boolean isDecaf, int stock, String[] flavorNotes, String brewMethod) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.price = 100; //Base price for all coffees
        this.roastLevel = roastLevel;
        this.origin = origin;
        this.isDecaf = isDecaf;
        this.stock = stock;
        this.flavorNotes = new ArrayList<>(Arrays.asList(flavorNotes));
        this.brewMethod = brewMethod;
    }

    /**
     * method to calculate the price based on the size of the coffee.
     *
     * @param size The size of the coffee (small, medium, large).
     */
    public void calculatePrice(String size) {
        switch (size.toLowerCase()) {
            case "small":
                this.price += 10;
                break;
            case "medium":
                this.price += 30;
                break;
            case "large":
                this.price += 50;
                break;
            default:
                System.out.println("Invalid size. Keeping base price.");
        }
        System.out.println("Price of " + this.name + " (" + this.size + "): PHP " + this.price);
    }

    //method to check if the coffee is in stock.
    public void checkStock() {
        System.out.println("Stock for " + this.name + ": " + (this.stock > 0 ? "Available" : "Out of stock"));
    }

    /**
     * method to add a new flavor note to the coffee.
     *
     * @param note The flavor note to add.
     */
    public void addFlavor(String note) {
        this.flavorNotes.add(note);
        System.out.println("Added flavor: " + note);
    }

    /**
     * method to update the stock of the coffee.
     *
     * @param quantity The quantity to add to the stock.
     */
    public void updateStock(int quantity) {
        this.stock += quantity;
        System.out.println("Updated stock for " + this.name + ": " + this.stock);
    }

    //method to describe the coffee including its roast level and flavor notes.
    public void describe() {
        System.out.println("Description: A " + this.roastLevel + " roast " + this.name + " with " + String.join(", ", this.flavorNotes) + " notes.");
    }

    /**
     * method to update the decaf status of the coffee.
     *
     * @param isDecaf Flag indicating whether the coffee is decaffeinated.
     */
    public void setDecaf(boolean isDecaf) {
        this.isDecaf = isDecaf;
        System.out.println(this.name + " is now " + (this.isDecaf ? "decaffeinated" : "regular"));
    }

    /**
     * method to change the roast level of the coffee.
     *
     * @param newRoastLevel The new roast level for the coffee.
     */
    public void changeRoastLevel(String newRoastLevel) {
        this.roastLevel = newRoastLevel;
        System.out.println("Roast level of " + this.name + " changed to " + this.roastLevel);
    }

    /**
     * method to apply a discount to the price of the coffee.
     *
     * @param percentage The discount percentage to apply.
     */
    public void discount(int percentage) {
        double discountAmount = (this.price * percentage) / 100;
        this.price -= discountAmount;
        System.out.println("Discount applied! New price of " + this.name + ": PHP " + this.price);
    }
}

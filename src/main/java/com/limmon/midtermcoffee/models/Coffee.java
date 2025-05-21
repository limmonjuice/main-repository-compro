package com.limmon.midtermcoffee.models;

import jakarta.validation.constraints.*;

import java.util.List;

/**
 * Represents a coffee item with various attributes such as name, type, size, price, roast level, origin, and more.
 */
public class Coffee {
    private int id;

    @Size(min = 2, max= 50, message = "Name should have 2 to 50 characters")
    private String name;

    @NotBlank(message = "Type of Coffee is required")
    private String type;

    @NotBlank(message = "Size of Coffee is required")
    private String size;

    @DecimalMin(value= "0.01", message = "Price of Coffee is required and must be more than 0")
    private double price;

    @NotBlank(message = "Roast Level of Coffee is required")
    private String roastLevel;

    @Size(max= 100, message= "Enter only less than 100 characters")
    private String origin;

    private boolean isDecaf;

    @Min(value = 0, message = "Stock of Coffee must at least be 0")
    private int stock;

    @NotEmpty(message = "At least one flavor note must be selected")
    private List<String> flavorNotes;

    @NotBlank(message = "Brew Method of Coffee is required")
    private String brewMethod;

    private String coffeePicture;

    public Coffee() {}

    /**
     * Constructs a new Coffee object with the given parameters.
     *
     * @param id         Unique identifier for the coffee.
     * @param name       Name of the coffee.
     * @param type       Type of coffee bean used.
     * @param size       Size of the coffee serving.
     * @param price      Price of the coffee.
     * @param roastLevel Roast level of the coffee beans.
     * @param origin     Origin of the coffee beans.
     * @param isDecaf    Whether the coffee is decaffeinated.
     * @param stock      Quantity of this coffee available in stock.
     * @param flavorNotes List of flavor notes describing the coffee.
     * @param brewMethod Preferred brewing method for this coffee.
     */

    public Coffee(int id, String name, String type, String size, double price, String roastLevel, String origin, boolean isDecaf, int stock, List<String> flavorNotes, String brewMethod, String coffeePicture) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.size = size;
        this.price = price;
        this.roastLevel = roastLevel;
        this.origin = origin;
        this.isDecaf = isDecaf;
        this.stock = stock;
        this.flavorNotes = flavorNotes;
        this.brewMethod = brewMethod;
        this.coffeePicture = coffeePicture;
    }

    /** @return The unique identifier of the coffee. */
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    /** @return The name of the coffee. */
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    /** @return The type of coffee bean used. */
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    /** @return The size of the coffee serving. */
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    /** @return The price of the coffee. */
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    /** @return The roast level of the coffee beans. */
    public String getRoastLevel() { return roastLevel; }
    public void setRoastLevel(String roastLevel) { this.roastLevel = roastLevel; }

    /** @return The origin of the coffee beans. */
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    /** @return True if the coffee is decaffeinated, false otherwise. */
    public boolean isDecaf() { return isDecaf; }
    public void setDecaf(boolean isDecaf) { this.isDecaf = isDecaf; }

    /** @return The stock quantity available for this coffee. */
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    /** @return The list of flavor notes describing the coffee. */
    public List<String> getFlavorNotes() { return flavorNotes; }
    public void setFlavorNotes(List<String> flavorNotes) { this.flavorNotes = flavorNotes; }

    /** @return The preferred brewing method for this coffee. */
    public String getBrewMethod() { return brewMethod; }
    public void setBrewMethod(String brewMethod) { this.brewMethod = brewMethod; }

    public String getCoffeePicture() { return coffeePicture; }
    public void setCoffeePicture(String coffeePicture) { this.coffeePicture = coffeePicture; }
}
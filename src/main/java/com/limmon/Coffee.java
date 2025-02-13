package com.limmon;

class Coffee {
    // Attributes
    private String name;
    private String type;
    private String size;
    private double price;
    private String roastLevel;
    private String origin;
    private boolean isDecaf;
    private int stock;
    private String[] flavorNotes;
    private String brewMethod;


    public Coffee(String name, String type) {
        this.name = name;
        this.type = type;
        this.size = "";
        this.price = 0.0;
        this.roastLevel = "";
        this.origin = "";
        this.isDecaf = false;
        this.stock = 0;
        this.brewMethod = "";
        this.flavorNotes = new String[0];
    }


    public void setSize(String size) { this.size = size; }
    public void setPrice(double price) { this.price = price; }
    public void setRoastLevel(String roastLevel) { this.roastLevel = roastLevel; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setDecaf(boolean isDecaf) { this.isDecaf = isDecaf; }
    public void setStock(int stock) { this.stock = stock; }
    public void setBrewMethod(String brewMethod) { this.brewMethod = brewMethod; }
    public void setFlavorNotes(String[] flavorNotes) { this.flavorNotes = flavorNotes; }


    public String getName() { return this.name; }
    public String getType() { return this.type; }
    public String getSize() { return this.size; }
    public double getPrice() { return this.price; }
    public String getRoastLevel() { return this.roastLevel; }
    public String getOrigin() { return this.origin; }
    public boolean isDecaf() { return this.isDecaf; }
    public int getStock() { return this.stock; }
    public String getBrewMethod() { return this.brewMethod; }
    public String getFlavorNotes() {
        if (this.flavorNotes.length == 0) return "No flavor notes";
        return String.join(",", this.flavorNotes);
    }


    public void displayDescription() {
        System.out.println("Coffee Name: " + getName());
        System.out.println("Type: " + getType());
        System.out.println("Size: " + getSize());
        System.out.println("Price: $" + getPrice());
        System.out.println("Roast Level: " + getRoastLevel());
        System.out.println("Origin: " + getOrigin());
        System.out.println("Decaf: " + (isDecaf() ? "Yes" : "No"));
        System.out.println("Stock: " + getStock());
        System.out.println("Brew Method: " + getBrewMethod());
        System.out.println("Flavor Notes: " + getFlavorNotes());
        System.out.println("-----------------------------");
    }
}



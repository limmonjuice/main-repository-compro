package com.limmon;

class Coffee {
    private String name;
    private String type;
    private String size;
    private double price;
    private double originalPrice;
    private String roastLevel;
    private String origin;
    private boolean isDecaf;
    private int stock;
    private int originalStock;
    private String[] flavorNotes;
    private String brewMethod;

    public Coffee() {
        this.name = "";
        this.type = "";
        this.size = "";
        this.price = 0.0;
        this.originalPrice = 0.0;
        this.roastLevel = "";
        this.origin = "";
        this.isDecaf = false;
        this.stock = 0;
        this.originalStock = 0;
        this.brewMethod = "";
        this.flavorNotes = new String[0];
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setPrice(double price) {
        this.price = price;
        this.originalPrice = price;
    }
    public void setStock(int stock) {
        this.stock = stock;
        if (this.originalStock == 0) {
            this.originalStock = stock;
        }
    }
    public void discount(double percentage) {
        this.price = this.originalPrice - (this.originalPrice * (percentage / 100));
    }
    public void setSize(String size) {
        this.size = size;
    }
    public void setRoastLevel(String roastLevel) {
        this.roastLevel = roastLevel;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public void setDecaf(boolean isDecaf) {
        this.isDecaf = isDecaf;
    }
    public void setBrewMethod(String brewMethod) {
        this.brewMethod = brewMethod;
    }
    public void setFlavorNotes(String[] flavorNotes) {
        this.flavorNotes = flavorNotes;
    }
    public void addFlavor(String note) {
        String[] newFlavorNotes = new String[this.flavorNotes.length + 1];
        for (int i = 0; i < this.flavorNotes.length; i++) {
            newFlavorNotes[i] = this.flavorNotes[i];
        }
        newFlavorNotes[this.flavorNotes.length] = note;
        this.flavorNotes = newFlavorNotes;
    }
    public void updateStock(int quantity) {
        this.stock += quantity;
    }

    public String describe() {
        StringBuilder description = new StringBuilder();
        description.append("Coffee Name: ").append(this.name).append("\n");
        description.append("Type: ").append(this.type).append("\n");
        description.append("Size: ").append(this.size).append("\n");
        description.append("Original Price: PHP ").append(this.originalPrice).append("\n");
        description.append("Discounted Price: PHP ").append(this.price).append("\n");
        description.append("Roast Level: ").append(this.roastLevel).append("\n");
        description.append("Origin: ").append(this.origin).append("\n");
        description.append("Decaf: ").append(this.isDecaf ? "Yes" : "No").append("\n");
        description.append("Original Stock: ").append(this.originalStock).append("\n");
        description.append("Updated Stock: ").append(this.stock).append("\n");
        description.append("Brew Method: ").append(this.brewMethod).append("\n");
        description.append("Flavor Notes: ");
        if (this.flavorNotes.length == 0) {
            description.append("No flavor notes");
        } else {
            for (int i = 0; i < this.flavorNotes.length; i++) {
                description.append(this.flavorNotes[i]);
                if (i < this.flavorNotes.length - 1) {
                    description.append(", ");
                }
            }
        }
        description.append("\n");
        return description.toString();
    }


}

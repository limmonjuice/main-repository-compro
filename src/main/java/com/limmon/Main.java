//side note = Sir, di ko magets yung functionaliy ng calculatePrice na method so nagset ako ng hardcoded value of 100
//as a base price of all coffees, tsaka sa calculatePrice() na method ko, is mag-aadd ng certain price if yung size ba is small,
//medium, or large. thank you sir!

package com.limmon;

public class Main {
    public static void main(String[] args) {
        //first coffee object
        Coffee coffee1 = new Coffee(
                "Espresso", "Arabica", "Medium", 100, "Dark", "Colombia", false, 10,
                new String[]{"Chocolate", "Nutty"}, "French Press"
        );

        //second coffee object
        Coffee coffee2 = new Coffee(
                "Latte", "Robusta", "Large", 100, "Medium", "Brazil", false, 5,
                new String[]{"Caramel", "Creamy"}, "Drip"
        );

        //properties for coffee 1
        System.out.println("Coffee 1 Details:");
        System.out.println("Name: " + coffee1.name);
        System.out.println("Type: " + coffee1.type);
        System.out.println("Size: " + coffee1.size);
        System.out.println("Base Price of all coffees: PHP" + coffee1.price);
        System.out.println("Roast Level: " + coffee1.roastLevel);
        System.out.println("Origin: " + coffee1.origin);
        System.out.println("Is Decaf: " + coffee1.isDecaf);
        System.out.println("Stock: " + coffee1.stock);
        System.out.println("Flavor Notes: " + String.join(", ", coffee1.flavorNotes));
        System.out.println("Brew Method: " + coffee1.brewMethod);
        System.out.println();

        //properties for coffee 2
        System.out.println("Coffee 2 Details:");
        System.out.println("Name: " + coffee2.name);
        System.out.println("Type: " + coffee2.type);
        System.out.println("Size: " + coffee2.size);
        System.out.println("Base Price of all coffees: PHP" + coffee2.price);
        System.out.println("Roast Level: " + coffee2.roastLevel);
        System.out.println("Origin: " + coffee2.origin);
        System.out.println("Is Decaf: " + coffee2.isDecaf);
        System.out.println("Stock: " + coffee2.stock);
        System.out.println("Flavor Notes: " + String.join(", ", coffee2.flavorNotes));
        System.out.println("Brew Method: " + coffee2.brewMethod);
        System.out.println();

        //methods for coffee 1
        System.out.println("\n=== Coffee 1 ===");
        coffee1.describe();
        coffee1.calculatePrice(coffee1.size);
        coffee1.updateStock(5);
        coffee1.checkStock();
        coffee1.addFlavor("Vanilla");
        coffee1.setDecaf(true);
        coffee1.changeRoastLevel("Light");
        coffee1.discount(15);

        //methods for coffee 2
        System.out.println("\n=== Coffee 2 ===");
        coffee2.describe();
        coffee2.calculatePrice(coffee2.size);
        coffee2.updateStock(-2);
        coffee2.checkStock();
        coffee2.addFlavor("Hazelnut");
        coffee2.setDecaf(false);
        coffee2.changeRoastLevel("Dark");
        coffee2.discount(10);

        //updated coffee 1 details
        System.out.println();
        System.out.println("Updated Coffee 1 Details:");
        coffee1.describe();
        System.out.println("Name: " + coffee1.name);
        System.out.println("Type: " + coffee1.type);
        System.out.println("Size: " + coffee1.size);
        System.out.println("Updated Price: PHP" + coffee1.price);
        System.out.println("Roast Level: " + coffee1.roastLevel);
        System.out.println("Origin: " + coffee1.origin);
        System.out.println("Is Decaf: " + coffee1.isDecaf);
        System.out.println("Stock: " + coffee1.stock);
        System.out.println("Flavor Notes: " + String.join(", ", coffee1.flavorNotes));
        System.out.println("Brew Method: " + coffee1.brewMethod);
        System.out.println();

        //updated coffee 2 details
        System.out.println();
        System.out.println("Updated Coffee 2 Details:");
        coffee1.describe();
        System.out.println("Name: " + coffee2.name);
        System.out.println("Type: " + coffee2.type);
        System.out.println("Size: " + coffee2.size);
        System.out.println("Updated Price: PHP" + coffee2.price);
        System.out.println("Roast Level: " + coffee2.roastLevel);
        System.out.println("Origin: " + coffee2.origin);
        System.out.println("Is Decaf: " + coffee2.isDecaf);
        System.out.println("Stock: " + coffee2.stock);
        System.out.println("Flavor Notes: " + String.join(", ", coffee2.flavorNotes));
        System.out.println("Brew Method: " + coffee2.brewMethod);
        System.out.println();
    }
}

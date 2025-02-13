package com.limmon;

public class Main {
    public static void main(String[] args) {

        Coffee coffee1 = new Coffee();

        coffee1.setName("Espresso");
        coffee1.setType("Arabica");
        coffee1.setSize("Medium");
        coffee1.setPrice(100.0);
        coffee1.discount(10);
        coffee1.setRoastLevel("Dark");
        coffee1.setOrigin("Brazil");
        coffee1.setDecaf(false);
        coffee1.setStock(10);
        coffee1.updateStock(3);
        coffee1.setBrewMethod("French Press");
        coffee1.setFlavorNotes(new String[]{"Chocolate", "Nutty"});
        coffee1.addFlavor("Matcha");

        System.out.println(coffee1.describe());
        System.out.println("---------------------------------\n");

        Coffee coffee2 = new Coffee();
        coffee2.setName("Latte");
        coffee2.setType("Robusta");
        coffee2.setSize("Large");
        coffee2.setPrice(150.0);
        coffee2.discount(10);
        coffee2.setRoastLevel("Medium");
        coffee2.setOrigin("Colombia");
        coffee2.setDecaf(true);
        coffee2.setStock(5);
        coffee2.updateStock(4);
        coffee2.setBrewMethod("Drip");
        coffee2.setFlavorNotes(new String[]{"Caramel", "Vanilla"});
        coffee2.addFlavor("Cinnamon");

        System.out.println(coffee2.describe());
        System.out.println("---------------------------------");
    }
}

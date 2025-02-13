package com.limmon;

public class Main {
    public static void main(String[] args) {

        Coffee coffee1 = new Coffee("Espresso", "Arabica");


        coffee1.setSize("Medium");
        coffee1.setPrice(100.0);
        coffee1.setRoastLevel("Dark");
        coffee1.setOrigin("Brazil");
        coffee1.setDecaf(false);
        coffee1.setStock(10);
        coffee1.setBrewMethod("Espresso");
        coffee1.setFlavorNotes(new String[]{"Chocolate", "Nutty"});


        Coffee coffee2 = new Coffee("Latte", "Robusta");


        coffee2.setSize("Large");
        coffee2.setPrice(150.0);
        coffee2.setRoastLevel("Medium");
        coffee2.setOrigin("Colombia");
        coffee2.setDecaf(true);
        coffee2.setStock(5);
        coffee2.setBrewMethod("Drip");
        coffee2.setFlavorNotes(new String[]{"Chocolate", "Nutty"});


        coffee1.displayDescription();
        coffee2.displayDescription();
    }
}
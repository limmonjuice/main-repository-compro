package com.limmon;

public class Main {
    public static void main(String[] args) {
        // Create Car objects (instances of the Car class)
        Car car1 = new Car("Toyota", "Corolla", 2020, "Blue");
        Car car2 = new Car("Honda", "Civic", 2022, "Red");

        // Call methods on the objects
        car1.displayDetails();
        car1.start();

        System.out.println(); // Add a line break for readability

        car2.displayDetails();
        car2.start();
    }
}

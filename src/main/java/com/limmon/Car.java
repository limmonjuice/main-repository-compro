package com.limmon;
// File: Car.java

// Define the Car class
public class Car {
    // Fields (attributes of the Car)
    String make;
    String model;
    int year;
    String color;

    // Constructor to initialize the Car object
    public Car(String make, String model, int year, String color) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
    }

    // Method to display car details
    public void displayDetails() {
        System.out.println("Car Make: " + make);
        System.out.println("Car Model: " + model);
        System.out.println("Car Year: " + year);
        System.out.println("Car Color: " + color);
    }

    // Method to simulate the car starting
    public void start() {
        System.out.println(make + " " + model + " is starting.");
    }
}

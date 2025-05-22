package com.limmon.midtermcoffee.services;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.limmon.midtermcoffee.models.Coffee;
import org.springframework.stereotype.Service;

/**
 * Service class for managing coffee data including loading, saving,
 * searching, and updating coffee records stored in a CSV file.
 */
@Service
public class CoffeeService {
    private List<Coffee> coffeeList;
    private final String FILE_NAME = "coffee_database.csv";

    /**
     * Constructor that initializes the coffee list by loading data from file,
     * and if empty, populates with sample data and saves it.
     */
    public CoffeeService() {
        coffeeList = new ArrayList<>();
        loadCoffeeData();

        if (coffeeList.isEmpty()) {
            addSampleData();
            saveCoffeeData();
        }
    }

    /**
     * Adds sample coffee data to the in-memory list.
     */
    private void addSampleData() {
        coffeeList.add(new Coffee(1, "Espresso", "Arabica", "Small", 70, "Dark", "Ethiopia", false, 10,
                Arrays.asList("Chocolate", "Nutty"), "Espresso", "d0fb3c80-06db-4863-a6cb-551f4f7730cc.jpg"));
        coffeeList.add(new Coffee(2, "Latte", "Arabica", "Medium", 80, "Medium", "Brazil", false, 8,
                Arrays.asList("Creamy", "Sweet"), "Drip", "e2fc3a53-fbd0-4eea-9186-7ecb7d072042.jpg"));
        coffeeList.add(new Coffee(3, "Cappuccino", "Robusta", "Large", 90, "Medium", "Colombia", false, 12,
                Arrays.asList("Fruity", "Bold"), "French Press", "7836abcd-5a9e-4664-aaba-1d4c8787d90e.jpg"));
        coffeeList.add(new Coffee(4, "Mocha", "Arabica", "Medium", 100, "Dark", "Guatemala", false, 6,
                Arrays.asList("Chocolate", "Smooth"), "Espresso", "12977b5e-1b0b-4b5b-a140-c10d8fc30820.jpg"));
        coffeeList.add(new Coffee(5, "Americano", "Robusta", "Large", 60, "Light", "Kenya", false, 15,
                Arrays.asList("Citrus", "Balanced"), "Drip", "e6a67a0d-ee9b-4f12-9fca-f06e2c895438.jpg"));
    }

    /**
     * Loads coffee data from the CSV file into the in-memory list.
     * If the file does not exist, the method does nothing.
     */
    public void loadCoffeeData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split by commas ignoring commas inside quotes
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                Coffee coffee = new Coffee();
                coffee.setId(Integer.parseInt(data[0]));
                coffee.setName(data[1]);
                coffee.setType(data[2]);
                coffee.setSize(data[3]);
                coffee.setPrice(Double.parseDouble(data[4]));
                coffee.setRoastLevel(data[5]);
                coffee.setOrigin(data[6]);
                coffee.setDecaf(Boolean.parseBoolean(data[7]));
                coffee.setStock(Integer.parseInt(data[8]));
                coffee.setFlavorNotes(Arrays.asList(data[9].replace("\"", "").split("\\s*,\\s*")));
                coffee.setBrewMethod(data[10].replace("\"", ""));
                coffee.setCoffeePicture(data[11]);

                coffeeList.add(coffee);
            }
        } catch (IOException e) {
            System.out.println("Error reading coffee data: " + e.getMessage());
        }
    }

    /**
     * Saves the current coffee list data to the CSV file.
     */
    public void saveCoffeeData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Coffee coffee : coffeeList) {
                bw.write(coffee.getId() + "," +
                        coffee.getName() + "," +
                        coffee.getType() + "," +
                        coffee.getSize() + "," +
                        coffee.getPrice() + "," +
                        coffee.getRoastLevel() + "," +
                        coffee.getOrigin() + "," +
                        coffee.isDecaf() + "," +
                        coffee.getStock() + "," +
                        "\"" + String.join(", ", coffee.getFlavorNotes()) + "\"" + "," +
                        coffee.getBrewMethod() + "," +
                        coffee.getCoffeePicture());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing coffee data: " + e.getMessage());
        }
    }

    /**
     * Returns the list of all coffees.
     *
     * @return the list of Coffee objects.
     */
    public List<Coffee> getCoffees() {
        return coffeeList;
    }

    /**
     * Updates a coffee entry identified by the given ID with the provided updatedCoffee.
     * Saves changes to the CSV file after updating.
     *
     * @param id           the ID of the coffee to update.
     * @param updatedCoffee the updated Coffee object.
     */
    public void updateCoffee(int id, Coffee updatedCoffee) {
        for (int i = 0; i < coffeeList.size(); i++) {
            if (coffeeList.get(i).getId() == id) {
                coffeeList.set(i, updatedCoffee);
                saveCoffeeData();
                return;
            }
        }
    }

    /**
     * Adds a new Coffee to the list, assigns it a new sequential ID,
     * and saves the updated list to the CSV file.
     *
     * @param coffee the Coffee object to add.
     */
    public void addCoffee(Coffee coffee) {
        coffee.setId(coffeeList.size() + 1);  // Assign next sequential ID

        coffeeList.add(coffee);
        saveCoffeeData();
    }

    /**
     * Finds a Coffee by its ID.
     *
     * @param id the coffee ID.
     * @return the Coffee object if found; otherwise null.
     */
    public Coffee getCoffeeById(int id) {
        for (Coffee coffee : coffeeList) {
            if (coffee.getId() == id) {
                return coffee;
            }
        }
        return null;
    }

    /**
     * Deletes the coffee with the specified ID from the list,
     * reassigns IDs to maintain sequence, and saves the updated list.
     *
     * @param id the ID of the coffee to delete.
     */
    public void deleteCoffeeById(int id) {
        coffeeList.removeIf(coffee -> coffee.getId() == id);

        // Reassign IDs starting from 1 to maintain sequence without gaps
        for (int i = 0; i < coffeeList.size(); i++) {
            coffeeList.get(i).setId(i + 1);
        }

        saveCoffeeData();
    }

    /**
     * Searches for coffees by matching the keyword against multiple fields,
     * including id, name, type, size, origin, roast level, brew method,
     * flavor notes, price, and decaf status.
     *
     * @param keyword the search keyword.
     * @return a list of Coffee objects that match the keyword.
     */
    public List<Coffee> searchCoffeesByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return coffeeList;
        }
        return coffeeList.stream()
                .filter(coffee -> String.valueOf(coffee.getId()).equals(keyword)
                        || coffee.getName().toLowerCase().contains(keyword.toLowerCase())
                        || coffee.getType().toLowerCase().contains(keyword.toLowerCase())
                        || coffee.getSize().toLowerCase().contains(keyword.toLowerCase())
                        || coffee.getOrigin().toLowerCase().contains(keyword.toLowerCase())
                        || coffee.getRoastLevel().toLowerCase().contains(keyword.toLowerCase())
                        || coffee.getBrewMethod().toLowerCase().contains(keyword.toLowerCase())
                        || String.join(" ", coffee.getFlavorNotes()).toLowerCase().contains(keyword.toLowerCase())
                        || String.valueOf(coffee.getPrice()).contains(keyword)
                        || (List.of("true", "decaf", "yes").contains(keyword.toLowerCase()) && coffee.isDecaf())
                        || (List.of("false", "regular", "no").contains(keyword.toLowerCase()) && !coffee.isDecaf()))
                .collect(Collectors.toList());
    }
}

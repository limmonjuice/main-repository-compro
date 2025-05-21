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

    public CoffeeService() {
        coffeeList = new ArrayList<>();
        loadCoffeeData();


        if (coffeeList.isEmpty()) {
            addSampleData();
            saveCoffeeData();
        }
    }


    private void addSampleData() {
        coffeeList.add(new Coffee(1, "Espresso", "Arabica", "Small", 3.50, "Dark", "Ethiopia", false, 10,
                Arrays.asList("Chocolate", "Nutty"), "Espresso", "50c9d4cb-67e0-4a18-9c6b-4a5785b8eda7.jpg"));
        coffeeList.add(new Coffee(2, "Latte", "Arabica", "Medium", 4.50, "Medium", "Brazil", false, 8,
                Arrays.asList("Creamy", "Sweet"), "Drip", "50c9d4cb-67e0-4a18-9c6b-4a5785b8eda7.jpg"));
        coffeeList.add(new Coffee(3, "Cappuccino", "Robusta", "Large", 5.00, "Medium", "Colombia", false, 12,
                Arrays.asList("Fruity", "Bold"), "French Press", "50c9d4cb-67e0-4a18-9c6b-4a5785b8eda7.jpg"));
        coffeeList.add(new Coffee(4, "Mocha", "Arabica", "Medium", 4.75, "Dark", "Guatemala", false, 6,
                Arrays.asList("Chocolate", "Smooth"), "Espresso", "50c9d4cb-67e0-4a18-9c6b-4a5785b8eda7.jpg"));
        coffeeList.add(new Coffee(5, "Americano", "Robusta", "Large", 3.25, "Light", "Kenya", false, 15,
                Arrays.asList("Citrus", "Balanced"), "Drip", "50c9d4cb-67e0-4a18-9c6b-4a5785b8eda7.jpg"));
    }



    public void loadCoffeeData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
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

    public List<Coffee> getCoffees() {
        return coffeeList;
    }

    public void updateCoffee(int id, Coffee updatedCoffee) {
        for (int i = 0; i < coffeeList.size(); i++) {
            if (coffeeList.get(i).getId() == id) {
                coffeeList.set(i, updatedCoffee);
                saveCoffeeData();
                return;
            }
        }
    }


    public void addCoffee(Coffee coffee) {
        coffee.setId(coffeeList.size() + 1);  // Assign next sequential ID

        System.out.println("Saving coffee: " + coffee.getName() + " with image: " + coffee.getCoffeePicture());

        coffeeList.add(coffee);
        saveCoffeeData();
    }

    public Coffee getCoffeeById(int id) {
        for (Coffee coffee : coffeeList) {
            if (coffee.getId() == id) {
                return coffee;
            }
        }
        return null;
    }

    public void deleteCoffeeById(int id) {
        coffeeList.removeIf(coffee -> coffee.getId() == id);

        // Reassign IDs starting from 1 to maintain sequence without gaps
        for (int i = 0; i < coffeeList.size(); i++) {
            coffeeList.get(i).setId(i + 1);
        }

        saveCoffeeData();
    }

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


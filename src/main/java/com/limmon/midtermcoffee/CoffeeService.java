package com.limmon.midtermcoffee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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
        coffeeList.add(new Coffee(1, "Espresso", "Arabica", "Small", 3.50, "Dark", "Ethiopia", false, 10, "Chocolate, Nutty", "Espresso"));
        coffeeList.add(new Coffee(2, "Latte", "Arabica", "Medium", 4.50, "Medium", "Brazil", false, 8, "Creamy, Sweet", "Drip"));
        coffeeList.add(new Coffee(3, "Cappuccino", "Robusta", "Large", 5.00, "Medium", "Colombia", false, 12, "Fruity, Bold", "French Press"));
        coffeeList.add(new Coffee(4, "Mocha", "Arabica", "Medium", 4.75, "Dark", "Guatemala", false, 6, "Chocolate, Smooth", "Espresso"));
        coffeeList.add(new Coffee(5, "Americano", "Robusta", "Large", 3.25, "Light", "Kenya", false, 15, "Citrus, Balanced", "Drip"));
    }


    public void loadCoffeeData() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Use regex to correctly split data even if some values are quoted
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                String type = data[2];
                String size = data[3];
                double price = Double.parseDouble(data[4]);
                String roastLevel = data[5];
                String origin = data[6];
                boolean isDecaf = Boolean.parseBoolean(data[7]);
                int stock = Integer.parseInt(data[8]);
                String flavorNotes = data[9].replace("\"", "");
                String brewMethod = data[10].replace("\"", "");

                coffeeList.add(new Coffee(id, name, type, size, price, roastLevel, origin, isDecaf, stock, flavorNotes, brewMethod));
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
                        "\"" + coffee.getFlavorNotes().replace("\"", "") + "\"" + "," +
                        coffee.getBrewMethod());
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
        saveCoffeeData();
    }

    public List<Coffee> searchCoffeesByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return coffeeList;
        }
        List<Coffee> result = new ArrayList<>();
        for (Coffee coffee : coffeeList) {
            if (coffee.getName().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(coffee);
            }
        }
        return result;
    }
}


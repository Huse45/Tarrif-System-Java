package Part1_TariffAdjustmentSystem;
// Assignment 3
// Question: Tariff Adjustment System for Part 1
// Written by: Huseyin Pilavci, 40312242
/*This program reads trade data from a file, applies country-based tariff adjustments,
 sorts the updated products by name, and writes the result to a new file.
 It uses ArrayLists and File I/O to simulate basic economic tariff impacts.*/


import java.io.*;
import java.util.*;

public class Main {

    // Product class
    static class Product {
        String name;
        String country;
        String category;
        double price;

        public Product(String name, String country, String category, double price) {
            this.name = name;
            this.country = country;
            this.category = category;
            this.price = price;
        }

        @Override
        public String toString() {
            return name + "," + country + "," + category + "," + String.format("%.2f", price);
        }
    }

    // Tariff logic
    public static void applyTariff(Product p) {
        switch (p.country) {
            case "China":
                p.price *= 1.25;
                break;
            case "USA":
                if (p.category.equalsIgnoreCase("Electronics"))
                    p.price *= 1.10;
                break;
            case "Japan":
                if (p.category.equalsIgnoreCase("Automobiles"))
                    p.price *= 1.15;
                break;
            case "India":
                if (p.category.equalsIgnoreCase("Agriculture"))
                    p.price *= 1.05;
                break;
            case "South Korea":
                if (p.category.equalsIgnoreCase("Electronics"))
                    p.price *= 1.08;
                break;
            case "Saudi Arabia":
                if (p.category.equalsIgnoreCase("Energy"))
                    p.price *= 1.12;
                break;
            case "Germany":
                if (p.category.equalsIgnoreCase("Manufacturing"))
                    p.price *= 1.06;
                break;
            case "Bangladesh":
                if (p.category.equalsIgnoreCase("Textile"))
                    p.price *= 1.04;
                break;
            case "Brazil":
                if (p.category.equalsIgnoreCase("Agriculture"))
                    p.price *= 1.09;
                break;
        }
    }

    public static void main(String[] args) {
       final String INPUT_FILE = "src/Part1_TariffAdjustmentSystem/TradeData.txt";
       final String OUTPUT_FILE="src/Part1_TariffAdjustmentSystem/UpdatedTradeData.txt";
        System.out.println("Welcome to the Tariff Management System!");

        ArrayList<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(",");
                if (parts.length != 4) continue;

                String name = parts[0].trim();
                String country = parts[1].trim();
                String category = parts[2].trim();
                double price = Double.parseDouble(parts[3].trim());

                Product p = new Product(name, country, category, price);
                applyTariff(p);
                products.add(p);
            }

        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
            return;
        }

        // Sort alphabetically by product name
        products.sort(Comparator.comparing(p -> p.name));

        // Write to UpdatedTradeData.txt
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            for (Product p : products) {
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to output file: " + e.getMessage());
        }

        System.out.println("Updated trade data written to UpdatedTradeData.txt.");
        System.out.println("Program complete. Goodbye!");
    }
}

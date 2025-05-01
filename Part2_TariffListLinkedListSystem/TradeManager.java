package Part2_TariffListLinkedListSystem;

// Assignment 3
// Question: TradeManager Driver Class for Part 2
// Written by: Huseyin Pilavci, 40312242

/*
 * This program models international trade disputes using a custom linked list (TariffList).
 * It loads tariffs and trade requests from files, evaluates trade outcomes (accepted, conditional, rejected),
 * and demonstrates linked list operations like insert, delete, find, and replace.
 */

import java.io.*;
import java.util.*;

/**
 * The TradeManager class is the main driver for Part 2 of the assignment.
 * It reads tariff rules from a file and loads them into a custom linked list (TariffList).
 * It then reads trade requests and evaluates them against the tariff policies,
 * outputting whether the trade is accepted, conditionally accepted (with surcharge), or rejected.
 * It also includes test cases to verify the functionality of the TariffList methods.
 */
public class TradeManager {

    /**
     * The entry point of the program.
     * Loads data from input files, evaluates trade requests, prompts for user interaction,
     * and demonstrates method functionality of the TariffList class.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        TariffList tariffs = new TariffList();
        TariffList copiedTariffs = new TariffList();

        final String INPUT_FILE1 = "src/Part2_TariffListLinkedListSystem/Tariffs.txt";
        final String INPUT_FILE2 = "src/Part2_TariffListLinkedListSystem/TradeRequests.txt";

        System.out.println("Welcome to the Trade Conflict Manager");
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(OutputStream.nullOutputStream())); // Silence "not found" output

        // Load tariffs from Tariffs.txt
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE1))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length != 4) continue;

                String dest = parts[0];
                String orig = parts[1];
                String cat = parts[2];
                double min = Double.parseDouble(parts[3]);

                if (!tariffs.contains(orig, dest, cat)) {
                    tariffs.addToStart(new Tariff(dest, orig, cat, min));
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read Tariffs.txt: " + e.getMessage());
            return;
        }
        System.setOut(originalOut); // Restore output

        // Copy constructor test
        copiedTariffs = new TariffList(tariffs);

        // Load trade requests from TradeRequests.txt
        ArrayList<String[]> requests = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE2))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(" ");
                if (parts.length != 6) continue;
                requests.add(parts);
            }
        } catch (IOException e) {
            System.err.println("Failed to read TradeRequests.txt: " + e.getMessage());
            return;
        }

        // Evaluate each trade request
        System.out.println("\n--- TRADE REQUEST RESULTS ---");
        for (String[] req : requests) {
            String id = req[0];
            String orig = req[1];
            String dest = req[2];
            String cat = req[3];
            double value = Double.parseDouble(req[4]);
            double proposed = Double.parseDouble(req[5]);

            Tariff t = null;
            TariffList.TariffNode node = tariffs.find(orig, dest, cat);

            if (node != null) {
                t = node.getData();
                double minTariff = t.getMinimumTariff();

                System.out.print(id + " - ");
                if (proposed >= minTariff) {
                    System.out.println("Accepted.");
                    System.out.println("Proposed tariff meets or exceeds the minimum requirement.");
                } else if (proposed >= minTariff * 0.8) {
                    double surcharge = value * ((minTariff - proposed) / 100.0);
                    System.out.println("Conditionally Accepted.");
                    System.out.printf("Proposed tariff %.0f%% is within 20%% of the required minimum tariff %.0f%%.%n", proposed, minTariff);
                    System.out.printf("A surcharge of $%.0f is applied.%n", surcharge);
                } else {
                    System.out.println("Rejected");
                    System.out.printf("Proposed tariff %.0f%% is more than 20%% below the required minimum tariff %.0f%%.%n", proposed, minTariff);
                }

            }
        }

        // User interactive search
        System.out.println("\n--- SEARCH MODE ---");
        while (true) {
            System.out.print("Search a tariff?(y) or Testing methods(t): ");
            if (!input.nextLine().equalsIgnoreCase("y")) break;

            System.out.print("Origin Country: ");
            String orig = input.nextLine();
            System.out.print("Destination Country: ");
            String dest = input.nextLine();
            System.out.print("Product Category: ");
            String cat = input.nextLine();
            TariffList.TariffNode x=tariffs.find(orig, dest, cat);
            System.out.println(x.getData());



        }

        // Test linked list functionality
        System.out.println("\n--- TESTING METHODS ---");
        Tariff testTariff = new Tariff("UAE", "Norway", "Oil", 18.0);
        tariffs.addToStart(testTariff);
        tariffs.replaceAtIndex(new Tariff("Mexico", "Chile", "Avocados", 11.0), 0);
        System.out.println("\nAfter replacement at index 0:");
        tariffs.printList();
        tariffs.deleteFromIndex(0);
        System.out.println("\nAfter deletion from index 0:");
        tariffs.printList();
        tariffs.deleteFromStart();
        System.out.println("\nAfter deleting from start:");
        tariffs.printList();

        System.out.println("\nProgram completed. Goodbye!");
    }
}

package com.iticbcn;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();

            properties.load(input);

            Connection conn = Utility.databaseConnection(properties);

            if (conn == null) return;

            
            
        }
        
        System.out.println("Welcome to GoldenFord.");
        while (true) {
            System.out.println("""
                What would you like to do? 
                1. Load DB script
                2. Insert on table
                3. Show table rows
                4. Create XML file
                5. Show row by Id
                6. Show table by
                7. Modify
                8. Delete row by Id
                """);
            String option = Utility.input().toLowerCase();
            switch (option) {
                case "1":
                    makeOrder();
                    break;
                case "2":
                    manageOrder();
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Error: Option " + option + " not recognized");
            }
        }
    }
}
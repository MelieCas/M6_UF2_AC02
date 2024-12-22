package com.iticbcn;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import org.apache.logging.log4j.util.PropertySource.Util;

public class Main {
    private static Connection conn;
    private static CRUDManager manager;
    public static void main(String[] args) {

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {

            Properties properties = new Properties();

            properties.load(input);

            conn = Utility.databaseConnection(properties);

            if (conn == null) return;

            loadData();

            manager = new CRUDManager("empleats");

        } catch (Exception e) {
            System.out.println(e);
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
                6. Show Workers by Name
                7. Modify
                8. Delete row by Id
                """);
            String option = Utility.input().toLowerCase();
            switch (option) {
                case "1":
                    loadData();
                    break;
                case "2":
                    insertWorker();
                    break;
                case "3":
                    listTableRows();
                    break;
                case "5":
                    searchRowById();
                    break;
                case "6":
                    searchWorkersByName();
                    break;
                case "8":
                    deleteRowById();
                    break;
                default:
                    System.out.println("Error: Option " + option + " not recognized");
            }
        }
    }

    public static void loadData() {
        try {
            String scriptDatabase = "databaseScripts/LoadDBScript.sql";

            InputStream streamScript = Main.class.getClassLoader().getResourceAsStream(scriptDatabase);
            
            CRUDManager.loadDatabaseScript(conn, streamScript);

            System.out.println("Database loaded");
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void insertWorker() {
        while (true) {
            System.out.println("Insert worker ID: ");
            String workerIdString = Utility.input();
            int workerId = 0;
            try {

                workerId = Integer.parseInt(workerIdString);

                if (workerId <= 0) {
                    System.out.println("Id can't be negative");
                    continue;
                }
                
            } catch (Exception e) {
                System.out.println("Id has to be a number");
                continue;
            }

            System.out.println("Insert worker name: ");
            String workerName = Utility.input();

            System.out.println("Insert worker first surname: ");
            String workerSurname = Utility.input();

            System.out.println("Insert worker second surname: ");
            String workerSurname2 = Utility.input();

            System.out.println("Insert worker Identity number (DNI): ");
            String workerDNI = Utility.input();
            while (true) {
                if (!workerDNI.matches("^\\d{8}[A-Z]$")) {
                    System.out.println("Format not supported.");
                    workerDNI = Utility.input();
                } else {
                    break;
                }
            }

            System.out.println("Insert worker phone number: ");
            String workerPhone = Utility.input();
            while (true) {
                if (!workerPhone.matches("^\\d{9}")) {
                    System.out.println("Format not supported.");
                    workerPhone = Utility.input();
                } else {
                    break;
                }
            }
            
            System.out.println("Insert worker salary: ");
            double workerSalaryDouble = 0;
            while (true) {
                String workerSalaryString = Utility.input();
                try {
                    

                    workerSalaryDouble = Integer.parseInt(workerSalaryString);
    
                    if (workerSalaryDouble <= 0) {
                        System.out.println("Salary can't be negative");
                        continue;
                    }
                    break;
                    
                } catch (Exception e) {
                    System.out.println("Salary has to be a number");
                    continue;
                }
            }

            Worker worker = new Worker(workerId, workerName, workerSurname, workerSurname2, workerDNI, workerPhone, workerSalaryDouble, 1, 1);

            try {
                manager.insertWorker(worker, conn);
            } catch (Exception e) {
                System.out.println(e);
            }

            System.out.println("Add another worker? (y/N): ");
            String continueInsert = Utility.input();
            if (!Utility.confirmAnswer(continueInsert)) break;
            
        }
    }

    public static void listTableRows() {
        int offset = 0;

        while (true) {
            boolean setAvailable = manager.readTableRows(conn, offset);

            if (setAvailable) {
                System.out.println("Read another set? (y/N): ");
                String readSet = Utility.input();

                if (!Utility.confirmAnswer(readSet)) break;
                offset += 10;

            } else {
                System.out.println("All rows read.");
                break;
            }
        }

    }

    public static void searchRowById() {
        while (true) {
            System.out.println("Write the ID of the worker you want to find: ");
            String workerIdString = Utility.input();
            int workerId = 0;
            try {

                workerId = Integer.parseInt(workerIdString);

                if (workerId <= 0) {
                    System.out.println("Id can't be negative");
                    continue;
                }
                
            } catch (Exception e) {
                System.out.println("Id has to be a number");
                continue;
            }

            manager.searchRowById(conn, workerId);

            System.out.println("Read another row? (y/N): ");
            String readRow = Utility.input();

            if (!Utility.confirmAnswer(readRow)) break;

        }
    }

    public static void searchWorkersByName() {
        while (true) {
            System.out.println("Write the Name of the worker you want to find: ");
            String workerName = Utility.input();

            manager.searchWorkersByName(conn, workerName);

            System.out.println("Search another name? (y/N): ");
            String searchName = Utility.input();

            if (!Utility.confirmAnswer(searchName)) break;
        }

    }

    public static void deleteRowById() {
        while (true) {
            System.out.println("Write the ID of the worker you want to delete: ");
            String workerIdString = Utility.input();
            int workerId = 0;
            try {

                workerId = Integer.parseInt(workerIdString);

                if (workerId <= 0) {
                    System.out.println("Id can't be negative");
                    continue;
                }
                
            } catch (Exception e) {
                System.out.println("Id has to be a number");
                continue;
            }

            manager.deleteRowById(conn, workerId);

            System.out.println("delete another row? (y/N): ");
            String readRow = Utility.input();

            if (!Utility.confirmAnswer(readRow)) break;

        }

    }
}
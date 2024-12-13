package com.iticbcn;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class Utility {
    private final static Pattern phonePattern = Pattern.compile("^\\d{9}$");
    private final static Pattern datePattern = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$");
    //Checks if user answer is positive or negative.
    public static boolean confirmAnswer(String answer) {
        if (null == answer) {  
            return false;
        }
        answer = answer.toLowerCase();
        if (answer.equals("y")) {
            return true;
        }
        return false;
    }

    public static String input() {
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
        try {
            String input = reader1.readLine();
            return input;



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection databaseConnection(Properties properties) {
        String dbUrl = properties.getProperty("db.url");
        String dbName = properties.getProperty("db.username");
        String dbPasswd = properties.getProperty("db.password");

        try {
            Connection conn = DriverManager.getConnection(dbUrl, dbName, dbPasswd);

            System.out.println("Connexion to database succesful.");

            return conn;
        } catch (SQLException e) {
            System.out.println("Connexion to database failed.");
            return null;
        }
    }
}
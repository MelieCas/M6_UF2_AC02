package com.iticbcn;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUDManager {

    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public CRUDManager(String tableName) {
        setTableName(tableName);
    }

    public static void loadDatabaseScript(Connection conn) {
        String scriptFile = "databaseScripts/LoadDBScript.sql";

        String readScript = "source " + scriptFile;

        try (Statement statement = conn.createStatement()) {
            
        }
    }
}
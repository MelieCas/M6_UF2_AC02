package com.iticbcn;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

    public static void loadDatabaseScript(Connection conn, InputStream script) {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(script))) {
            StringBuilder sqlStatement = new StringBuilder();
            String line;

            try (Statement statement = conn.createStatement()) {
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                        
                    if (line.isEmpty() || line.startsWith("--") || line.startsWith("//") || line.startsWith("/*")) {
                            continue;
                    }

                    sqlStatement.append(line);

                    if (line.endsWith(";")) {

                        String sql = sqlStatement.toString().replace(";", "").trim();
                        statement.execute(sql);

                        
                        sqlStatement.setLength(0);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void insertWorker(Worker worker, Connection conn) throws SQLException {
        String query = "INSERT INTO " + tableName 
                    + " (id, nom, primer_cognom, segon_cognom, dni, telefon, sou, departament_id, equip_id)"
                    + " VALUES (?,?,?,?,?,?,?,?,?)";
                    

        try (PreparedStatement prepstat = conn.prepareStatement(query)) {

            boolean autocommitvalue = conn.getAutoCommit();


            conn.setAutoCommit(false);

            prepstat.setInt(1, worker.getId());
            prepstat.setString(2, worker.getName());
            prepstat.setString(3, worker.getSurname1());
            prepstat.setString(4, worker.getSurname2());
            prepstat.setString(5, worker.getDni());
            prepstat.setString(6, worker.getPhone());
            prepstat.setDouble(7, worker.getSalary());
            prepstat.setInt(8, worker.getDepartmentId());
            prepstat.setInt(9, worker.getTeamId());

            prepstat.executeUpdate();

            conn.commit();

            System.out.println("Worker added to database");

            conn.setAutoCommit(autocommitvalue);
        
        } catch (SQLException sqle) {
            if (!sqle.getMessage().contains("Duplicate entry")) {
                System.err.println(sqle.getMessage());
            } else {
                System.out.println("Worker already exists");
            }

            conn.rollback();
        }
    }

    public boolean readTableRows(Connection conn, int offset) {

        String query = "select * from " + tableName + " order by id limit 10 offset " + offset;

        try (Statement statement = conn.createStatement()) {

            ResultSet rSet = statement.executeQuery(query);

            int colNumber = 0;

            if (rSet != null) {
                ResultSetMetaData rsMetaData = rSet.getMetaData();
                colNumber = rsMetaData.getColumnCount();
            }

            if (colNumber > 0) {
                while(rSet.next()) {
                    for(int i =0; i<colNumber; i++) {
                        if(i+1 == colNumber) {
                            System.out.println(rSet.getString(i+1));
                        } else {
                    
                        System.out.print(rSet.getString(i+1)+ ", ");
                        }
                    } 
                }
                return true;
            }
            return false;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    public void searchRowById(Connection conn, int id) {

        String query = "SELECT * FROM " + tableName + " WHERE id = ?";

        try (PreparedStatement prepstat = conn.prepareStatement(query)) {

            prepstat.setInt(1, id);
            ResultSet rSet = prepstat.executeQuery();

            int colNumber = 0;

            if (rSet != null) {
                ResultSetMetaData rsMetaData = rSet.getMetaData();
                colNumber = rsMetaData.getColumnCount();
            }

            if (colNumber > 0) {
                while(rSet.next()) {
                    for(int i =0; i<colNumber; i++) {
                        if(i+1 == colNumber) {
                            System.out.println(rSet.getString(i+1));
                        } else {
                    
                        System.out.print(rSet.getString(i+1)+ ", ");
                        }
                    } 
                }
            } else {
                System.out.println("Worker not found.");
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
    }

    public void searchWorkersByName(Connection conn, String name) {

        String query = "SELECT * FROM " + tableName + " WHERE name like ?";

        try (PreparedStatement prepstat = conn.prepareStatement(query)) {

            prepstat.setString(1, name);
            ResultSet rSet = prepstat.executeQuery();
            int colNumber = 0;


            if (rSet != null) {
                ResultSetMetaData rsMetaData = rSet.getMetaData();
                colNumber = rsMetaData.getColumnCount();
            }

            if (colNumber > 0) {
                while(rSet.next()) {
                    for(int i =0; i<colNumber; i++) {
                        if(i+1 == colNumber) {
                            System.out.println(rSet.getString(i+1));
                        } else {
                    
                        System.out.print(rSet.getString(i+1)+ ", ");
                        }
                    } 
                }
            } else {
                System.out.println("Worker not found.");
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }

    }

    public void deleteRowById(Connection conn, int id) {
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        try (PreparedStatement prepstat = conn.prepareStatement(query)) {

            prepstat.setInt(1, id);
            ResultSet rSet = prepstat.executeQuery();
            int colNumber = 0;


            if (rSet != null) {
                ResultSetMetaData rsMetaData = rSet.getMetaData();
                colNumber = rsMetaData.getColumnCount();
            }

            if (colNumber > 0) {
                while(rSet.next()) {
                    for(int i =0; i<colNumber; i++) {
                        if(i+1 == colNumber) {
                            System.out.println(rSet.getString(i+1));
                        } else {
                    
                            System.out.print(rSet.getString(i+1)+ ", ");
                        }
                    } 
                }
            } else {
                System.out.println("Worker not found.");
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }

    }
}
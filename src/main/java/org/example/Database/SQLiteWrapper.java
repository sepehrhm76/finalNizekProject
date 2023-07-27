package org.example.Database;

import org.example.Log.Logger;

import java.sql.*;

public class SQLiteWrapper {

    private static SQLiteWrapper instance = null;

    private Connection connection;

    // Constructor to initialize the database connection
    private SQLiteWrapper() {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            // Connect to the SQLite database
            connection = DriverManager.getConnection("jdbc:sqlite:" + DatabaseConstant.DATABASE_FILE_NAME);
            Logger.getInstance().logDebug("Connected to the SQLite database.");
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getInstance().logError("Error connecting to the database: " + e.getMessage());
        }
    }

    public static SQLiteWrapper getInstance() {
        if (instance == null)
            instance = new SQLiteWrapper();
        return instance;
    }

    // Check if a table exists in the database
    private boolean tableExists(String tableName) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, null);
            return resultSet.next();
        } catch (SQLException e) {
            Logger.getInstance().logError("Error checking table existence: " + e.getMessage());
            return false;
        }
    }

    // Create a table if it doesn't exist
    public void createTableIfNotExists(String tableName, String schema) {
        try {
            Statement statement = connection.createStatement();
            boolean isAlreadyCreated = tableExists(tableName);
            statement.executeUpdate(String.format("CREATE TABLE IF NOT EXISTS %S (%S);", tableName, schema));
            if (!isAlreadyCreated) Logger.getInstance().logDebug("Table '" + tableName + "' created.");
        } catch (SQLException e) {
            Logger.getInstance().logError("Error creating table: " + e.getMessage());
        }
    }

    // Execute an SQL query that returns a ResultSet
    public ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            Logger.getInstance().logError("Error executing query: " + e.getMessage());
        }
        return resultSet;
    }

    // Execute an SQL update (e.g., INSERT, UPDATE, DELETE)
    public int executeUpdate(String query) {
        int rowsAffected = 0;
        try {
            Statement statement = connection.createStatement();
            rowsAffected = statement.executeUpdate(query);
        } catch (SQLException e) {
            Logger.getInstance().logError("Error executing update: " + e.getMessage());
        }
        return rowsAffected;
    }

    // Close the database connection
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
                Logger.getInstance().logDebug("Connection to the SQLite database closed.");
            }
        } catch (SQLException e) {
            Logger.getInstance().logError("Error closing the connection: " + e.getMessage());
        }
    }
}

package org.example.Database;

import org.example.Log.Logger;
import java.sql.*;

public class SQLiteWrapper {

    private static SQLiteWrapper instance = null;


    private Connection connection;

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

    public ResultSet executeQuery(String query, Object... params) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameters for the prepared statement
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            Logger.getInstance().logError("Error executing query: " + e.getMessage());
        }
        return resultSet;
    }

    public int executeUpdate(String query, Object... params) {
        int rowsAffected = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameters for the prepared statement
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getInstance().logError("Error executing update: " + e.getMessage());
        }
        return rowsAffected;
    }

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

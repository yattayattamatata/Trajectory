package com.trajectory.app.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * repository classes should use this class instead of
 * opening their own database connections manually.
 */
public final class DatabaseManager {

    private static final String DATABASE_URL =
            "jdbc:sqlite:trajectory.db";

    /**
     * Private constructor prevents creation of DatabaseManager objects
     */
    private DatabaseManager() {
    }

    /**
     * Opens and returns a connection to the SQLite database.
     *
     * @return open SQLite database connection
     * @throws SQLException if connection cannot be opened
     */
    public static Connection getConnection() throws SQLException {

        Connection connection =
                DriverManager.getConnection(DATABASE_URL);

        // SQLite foreign key enforcement must be enabled
        // for each database connection.
        try (Statement statement = connection.createStatement()) {
            statement.execute("PRAGMA foreign_keys = ON");
        }

        return connection;
    }
}
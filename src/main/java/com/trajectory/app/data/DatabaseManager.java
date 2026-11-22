package com.trajectory.app.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Handles connections to the Trajectory SQLite database.
 */
public final class DatabaseManager {

    /*
     * SQLite will create trajectory.db automatically
     * if the database file does not already exist.
     */
    private static final String DATABASE_URL =
            "jdbc:sqlite:trajectory.db";

    /**
     * Private constructor prevents DatabaseManager
     * from being instantiated.
     */
    private DatabaseManager() {
    }

    /**
     * Opens a connection to the SQLite database.
     *
     * @return an open database connection
     * @throws SQLException if the connection fails
     */
    public static Connection getConnection()
            throws SQLException {

        Connection connection =
                DriverManager.getConnection(DATABASE_URL);

        /*
         * SQLite does not enforce foreign keys by default.
         * This turns them on for this connection.
         */
        try (Statement statement =
                     connection.createStatement()) {

            statement.execute(
                    "PRAGMA foreign_keys = ON"
            );
        }

        return connection;
    }
}
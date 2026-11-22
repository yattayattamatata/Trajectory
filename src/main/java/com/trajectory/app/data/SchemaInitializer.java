package com.trajectory.app.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Creates all database tables required by Trajectory.
 */
public final class SchemaInitializer {

    private SchemaInitializer() {
    }

    /**
     * Creates the database schema.
     *
     * CREATE TABLE IF NOT EXISTS means this can safely
     * run every time Trajectory starts.
     */
    public static void initialize() {

        String usersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL
                );
                """;

        String debtsTable = """
                CREATE TABLE IF NOT EXISTS debts (
                    debt_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    label TEXT NOT NULL,
                    balance REAL NOT NULL,
                    apr_decimal REAL NOT NULL,
                    minimum_payment REAL NOT NULL,

                    FOREIGN KEY (user_id)
                    REFERENCES users(user_id)
                    ON DELETE CASCADE
                );
                """;

        String investmentsTable = """
                CREATE TABLE IF NOT EXISTS investments (
                    investment_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    label TEXT NOT NULL,
                    balance REAL NOT NULL,
                    apy_decimal REAL NOT NULL,
                    monthly_contribution REAL NOT NULL,

                    FOREIGN KEY (user_id)
                    REFERENCES users(user_id)
                    ON DELETE CASCADE
                );
                """;

        String budgetsTable = """
                CREATE TABLE IF NOT EXISTS budgets (
                    budget_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    month INTEGER NOT NULL,
                    year INTEGER NOT NULL,

                    FOREIGN KEY (user_id)
                    REFERENCES users(user_id)
                    ON DELETE CASCADE
                );
                """;

        String budgetEntriesTable = """
                CREATE TABLE IF NOT EXISTS budget_entries (
                    entry_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    budget_id INTEGER NOT NULL,
                    custom_description TEXT,
                    category TEXT NOT NULL,
                    amount REAL NOT NULL,

                    FOREIGN KEY (budget_id)
                    REFERENCES budgets(budget_id)
                    ON DELETE CASCADE
                );
                """;

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                Statement statement =
                        connection.createStatement()
        ) {

            statement.executeUpdate(usersTable);
            statement.executeUpdate(debtsTable);
            statement.executeUpdate(investmentsTable);
            statement.executeUpdate(budgetsTable);
            statement.executeUpdate(budgetEntriesTable);

            System.out.println(
                    "Trajectory database initialized."
            );

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Could not initialize database.",
                    e
            );
        }
    }
}
package com.trajectory.app.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SchemaInitializer creates the database tables needed
 * by Trajectory
 */
public final class SchemaInitializer {

    /**
     * Private constructor prevents objects of this utility class.
     */
    private SchemaInitializer() {
    }

    /**
     * Creates all Trajectory database tables if they do not
     * already exist.
     */
    public static void initialize() {

        /*
         * One row represents one User object.
         */
        String createUsers = """
                CREATE TABLE IF NOT EXISTS users (
                    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL
                )
                """;

        /*
         * Each debt belongs to exactly one user.
         */
        String createDebts = """
                CREATE TABLE IF NOT EXISTS debts (
                    debt_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    label TEXT NOT NULL,
                    balance REAL NOT NULL CHECK(balance >= 0),
                    apr_decimal REAL NOT NULL CHECK(apr_decimal >= 0),
                    minimum_payment REAL NOT NULL CHECK(minimum_payment >= 0),

                    FOREIGN KEY (user_id)
                        REFERENCES users(user_id)
                        ON DELETE CASCADE
                )
                """;

        /*
         * Each investment belongs to exactly one user.
         */
        String createInvestments = """
                CREATE TABLE IF NOT EXISTS investments (
                    investment_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    label TEXT NOT NULL,
                    balance REAL NOT NULL CHECK(balance >= 0),
                    apy_decimal REAL NOT NULL CHECK(apy_decimal >= 0),
                    monthly_contribution REAL NOT NULL
                        CHECK(monthly_contribution >= 0),

                    FOREIGN KEY (user_id)
                        REFERENCES users(user_id)
                        ON DELETE CASCADE
                )
                """;

        /*
         * Each budget belongs to exactly one user.
         */
        String createBudgets = """
                CREATE TABLE IF NOT EXISTS budgets (
                    budget_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    user_id INTEGER NOT NULL,
                    name TEXT NOT NULL,
                    month INTEGER NOT NULL CHECK(month BETWEEN 1 AND 12),
                    year INTEGER NOT NULL,

                    FOREIGN KEY (user_id)
                        REFERENCES users(user_id)
                        ON DELETE CASCADE
                )
                """;

        /*
         * BudgetEntry objects belong to a Budget.
         *
         * category stores the name of the BudgetCategory enum,
         * such as SALARY, GROCERIES, or OTHER_EXPENSE.
         */
        String createBudgetEntries = """
                CREATE TABLE IF NOT EXISTS budget_entries (
                    entry_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    budget_id INTEGER NOT NULL,
                    custom_description TEXT,
                    category TEXT NOT NULL,
                    amount REAL NOT NULL CHECK(amount >= 0),

                    FOREIGN KEY (budget_id)
                        REFERENCES budgets(budget_id)
                        ON DELETE CASCADE
                )
                """;

        try (Connection connection =
                     DatabaseManager.getConnection();

             Statement statement =
                     connection.createStatement()) {

            // Parent tables are created before their child tables.
            statement.executeUpdate(createUsers);
            statement.executeUpdate(createDebts);
            statement.executeUpdate(createInvestments);
            statement.executeUpdate(createBudgets);
            statement.executeUpdate(createBudgetEntries);

            System.out.println(
                    "Trajectory database initialized successfully."
            );

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to initialize Trajectory database.",
                    e
            );
        }
    }
}
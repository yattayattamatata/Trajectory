package com.trajectory.app.data;

import com.trajectory.app.model.Budget;
import com.trajectory.app.model.BudgetCategory;
import com.trajectory.app.model.BudgetEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles persistence of Budget objects and
 * their BudgetEntry objects.
 */
public class BudgetRepository {

    /**
     * Saves one Budget and all entries belonging to it.
     */
    public void save(
            int userID,
            Budget budget
    ) {

        String budgetSQL = """
                INSERT INTO budgets (
                    user_id,
                    name,
                    month,
                    year
                )
                VALUES (?, ?, ?, ?)
                """;

        Connection connection = null;

        try {

            connection =
                    DatabaseManager.getConnection();

            /*
             * Everything should save together.
             */
            connection.setAutoCommit(false);

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    budgetSQL,
                                    Statement.RETURN_GENERATED_KEYS
                            )
            ) {

                statement.setInt(
                        1,
                        userID
                );

                statement.setString(
                        2,
                        budget.getName()
                );

                statement.setInt(
                        3,
                        budget.getMonth()
                );

                statement.setInt(
                        4,
                        budget.getYear()
                );

                statement.executeUpdate();

                try (ResultSet keys =
                             statement.getGeneratedKeys()) {

                    if (keys.next()) {

                        budget.setDatabaseID(
                                keys.getInt(1)
                        );
                    }
                }
            }

            /*
             * Save all of this budget's entries.
             */
            for (BudgetEntry entry :
                    budget.getEntries()) {

                saveEntry(
                        connection,
                        budget.getDatabaseID(),
                        entry
                );
            }

            connection.commit();

        } catch (SQLException e) {

            if (connection != null) {

                try {
                    connection.rollback();
                } catch (SQLException ignored) {
                }
            }

            throw new RuntimeException(
                    "Unable to save budget.",
                    e
            );

        } finally {

            if (connection != null) {

                try {
                    connection.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    /**
     * Saves one BudgetEntry.
     */
    private void saveEntry(
            Connection connection,
            int budgetID,
            BudgetEntry entry
    ) throws SQLException {

        String sql = """
                INSERT INTO budget_entries (
                    budget_id,
                    custom_description,
                    category,
                    amount
                )
                VALUES (?, ?, ?, ?)
                """;

        try (
                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setInt(
                    1,
                    budgetID
            );

            /*
             * getDescription() returns the user's
             * custom description for OTHER categories.
             */
            if (
                    entry.getBudgetCategory()
                            == BudgetCategory.OTHER_EXPENSE
                            ||
                            entry.getBudgetCategory()
                                    == BudgetCategory.OTHER_INCOME
            ) {

                statement.setString(
                        2,
                        entry.getDescription()
                );

            } else {

                statement.setNull(
                        2,
                        Types.VARCHAR
                );
            }

            /*
             * Save enum name:
             * GROCERIES
             * SALARY
             * OTHER_EXPENSE
             */
            statement.setString(
                    3,
                    entry.getBudgetCategory().name()
            );

            statement.setDouble(
                    4,
                    entry.getAmount()
            );

            statement.executeUpdate();

            try (ResultSet keys =
                         statement.getGeneratedKeys()) {

                if (keys.next()) {

                    entry.setDatabaseID(
                            keys.getInt(1)
                    );
                }
            }
        }
    }

    /**
     * Loads all budgets belonging to a User.
     */
    public List<Budget> findByUserID(
            int userID
    ) {

        String sql = """
                SELECT
                    budget_id,
                    name,
                    month,
                    year
                FROM budgets
                WHERE user_id = ?
                ORDER BY year, month
                """;

        ArrayList<Budget> budgets =
                new ArrayList<>();

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    userID
            );

            try (ResultSet results =
                         statement.executeQuery()) {

                while (results.next()) {

                    Budget budget =
                            new Budget(
                                    results.getString("name"),
                                    results.getInt("month"),
                                    results.getInt("year")
                            );

                    budget.setDatabaseID(
                            results.getInt(
                                    "budget_id"
                            )
                    );

                    loadEntries(
                            connection,
                            budget
                    );

                    budgets.add(budget);
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to load budgets.",
                    e
            );
        }

        return budgets;
    }

    /**
     * Restores BudgetEntry objects belonging
     * to a Budget.
     */
    private void loadEntries(
            Connection connection,
            Budget budget
    ) throws SQLException {

        String sql = """
                SELECT
                    entry_id,
                    custom_description,
                    category,
                    amount
                FROM budget_entries
                WHERE budget_id = ?
                ORDER BY entry_id
                """;

        try (
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    budget.getDatabaseID()
            );

            try (ResultSet results =
                         statement.executeQuery()) {

                while (results.next()) {

                    BudgetCategory category =
                            BudgetCategory.valueOf(
                                    results.getString(
                                            "category"
                                    )
                            );

                    BudgetEntry entry =
                            new BudgetEntry(
                                    results.getString(
                                            "custom_description"
                                    ),
                                    category,
                                    results.getDouble(
                                            "amount"
                                    )
                            );

                    entry.setDatabaseID(
                            results.getInt(
                                    "entry_id"
                            )
                    );

                    budget.addEntry(entry);
                }
            }
        }
    }

    /**
     * Updates the Budget and replaces its stored
     * entries with the Budget's current entries.
     */
    public void update(
            Budget budget
    ) {

        String updateBudget = """
                UPDATE budgets
                SET
                    name = ?,
                    month = ?,
                    year = ?
                WHERE budget_id = ?
                """;

        String deleteEntries = """
                DELETE FROM budget_entries
                WHERE budget_id = ?
                """;

        Connection connection = null;

        try {

            connection =
                    DatabaseManager.getConnection();

            connection.setAutoCommit(false);

            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    updateBudget
                            )
            ) {

                statement.setString(
                        1,
                        budget.getName()
                );

                statement.setInt(
                        2,
                        budget.getMonth()
                );

                statement.setInt(
                        3,
                        budget.getYear()
                );

                statement.setInt(
                        4,
                        budget.getDatabaseID()
                );

                statement.executeUpdate();
            }

            /*
             * Delete old entries.
             */
            try (
                    PreparedStatement statement =
                            connection.prepareStatement(
                                    deleteEntries
                            )
            ) {

                statement.setInt(
                        1,
                        budget.getDatabaseID()
                );

                statement.executeUpdate();
            }

            /*
             * Save current entries.
             */
            for (BudgetEntry entry :
                    budget.getEntries()) {

                saveEntry(
                        connection,
                        budget.getDatabaseID(),
                        entry
                );
            }

            connection.commit();

        } catch (SQLException e) {

            if (connection != null) {

                try {
                    connection.rollback();
                } catch (SQLException ignored) {
                }
            }

            throw new RuntimeException(
                    "Unable to update budget.",
                    e
            );

        } finally {

            if (connection != null) {

                try {
                    connection.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    /**
     * Deletes a Budget.
     *
     * Its entries are deleted automatically because
     * budget_entries uses ON DELETE CASCADE.
     */
    public void delete(
            Budget budget
    ) {

        String sql = """
                DELETE FROM budgets
                WHERE budget_id = ?
                """;

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    budget.getDatabaseID()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to delete budget.",
                    e
            );
        }
    }
}
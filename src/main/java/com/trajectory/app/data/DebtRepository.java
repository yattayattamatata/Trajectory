package com.trajectory.app.data;

import com.trajectory.app.model.Debt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles database CRUD operations for Debt objects.
 */
public class DebtRepository {

    /**
     * Saves a new debt.
     */
    public void save(
            int userID,
            Debt debt
    ) {

        String sql = """
                INSERT INTO debts (
                    user_id,
                    label,
                    balance,
                    apr_decimal,
                    minimum_payment
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {

            statement.setInt(
                    1,
                    userID
            );

            statement.setString(
                    2,
                    debt.getLabel()
            );

            statement.setDouble(
                    3,
                    debt.getBalance()
            );

            statement.setDouble(
                    4,
                    debt.getAPRDecimal()
            );

            statement.setDouble(
                    5,
                    debt.getMinimumPayment()
            );

            statement.executeUpdate();

            try (ResultSet keys =
                         statement.getGeneratedKeys()) {

                if (keys.next()) {

                    debt.setDatabaseID(
                            keys.getInt(1)
                    );
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to save debt.",
                    e
            );
        }
    }

    /**
     * Loads every debt belonging to one user.
     */
    public List<Debt> findByUserID(
            int userID
    ) {

        String sql = """
                SELECT
                    debt_id,
                    label,
                    balance,
                    apr_decimal,
                    minimum_payment
                FROM debts
                WHERE user_id = ?
                ORDER BY debt_id
                """;

        ArrayList<Debt> debts =
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

                    Debt debt =
                            new Debt(
                                    results.getDouble(
                                            "balance"
                                    ),
                                    results.getDouble(
                                            "apr_decimal"
                                    ),
                                    results.getDouble(
                                            "minimum_payment"
                                    ),
                                    results.getString(
                                            "label"
                                    )
                            );

                    debt.setDatabaseID(
                            results.getInt(
                                    "debt_id"
                            )
                    );

                    debts.add(debt);
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to load debts.",
                    e
            );
        }

        return debts;
    }

    /**
     * Updates an existing debt.
     */
    public void update(Debt debt) {

        String sql = """
                UPDATE debts
                SET
                    label = ?,
                    balance = ?,
                    apr_decimal = ?,
                    minimum_payment = ?
                WHERE debt_id = ?
                """;

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    debt.getLabel()
            );

            statement.setDouble(
                    2,
                    debt.getBalance()
            );

            statement.setDouble(
                    3,
                    debt.getAPRDecimal()
            );

            statement.setDouble(
                    4,
                    debt.getMinimumPayment()
            );

            statement.setInt(
                    5,
                    debt.getDatabaseID()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to update debt.",
                    e
            );
        }
    }

    /**
     * Deletes one debt.
     */
    public void delete(Debt debt) {

        String sql = """
                DELETE FROM debts
                WHERE debt_id = ?
                """;

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    debt.getDatabaseID()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to delete debt.",
                    e
            );
        }
    }
}
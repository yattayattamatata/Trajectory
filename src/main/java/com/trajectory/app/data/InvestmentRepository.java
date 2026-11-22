package com.trajectory.app.data;

import com.trajectory.app.model.Investment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles SQLite operations for Investment objects.
 */
public class InvestmentRepository {

    public void save(
            int userID,
            Investment investment
    ) {

        String sql = """
                INSERT INTO investments (
                    user_id,
                    label,
                    balance,
                    apy_decimal,
                    monthly_contribution
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
                    investment.getLabel()
            );

            statement.setDouble(
                    3,
                    investment.getBalance()
            );

            statement.setDouble(
                    4,
                    investment.getAPYDecimal()
            );

            statement.setDouble(
                    5,
                    investment.getMonthlyContribution()
            );

            statement.executeUpdate();

            try (ResultSet keys =
                         statement.getGeneratedKeys()) {

                if (keys.next()) {

                    investment.setDatabaseID(
                            keys.getInt(1)
                    );
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to save investment.",
                    e
            );
        }
    }

    public List<Investment> findByUserID(
            int userID
    ) {

        String sql = """
                SELECT
                    investment_id,
                    label,
                    balance,
                    apy_decimal,
                    monthly_contribution
                FROM investments
                WHERE user_id = ?
                ORDER BY investment_id
                """;

        ArrayList<Investment> investments =
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

                    Investment investment =
                            new Investment(
                                    results.getDouble(
                                            "balance"
                                    ),
                                    results.getDouble(
                                            "apy_decimal"
                                    ),
                                    results.getDouble(
                                            "monthly_contribution"
                                    ),
                                    results.getString(
                                            "label"
                                    )
                            );

                    investment.setDatabaseID(
                            results.getInt(
                                    "investment_id"
                            )
                    );

                    investments.add(investment);
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to load investments.",
                    e
            );
        }

        return investments;
    }

    public void update(
            Investment investment
    ) {

        String sql = """
                UPDATE investments
                SET
                    label = ?,
                    balance = ?,
                    apy_decimal = ?,
                    monthly_contribution = ?
                WHERE investment_id = ?
                """;

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(
                    1,
                    investment.getLabel()
            );

            statement.setDouble(
                    2,
                    investment.getBalance()
            );

            statement.setDouble(
                    3,
                    investment.getAPYDecimal()
            );

            statement.setDouble(
                    4,
                    investment.getMonthlyContribution()
            );

            statement.setInt(
                    5,
                    investment.getDatabaseID()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to update investment.",
                    e
            );
        }
    }

    public void delete(
            Investment investment
    ) {

        String sql = """
                DELETE FROM investments
                WHERE investment_id = ?
                """;

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    investment.getDatabaseID()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to delete investment.",
                    e
            );
        }
    }
}
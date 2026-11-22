package com.trajectory.app.data;

import com.trajectory.app.model.Budget;
import com.trajectory.app.model.Debt;
import com.trajectory.app.model.Investment;
import com.trajectory.app.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles database operations for User objects.
 */
public class UserRepository {

    /**
     * Saves a new user.
     */
    public void save(User user) {

        String sql = """
                INSERT INTO users (name)
                VALUES (?)
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

            statement.setString(
                    1,
                    user.getName()
            );

            statement.executeUpdate();

            /*
             * Get SQLite's generated user_id.
             */
            try (ResultSet keys =
                         statement.getGeneratedKeys()) {

                if (keys.next()) {

                    user.setDatabaseID(
                            keys.getInt(1)
                    );
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to save user.",
                    e
            );
        }
    }

    /**
     * Loads one user.
     */
    public User findByID(int userID) {

        String sql = """
                SELECT user_id, name
                FROM users
                WHERE user_id = ?
                """;

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

                if (results.next()) {

                    User user =
                            new User(
                                    results.getString("name")
                            );

                    user.setDatabaseID(
                            results.getInt("user_id")
                    );

                    return user;
                }
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to load user.",
                    e
            );
        }

        return null;
    }

    /**
     * Loads every saved profile.
     */
    public List<User> findAll() {

        String sql = """
                SELECT user_id, name
                FROM users
                ORDER BY name
                """;

        ArrayList<User> users =
                new ArrayList<>();

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet results =
                        statement.executeQuery()
        ) {

            while (results.next()) {

                User user =
                        new User(
                                results.getString("name")
                        );

                user.setDatabaseID(
                        results.getInt("user_id")
                );

                users.add(user);
            }

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to load users.",
                    e
            );
        }

        return users;
    }

    /**
     * Deletes a user.
     *
     * Because the child tables use ON DELETE CASCADE,
     * the user's debts, investments, budgets, and
     * budget entries will also be removed.
     */
    public void delete(User user) {

        String sql = """
                DELETE FROM users
                WHERE user_id = ?
                """;

        try (
                Connection connection =
                        DatabaseManager.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(
                    1,
                    user.getDatabaseID()
            );

            statement.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(
                    "Unable to delete user.",
                    e
            );
        }
    }

    /**
     * Loads a User and all financial information
     * belonging to that User.
     */
    public User loadFullProfile(int userID) {

        User user =
                findByID(userID);

        if (user == null) {
            return null;
        }

        DebtRepository debtRepository =
                new DebtRepository();

        InvestmentRepository investmentRepository =
                new InvestmentRepository();

        BudgetRepository budgetRepository =
                new BudgetRepository();

        for (Debt debt :
                debtRepository.findByUserID(userID)) {

            user.addDebt(debt);
        }

        for (Investment investment :
                investmentRepository.findByUserID(userID)) {

            user.addInvestment(investment);
        }

        for (Budget budget :
                budgetRepository.findByUserID(userID)) {

            user.addBudget(budget);
        }

        return user;
    }
}
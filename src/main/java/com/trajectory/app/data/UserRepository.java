package com.trajectory.app.data;

import com.trajectory.app.model.Budget;
import com.trajectory.app.model.Debt;
import com.trajectory.app.model.Investment;
import com.trajectory.app.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserRepository handles database operations involving User objects.
 */
public class UserRepository {

    /**
     * Saves a new User to SQLite.
     *
     * After SQLite generates the user_id, that ID is placed
     * into the User object's databaseID field.
     *
     * @param user User to save
     */
    public void save(User user) {

        String sql = """
                INSERT INTO users (name)
                VALUES (?)
                """;

        try (Connection connection =
                     DatabaseManager.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            statement.setString(1, user.getName());

            statement.executeUpdate();

            /*
             * Retrieve the automatically generated SQLite ID.
             */
            try (ResultSet keys = statement.getGeneratedKeys()) {

                if (keys.next()) {
                    user.setDatabaseID(keys.getInt(1));
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
     * Finds one user using the database ID.
     *
     * This loads only the basic User information.
     * Use loadFullProfile() to also load debts,
     * investments, and budgets.
     *
     * @param userID database user ID
     * @return User or null if not found
     */
    public User findByID(int userID) {

        String sql = """
                SELECT user_id, name
                FROM users
                WHERE user_id = ?
                """;

        try (Connection connection =
                     DatabaseManager.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userID);

            try (ResultSet results =
                         statement.executeQuery()) {

                if (results.next()) {

                    User user =
                            new User(results.getString("name"));

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
     * Returns every saved profile.
     *
     * Useful for a profile-selection screen.
     */
    public List<User> findAll() {

        String sql = """
                SELECT user_id, name
                FROM users
                ORDER BY name
                """;

        ArrayList<User> users = new ArrayList<>();

        try (Connection connection =
                     DatabaseManager.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql);

             ResultSet results =
                     statement.executeQuery()) {

            while (results.next()) {

                User user =
                        new User(results.getString("name"));

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
     * Deletes a User.
     *
     * Because our foreign keys use ON DELETE CASCADE,
     * deleting the User also deletes the User's:
     *
     * debts
     * investments
     * budgets
     * budget entries
     */
    public void delete(User user) {

        String sql = """
                DELETE FROM users
                WHERE user_id = ?
                """;

        try (Connection connection =
                     DatabaseManager.getConnection();

             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

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
     * Loads the complete financial profile for a User.
     *
     * This method:
     *
     * 1. Loads the User
     * 2. Loads all Debts
     * 3. Loads all Investments
     * 4. Loads all Budgets and their BudgetEntries
     *
     * @param userID database ID of User
     * @return completely reconstructed User object
     */
    public User loadFullProfile(int userID) {

        User user = findByID(userID);

        // User does not exist.
        if (user == null) {
            return null;
        }

        DebtRepository debtRepository =
                new DebtRepository();

        InvestmentRepository investmentRepository =
                new InvestmentRepository();

        BudgetRepository budgetRepository =
                new BudgetRepository();

        /*
         * Load each debt and put it back into
         * the User object's listOfDebts.
         */
        for (Debt debt :
                debtRepository.findByUserID(userID)) {

            user.addDebt(debt);
        }

        /*
         * Restore investments.
         */
        for (Investment investment :
                investmentRepository.findByUserID(userID)) {

            user.addInvestment(investment);
        }

        /*
         * Restore budgets.
         *
         * BudgetRepository also restores each Budget's
         * BudgetEntry objects.
         */
        for (Budget budget :
                budgetRepository.findByUserID(userID)) {

            user.addBudget(budget);
        }

        return user;
    }
}
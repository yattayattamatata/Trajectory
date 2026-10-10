package com.trajectory.app.model;
import java.util.ArrayList;

/**
 * User Class that holds basic info about the user.
 */

public class User {
    private String name; // User identifying information.
    private ArrayList<Debt> listOfDebts = new ArrayList<>(); // An arraylist of debt objects used to represent all of the debts a user has. 
    private ArrayList<Investment> listOfInvestments = new ArrayList<>(); // An arraylist of investment objects used to represent all of the investments a user has.
    private ArrayList<Budget> listOfBudgets = new ArrayList<>(); // An Arraylist of budget items used to represent all of the budgets a user has.

    private int databaseID = 0; // Object's database ID, default is 0, set once added to SQLite. Nothing else should change this.

// Constructor

    /**
     * Constructor that creates a user. Starts with empty debt, investment, and budget lists.
     * @param name Name of the user
     */
    public User(String name){
        this.name = name;
    }


// Getters

    /**
     * Get user's list of debts.
     * @return List of Debts.
     */
    public ArrayList<Debt> getDebts(){
        return new ArrayList<>(listOfDebts);
    }

    /**
     * Get user's list of investments.
     * @return List of Investments.
     */
    public ArrayList<Investment> getInvestments(){
        return new ArrayList<>(listOfInvestments);
    }

    /**
     * Get user's list of budgets.
     * @return List of Budgets.
     */
    public ArrayList<Budget> getBudgets(){
        return new ArrayList<>(listOfBudgets);
    }

    /**
     * Get user's name.
     * @return User's name.
     */
    public String getName(){
        return name;
    }

    /**
     * Get database id for user.
     * @return Database id of user.
     */
    public int getDatabaseID(){return databaseID;}
    

// Setters

    /**
     * Method to assign debts to a user.
     * @param newDebt Singular debt to add.
     */
    public void addDebt(Debt newDebt){
        listOfDebts.add(newDebt);
    }

    /**
     * Method to remove debt from a user.
     * @param debt Debt to remove.
     */
    public void removeDebt(Debt debt){
        listOfDebts.remove(debt);
    }

    /**
     * Method to assign investments to a user.
     * @param newInvestment Singular investment to add.
     */
    public void addInvestment(Investment newInvestment){
        listOfInvestments.add(newInvestment);
    }

    /**
     * Method to remove investments from a user.
     * @param investment Investment to remove.
     */
    public void removeInvestment(Investment investment){
        listOfInvestments.remove(investment);
    }

    /**
     * Method to assign budgets to a user.
     * @param newBudget Singular budget to add.
     */
    public void addBudget(Budget newBudget){
        listOfBudgets.add(newBudget);
    }

    /**
     * Method to remove budgets from a user.
     * @param budget Budget to remove.
     */
    public void removeBudget(Budget budget){
        listOfBudgets.remove(budget);
    }

    /**
     * Set database id of a user. DB USE ONLY!
     * @param newDatabaseID New database id of a user.
     */
    public void setDatabaseID(int newDatabaseID){
        this.databaseID = newDatabaseID;
    }


// Calculations

    /**
     * Method that adds all of the user's debts.
     * @return Total owed.
     */
    public double getTotalDebt(){
        double total = 0.0;

        for (Debt debt: listOfDebts){
            total += debt.getBalance();
        }

        return total;
    }

    /**
     * Method that adds all of the user's investments.
     * @return Total invested.
     */
    public double getTotalInvested(){
        double total = 0.0;

        for (Investment investment: listOfInvestments){
            total += investment.getBalance();
        }

        return total;
    }
}

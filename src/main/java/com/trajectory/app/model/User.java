package com.trajectory.app.model;
import java.util.ArrayList;

/**
 * User Class that holds basic info about the user
 */

public class User {
    private String name; // User identifying information.
    private ArrayList<Debt> listOfDebts = new ArrayList<>(); // An arraylist of debt objects used to represent all of the debts a user has. 
    private ArrayList<Investment> listOfInvestments = new ArrayList<>(); // An arraylist of investment objects used to represent all of the investments a user has.
//    private Budget myBudget; // A budget item representing this user's current budget.

// Constructors

    /**
     * Constructor that creates a user. Starts with empty debt and investment lists.
     * @param name Name of the user
     */
    public User(String name){
        this.name = name;
    }


// Setters
    /**
     * Method to assign debts to a user.
     * @param debt Singular debt to add.
     */
    public void addDebt(Debt debt){
        listOfDebts.add(debt);
    }

    /**
     * Method to assign investments to a user.
     * @param investment Singular investment to add.
     */
    public void addInvestment(Investment investment){
        listOfInvestments.add(investment);
    }


// Getters

    /**
     * Get user's list of debts.
     * @return List of Debts.
     */
    public ArrayList<Debt> getDebts(){
        return this.listOfDebts;
    }

    /**
     * Get user's list of investments.
     * @return List of Investments.
     */
    public ArrayList<Investment> getInvestments(){
        return this.listOfInvestments;
    }

    /**
     * Get user's name.
     * @return User's name.
     */
    public String getName(){
        return name;
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

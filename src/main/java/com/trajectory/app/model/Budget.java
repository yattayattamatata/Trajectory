package com.trajectory.app.model;

import java.util.ArrayList;

/**
 * Budget class represents 1 month's income and expenses.
 */
public class Budget {

    private String name; // Name of this budget.
    private int month, // Int value of month (1-12) that this budget covers.
                year; // Year that this budget covers.
    private ArrayList<BudgetEntry> monthlyEntries = new ArrayList<>(); // A list of all budget entries that make up this budget.

// Constructors
    /**
     * Constructor that builds a new budget item.
     * @param name Name of the budget.
     * @param month Month that this budget covers (1-12).
     * @param year Year that this budget covers.
     */
    public Budget(String name, int month, int year){
        this.name = name;
        this.month = month;
        this.year = year;
    }


// Getters
    /**
     * Gets the name of this budget.
     * @return Name of budget.
     */
    public String getBudgetName(){return this.name;}

    /**
     * Gets the month of this budget.
     * @return Month (1-12) of budget.
     */
    public int getMonth(){return this.month;}

    /**
     * Gets the year of this budget.
     * @return Year of budget.
     */
    public int getYear(){return this.year;}

    /**
     * Gets a list of budget item entries.
     * @return A copy of the budget entries (copy used so that original values cannot be modified outside of setters and constructor).
     */
    public ArrayList<BudgetEntry> getEntries(){return new ArrayList<>(monthlyEntries);}


// Setters
    /**
     * Method to add new budget entries to this budget.
     * @param newEntry Budget entry item that is added.
     */
    public void addEntry(BudgetEntry newEntry){
        monthlyEntries.add(newEntry);
    }

    /**
     * Method to remove a budget entry from this budget.
     * @param entry Budget entry item that is to be removed.
     */
    public void removeEntry(BudgetEntry entry){
        monthlyEntries.remove(entry);
    }

    /**
     * Method to change the name of the budget.
     * @param newName New name for budget.
     */
    public void setName(String newName){
        this.name = newName;
    }

    /**
     * Method to change the month of the budget.
     * @param newMonth New month of the budget.
     */
    public void setMonth(int newMonth){
        this.month = newMonth;
    }

    /**
     * Method to change the year of the budget.
     * @param newYear New year of the budget.
     */
    public void setYear(int newYear){
        this.year = newYear;
    }


// Calculations

    /**
     * Method that will calculate the total income for a budget.
     * @return Total income.
     */
    public double calculateTotalIncome(){
        double totalIncome = 0.0;

        for(BudgetEntry myEntry: monthlyEntries){
            if (myEntry.getCategoryType() == BudgetCategory.Type.INCOME){
                totalIncome += myEntry.getAmount();
            }
        }
        return totalIncome;
    }

    /**
     * Method that will calculate the total expense for a budget.
     * @return Total expenses.
     */
    public double calculateTotalExpense(){
        double totalExpense = 0.0;

        for(BudgetEntry myEntry: monthlyEntries){
            if (myEntry.getCategoryType() == BudgetCategory.Type.EXPENSE){
                totalExpense += myEntry.getAmount();
            }
        }
        return totalExpense;
    }

    /**
     * Method that will calculate the difference between income and expense for a budget.
     * @return Difference between income and expense.
     */
    public  double calculateRemainingBudget(){
        return this.calculateTotalIncome() - this.calculateTotalExpense();
    }

}

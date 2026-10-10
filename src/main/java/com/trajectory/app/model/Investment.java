package com.trajectory.app.model;
import java.util.ArrayList;

/**
 * Investment class representing a single investment or savings account.
 */

public class Investment {
    private double balance, // Principal amount for investment.
                   apyDecimal, // APY as a decimal (5% = 0.05).
                   monthlyContribution; // Monthly contribution.

    private String label; // Title for this investment.


//Constructor
    /**
     * Constructs an investment object representing a single investment or savings account.
     * @param balance Principal amount on an investment.
     * @param apyDecimal APY as a decimal (5% = 0.05).
     * @param monthlyContribution Monthly contribution.
     * @param label Title for this investment.
     */
    public Investment(double balance, double apyDecimal, double monthlyContribution, String label){
        this.balance = balance;
        this.apyDecimal = apyDecimal;
        this.monthlyContribution = monthlyContribution;
        this.label = label;
    }


// Getters

    /**
     * Get balance for investment.
     * @return Balance of investment.
     */
    public double getBalance(){return balance;}

    /**
     * Get APY for investment.
     * @return APY of investment as decimal (5% = 0.05).
     */
    public double getAPYDecimal(){return apyDecimal;}

    /**
     * Get monthly contribution for investment.
     * @return Monthly contribution of investment.
     */
    public double getMonthlyContribution(){return monthlyContribution;}

    /**
     * Get label for investment.
     * @return Label of investment.
     */
    public String getLabel(){return label;}


// Setters

    /**
     * Set balance of an investment.
     * @param newBalance New value for balance of an investment.
     */
    public void setBalance(double newBalance){
        this.balance = newBalance;
    }

    /**
     * Set APY of an investment.
     * @param newAPY New APY of investment as decimal (5% = 0.05).
     */
    public void setAPYDecimal(double newAPY){
        this.apyDecimal = newAPY;
    }

    /**
     * Set monthly contribution of an investment.
     * @param newMonthlyContribution New monthly contribution of an investment.
     */
    public void setMonthlyContribution(double newMonthlyContribution){
        this.monthlyContribution = newMonthlyContribution;
    }

    /**
     * Set label of an investment.
     * @param newLabel New label of an investment.
     */
    public void setLabel(String newLabel){
        this.label = newLabel;
    }

// Calculations

    /**
     * Method that projects this investment's balance over time (assuming constant contributions).
     * @param years Number of years to project growth.
     * @return Balance at end of each year.
     */
    public ArrayList<Double> growthOverTime(int years){
        CompoundInterestCalculator calculator = new CompoundInterestCalculator(balance, apyDecimal, monthlyContribution, 12, years);

        return calculator.getBalanceOverTime();
    }
}

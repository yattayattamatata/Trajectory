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
     * Constructs a Investment object representing a single investment or savings account.
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

import java.util.ArrayList;

/**
 * DebtPayoffFeasibility is a helper class for Debt
 */

public class DebtPayoffFeasibility {

    private boolean isPossible;
    private int monthsToPayoff;
    private double totalInterestPaid;
    private ArrayList<Double> monthlyBalance;

// Constructors
    /**
     * Private constructor used by impossible and possible function to create helper objects.
     * @param isPossible If it is possible to pay the debt off or not.
     * @param monthsToPayoff How many months until the new balance reaches zero.
     * @param totalInterestPaid Amount of interest paid.
     * @param monthlyBalance List of doubles that show monthly balance over time.
     */
    private DebtPayoffFeasibility(boolean isPossible, int monthsToPayoff, double totalInterestPaid, ArrayList<Double> monthlyBalance){
        this.isPossible = isPossible;
        this.monthsToPayoff = monthsToPayoff;
        this.totalInterestPaid = totalInterestPaid;
        this.monthlyBalance = monthlyBalance;
    }

// Getters
    public boolean isPossible(){
        return isPossible;
    }

    public int getMonthsToPayoff() {
        return monthsToPayoff;
    }

    public double getTotalInterestPaid() {
        return totalInterestPaid;
    }

    public ArrayList<Double> getMonthlyBalance() {
        return monthlyBalance;
    }

// Calculations

    /**
     * Method used when a debt payoff is not possible. Static so it can be called without an existing object; allowing the standardized object to be created.
     * @return Returns a standard impossible debt payoff object.
     */
    public static DebtPayoffFeasibility impossible(){
        return new DebtPayoffFeasibility(false, 0, 0.0, new ArrayList<Double>());
    }

    /**
     * Method used when a debt payoff is possible. Static so it can be called without an existing object; allowing the standardized object to be created.
     * @param monthsToPayoff How many months until the new balance reaches zero.
     * @param totalInterestPaid Amount of interest paid.
     * @param monthlyBalance List of doubles that show monthly balance over time.
     * @return Returns the possible debt payoff object.
     */
    public static DebtPayoffFeasibility possible(int monthsToPayoff, double totalInterestPaid, ArrayList<Double> monthlyBalance){
        return new DebtPayoffFeasibility(true, monthsToPayoff, totalInterestPaid, monthlyBalance);
    }

}

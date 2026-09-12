import java.util.ArrayList;

/**
 * Debt class that represents a single debt or loan.
 */

public class Debt {

    private double balance, // Principal amount owed on debt.
                   aprDecimal, // APR as a decimal (5% = 0.05).
                   minimumPayment; // Minimum required monthly payment.

    private String label; // Title for this debt.

    CompoundInterestCalculator interestHelper;


// Constructor
    /**
     * Constructs a Debt object representing a single debt or loan.
     * @param balance Principal amount owed on debt.
     * @param aprDecimal APR as a decimal (5% = 0.05).
     * @param minimumPayment Minimum required monthly payment.
     * @param label Title for this debt.
     */
    Debt(double balance, double aprDecimal, double minimumPayment, String label){
        this.balance = balance;
        this.aprDecimal = aprDecimal;
        this.minimumPayment = minimumPayment;
        this.label = label;
    }


// Calculations

    /**
     * Method that calculates the interest accrued this month on the balance.
     * @return Interest amount for one month with current balance and APR.
     */
    public double calculateMonthlyInterest(){

        return balance * (aprDecimal / 12);
    }

    /**
     * Method to project repayment timeline. Returns impossible if the payment is not more than the interest accrued.
     * @param monthlyPayment Amount paid towards debt.
     * @return DebtPayoffFeasibility object that contains whether it is possible to payoff the debt, and if it is, it contains how long and what the cost of the debt is
     */
    public DebtPayoffFeasibility projectPayoffTimeline(double monthlyPayment){

        double startingInterest = calculateMonthlyInterest();

        if (monthlyPayment <= startingInterest) { // If the monthly payment is less than or equal to the starting interest, then the balance would never get smaller.
            return DebtPayoffFeasibility.impossible();
        }

        double runningBalance = balance; // Holds the balance that is paid down month to month.
        double totalInterestPaid = 0.0; // Running total of interest paid during debt repayment.
        ArrayList<Double> monthlyBalances = new ArrayList<Double>(); // List of doubles that show monthly balance over time.
        int monthsUntilPayoff = 0; // Running total months needed to pay off debt.

        while (runningBalance > 0){
            double interestAccrued = runningBalance * (aprDecimal / 12); // Interest this month.
            totalInterestPaid += interestAccrued; // Add this interest to tracker.

            runningBalance = runningBalance + interestAccrued - monthlyPayment; // Correct the running balance to account for additional interest and subtract payment.

            if (runningBalance < 0){ // Check to see if debt is paid off.
                runningBalance = 0; // If it is paid off, show 0 not negative number.
            }
            monthlyBalances.add(runningBalance); // Add the new remaining balance to the list.
            monthsUntilPayoff++; // Add a month to the running total.
        }

        return DebtPayoffFeasibility.possible(monthsUntilPayoff, totalInterestPaid, monthlyBalances); // When debt repayment fully calculated create a feasibility object that reflects this debt's details.
    }
}

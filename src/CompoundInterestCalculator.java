import java.util.ArrayList;

/**
 * CompoundInterestCalculator Class used to compute the future value of money that grows with compound growth (can be combined with contributions or payments).
 * 
 * The sign (+/-) of periodicContribution allows these calculations to work for both Investment and Debt.
 * 
 */

public class CompoundInterestCalculator {

    private double amountOverTime = 0.0,  // Computed total after growth and contributions or payments.
                   principalAmount,       // Starting balance (P) before growth.
                   rate,                  // Annual interest rate, expressed in decimal form (5% = 0.05).
                   periodicContribution;  // Amount added or removed each compounding period. (+) = investment contribution (-) = debt payment
           
    private int numberOfCompoundsPerYear, // How many times per year that interest compounds (n).
                numberYears;              // Number of years to project (t).

// Constructors

    /**
     * Constructs a calculator and computes the final amount.
     * @param principal       starting balance
     * @param interestRate    annual rate as decimal
     * @param contribution    amount added or removed per compounding period
     * @param annualCompounds times interest compounds per year
     * @param years           number of years to project
     */
    CompoundInterestCalculator(double principal, double interestRate,double contribution, int annualCompounds, int years){
        this.principalAmount = principal;
        this.rate = interestRate;
        this.periodicContribution = contribution;
        this.numberOfCompoundsPerYear = annualCompounds;
        this.numberYears = years;
        calculateInterest();
    }


// Getters

    /**
     * Get the final amount after all compounding and contributions
     * @return final amount after all compounding and contributions
     */
    public double getAmountOverTime(){
        return amountOverTime;
    }


// Calculations

    /**
     * Calculates final future value using:
     *  1. FV_Principal = P * (1 + r/n)^(n * t) ~ future value of principle growing by itself
     *  2. FV_Contributions = C * [ (1 + r / n)^(n * t) - 1] / (r / n) ~ future value of ordinary annuity
     * 
     * Add the amounts because the principal and contributions are independent sources of money that grow or shrink together.
     * REFERENCE: https://www.calculatorsoup.com/calculators/financial/future-value-annuity-calculator.php
     */
    private void calculateInterest(){
        double n = numberOfCompoundsPerYear;
        double growthFactor = Math.pow(1 + rate / n, n * numberYears); // How much a dollar today, invested, would grow to in time period.

        double principalGrowth = principalAmount * growthFactor; // Future value of principal, using growth factor.
        double contributionGrowth = periodicContribution * ((growthFactor - 1) / (rate / n)); // Future value of all contributions.

        amountOverTime =  principalGrowth + contributionGrowth; // Combined principal growth and contribution growth.
        
    }

    /**
     * Returns running balance at the end of each year for the duration instead of a final total. Utilizing loop to manually compute values instead of annuity formula.
     * @return List of balances at the end of each year.
     */
    public ArrayList<Double> getBalanceOverTime(){

        ArrayList<Double> balances = new ArrayList<>(); // List that will hold one value per year.

        double balance = principalAmount, // Starting balance is principal.
               periodicRate = rate / numberOfCompoundsPerYear; // Interest rate applied per compound period.
        int totalPeriods = numberOfCompoundsPerYear * numberYears; // Total number of compounding periods.

        for ( int i = 1; i <= totalPeriods; i++){ // Loop through every compounding period and apply one periods worth of interest then add/subtract the contribution or payment.
            balance = balance * (1 + periodicRate) + periodicContribution;

            if (i % numberOfCompoundsPerYear == 0){ // Once per year add the balance to the list (so we don't have a value for every compound period).
                balances.add(balance);
            }
        }

        return balances;
    }
    
        
}

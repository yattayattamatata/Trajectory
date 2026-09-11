/**
 * Debt class that represents a single debt or loan.
 */

public class Debt {

    double balance, // Total amount owed on debt.
           aprDecimal, // Decimal version of APR 5% => 0.05
           minimumPayment; // Minimum 

    String label; // Name of specific debt

    CompoundInterestCalculator interestHelper;

    Debt(){

    }

    public void calculateInterestAccrued(){
        
    }

    public void projectPayoffTimeline(){

    }
}

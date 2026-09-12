package com.trajectory.app;

import com.trajectory.app.model.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

    // Get User
        System.out.println("Please enter your name:");
        User newUser = new User(scnr.nextLine());

    //Get debts
        while(true){
            System.out.println("\nDo you have a debt to enter? (Y/N)");
            if (scnr.nextLine().toUpperCase().equals("N")){
                break;
            }
            System.out.println("What is the name of this debt?");
            String debtName = scnr.nextLine();
            System.out.println("What is the balance of your " + debtName + " debt?");
            double debtBal = scnr.nextDouble();
            System.out.println("What is the APR of your " + debtName + " debt (as a decimal 5% = 0.05)?");
            double debtAPR = scnr.nextDouble();
            System.out.println("What is the minimum payment of your " + debtName + " debt?");
            double debtMinPay = scnr.nextDouble();
            scnr.nextLine();
            Debt currentDebt = new Debt(debtBal, debtAPR, debtMinPay, debtName);
            newUser.addDebt(currentDebt);
        }

    // Get investments
        while(true){
            System.out.println("\nDo you have a investment to enter? (Y/N)");
            if (scnr.nextLine().toUpperCase().equals("N")){
                break;
            }
            System.out.println("What is the name of this investment?");
            String investmentName = scnr.nextLine();
            System.out.println("What is the balance of your " + investmentName + " investment?");
            double investmentBal = scnr.nextDouble();
            System.out.println("What is the APY of your " + investmentName + " investment (as a decimal 5% = 0.05)?");
            double investmentAPY = scnr.nextDouble();
            System.out.println("What is the monthly contribution of your " + investmentName + " investment?");
            double investmentMonContrib = scnr.nextDouble();
            scnr.nextLine();
            Investment currentInvestment = new Investment(investmentBal, investmentAPY, investmentMonContrib, investmentName);
            newUser.addInvestment(currentInvestment);
        }

    // Debt Payoffs
        for(Debt debt: newUser.getDebts()){
            System.out.println("\nWhat monthly payment do you want to test for " + debt.getLabel() + "?");
            double testPayment = Double.parseDouble(scnr.nextLine());

            DebtPayoffFeasibility result = debt.projectPayoffTimeline(testPayment);

            if (!result.isPossible()){
                System.out.println("At $" + testPayment + "/month, " + debt.getLabel() + " can never get paid off (payment is less than interest accrued)!");
            }
            else{
                System.out.println(debt.getLabel() + " would be paid off in " + result.getMonthsToPayoff() + " months, costing $" + String.format("%,.2f", result.getTotalInterestPaid()) + " in interest.");
            }
        }

    // Investment Growth
        for(Investment investment: newUser.getInvestments()){
            System.out.println("\nHow many years do you want to project growth for " + investment.getLabel() + "?");
            int years = Integer.parseInt(scnr.nextLine());

            ArrayList<Double> growth = investment.growthOverTime(years);

            double finalBal = growth.get(growth.size() - 1);
            System.out.println(investment.getLabel() + " is projected to grow to $" + String.format("%,.2f", finalBal) + " after " + years + " years.");
        }

    // Summary
        System.out.print("\n\n");
        System.out.println("Summary for " + newUser.getName());
        System.out.println("Total debt $" + String.format("%,.2f", newUser.getTotalDebt()));
        System.out.println("Total investments $" + String.format("%,.2f", newUser.getTotalInvested()));
    }


    
}

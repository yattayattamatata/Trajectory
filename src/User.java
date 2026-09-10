import java.util.ArrayList;

/**
 * User Class that holds basic info about the user
 */

public class User {
    private String name; // User identifying information.
    private ArrayList<Debt> listOfDebts = new ArrayList<>(); // An arraylist of debt objects used to represent all of the debts a user has. 
    private ArrayList<Investment> listOfInvestments = new ArrayList<>(); // An arraylist of investment objects used to represent all of the investments a user has.
    private Budget myBudget; // A budget item representing this user's current budget.


    User(){

    }
}

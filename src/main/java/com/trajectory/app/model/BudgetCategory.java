package com.trajectory.app.model;

import java.util.ArrayList;

/**
 * An Enum (list of non-changing entries ~think days of the week M-T-W-H-F-Sa-Su) for the different possible income and expense budget categories. 
 */
public enum BudgetCategory {

    // Income Categories
    SALARY                  (Type.INCOME,"Salary"),
    SIDE_INCOME             (Type.INCOME,"Side Income"),
    INVESTMENT_INCOME       (Type.INCOME,"Investment Income"),
    OTHER_INCOME            (Type.INCOME,"Other Income"),

    //Expense Categories
    HOUSING                 (Type.EXPENSE,"Housing"),
    UTILITIES               (Type.EXPENSE,"Utilities"),
    GROCERIES               (Type.EXPENSE,"Groceries"),
    TRANSPORTATION          (Type.EXPENSE,"Transportation"),
    INSURANCE               (Type.EXPENSE,"Insurance"),
    HEALTHCARE              (Type.EXPENSE,"Healthcare"),
    DINING                  (Type.EXPENSE,"Dining Out"),
    SHOPPING                (Type.EXPENSE, "Shopping"),
    ENTERTAINMENT           (Type.EXPENSE,"Entertainment"),
    SUBSCRIPTIONS           (Type.EXPENSE,"Subscriptions"),
    DEBT_PAYMENTS           (Type.EXPENSE,"Debt Payments"),
    SAVINGS                 (Type.EXPENSE,"Saving Contributions"),
    INVESTMENTS             (Type.EXPENSE,"Investment Contributions"),
    OTHER_EXPENSE           (Type.EXPENSE,"Other Expense");


    /**
     * Enum used to differentiate a budget category as an income or an expense category.
     */
    public enum Type {
        INCOME,
        EXPENSE;
    }


    private final Type type; // Income or Expense, differentiating categories.
    private final String guiName; // Name that is displayed in GUI.
    

// Constructors
    /**
     * Constructor that gives each category specific traits.
     * @param type Income or Expense.
     * @param guiName Displayed name.
     */
    BudgetCategory(Type type, String guiName){
        this.type = type;
        this.guiName = guiName;
    }


// Getters
    /**
     * Method used to get a category type.
     * @return Income or Expense Type.
     */
    public Type getType(){
        return this.type;
    }

    /**
     * Method used to get a category's displayed name.
     * @return Name of a category for display.
     */
    public String getGUIName(){
        return this.guiName;
    }

    /**
     * Method used to get a list of all possible categories belonging to a specific type.
     * @param desiredType Desired type (INCOME||EXPENSE).
     * @return List of categories belonging to desired type.
     */
    public static ArrayList<BudgetCategory> getCategories(Type desiredType){

        ArrayList<BudgetCategory> correspondingCategories = new ArrayList<>(); // List of desired categories (expense or income).

        for (BudgetCategory category: BudgetCategory.values()){ // Loop through all values in this enumeration and if the value is the desired type then add it to the array list.
            if (category.type == desiredType){
                correspondingCategories.add(category);
            }
        }
        return correspondingCategories;
        
    }
}

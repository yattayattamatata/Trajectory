package com.trajectory.app.model;

/**
 * Class used to build entries in a budget.
 */
public class BudgetEntry {

    private String customDescription; // Holds user defined name (custom label when other is selected).
    private BudgetCategory category; // Holds specific category that this budget entry is.
    private double amount; // Amount assigned to this entry.


// Constructor
    /**
     * Constructor that builds a single entry in a budget.
     * @param description User-defined name when other is selected.
     * @param category Category from BudgetCategory Enum
     * @param amount Amount assigned to this entry. Always positive.
     */
    public BudgetEntry(String description, BudgetCategory category, double amount){
        this.customDescription = description;
        this.category = category;
        this.amount = amount;
    }


// Calculations
    /**
     * Method used to determine if a entry is an OTHER category.
     * @return If the category is an OTHER category.
     */
    private boolean isOtherCategory(){
        boolean isOther = category == BudgetCategory.OTHER_EXPENSE || category == BudgetCategory.OTHER_INCOME ? true : false;
        return isOther;
    }

    
// Getters

    /**
     * Gets the description of a budget entry.
     * @return The name of an entry (if other, return custom name else enum description).
     */
    public String getDescription(){
        if (isOtherCategory()){
            return this.customDescription;
        }
        else{
            return category.getGUIName();
        }
    }

    /**
     * Gets the category of a budget entry.
     * @return Category of entry.
     */
    public BudgetCategory getBudgetCategory(){return this.category;}

    /**
     * Gets the amount of a budget entry.
     * @return Amount of entry.
     */
    public double getAmount(){return this.amount;}

    /**
     * Gets the category type (income or expense) of an budget entry.
     * @return Type of entry.
     */
    public BudgetCategory.Type getCategoryType(){return this.category.getType();}


// Setters

    /**
     * Sets the description of a budget entry.
     * @param newDescription New description of an entry.
     */
    public void setDescription(String newDescription){this.customDescription = newDescription;}

    /**
     * Sets the category of a budget entry.
     * @param newCategory New category of an entry.
     */
    public void setBudgetCategory(BudgetCategory newCategory){this.category = newCategory;}

    /**
     * Sets the amount of a budget entry.
     * @param newAmount New amount of an entry.
     */
    public void setAmount(double newAmount){this.amount = newAmount;}

}

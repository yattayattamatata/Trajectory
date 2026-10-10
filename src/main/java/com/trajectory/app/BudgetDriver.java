package com.trajectory.app;

import com.trajectory.app.model.*;

public class BudgetDriver {

    public static void main(String[] args) {
        User user = new User("Test");

        Budget october = new Budget("October", 10, 2026);
        october.addEntry(new BudgetEntry("", BudgetCategory.SALARY, 2500.00));
        october.addEntry(new BudgetEntry("Birthday cash", BudgetCategory.OTHER_INCOME, 300.00));
        october.addEntry(new BudgetEntry("", BudgetCategory.HOUSING, 1200.00));
        october.addEntry(new BudgetEntry("Dentist", BudgetCategory.OTHER_EXPENSE, 250.50));
        user.addBudget(october);

        System.out.println(october.calculateTotalIncome());     // expect 2800.0
        System.out.println(october.calculateTotalExpense());    // expect 1450.5
        System.out.println(october.calculateRemainingBudget()); // expect 1349.5

        for (BudgetEntry entry : october.getEntries()) {
            System.out.println(entry.getDescription() + " | " + entry.getBudgetCategory().getGUIName() + " | " + entry.getCategoryType() + " | " + entry.getAmount());
        }

        // Category list for a drop-down
        System.out.println(BudgetCategory.getCategories(BudgetCategory.Type.INCOME));

        // The description follows the category, even after an edit
        BudgetEntry housing = october.getEntries().get(2);
        housing.setBudgetCategory(BudgetCategory.GROCERIES);
        System.out.println(housing.getDescription());           // expect Groceries
    }
}
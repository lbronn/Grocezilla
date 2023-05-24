package com.example.partial;

public class Budget {
    String budgetTitle;
    String budgetAmount;
    String budgetDescription;

    public Budget() {
    }

    public String getBudgetTitle() {
        return budgetTitle;
    }

    public void setBudgetTitle(String budgetTitle) {
        this.budgetTitle = budgetTitle;
    }

    public String getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(String budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public String getBudgetDescription() {
        return budgetDescription;
    }

    public void setBudgetDescription(String budgetDescription) {
        this.budgetDescription = budgetDescription;
    }
}

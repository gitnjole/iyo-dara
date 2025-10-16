package iyo.dara.application.budget;

public record BudgetBreakdown(
        double expensesLiquid,
        double expensesGiro,
        double expensesMenza,
        double total
) {}

package iyo.dara.application.budget;

public record BudgetOpening (
    double paycheck,
    double other,
    double overflow,
    double total
) {}

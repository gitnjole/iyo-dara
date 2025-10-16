package iyo.dara.application.budget;

import iyo.dara.application.overflow.OverflowSplit;

public record BudgetStanding(
        double cash,
        double liquid,
        double total,
        OverflowSplit overflow
) {
}

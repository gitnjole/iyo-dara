package iyo.dara.application.budget;

import java.time.YearMonth;

public record BudgetPeriod(
        YearMonth period,
        BudgetOpening opening,
        BudgetBreakdown breakdown,
        BudgetStanding standing
) {
}

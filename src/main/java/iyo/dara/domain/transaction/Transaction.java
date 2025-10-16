package iyo.dara.domain.transaction;

import iyo.dara.domain.account.AccountType;
import iyo.dara.core.domain.DatedAccountable;

import java.time.LocalDate;

public record Transaction(
        String detail,
        Category category,
        Subcategory subcategory,
        double cost,
        LocalDate date,
        AccountType account,
        boolean impulse
) implements DatedAccountable {
    @Override
    public LocalDate date() { return date; }

    @Override
    public AccountType account() { return account; }
}

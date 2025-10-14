package iyo.dara.transaction.domain;

import iyo.dara.account.domain.AccountType;
import iyo.dara.core.domain.DatedAccountable;

import java.time.LocalDate;

public record Transaction(
        String detail,
        Category category,
        Subcategory subcategory,
        double cost,
        LocalDate date,
        AccountType paymentSource,
        boolean impulse
) implements DatedAccountable {
    @Override
    public LocalDate date() { return date; }

    @Override
    public AccountType account() { return paymentSource; }
}

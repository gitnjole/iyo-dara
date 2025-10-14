package iyo.dara.income.domain;

import iyo.dara.account.domain.AccountType;
import iyo.dara.core.domain.DatedAccountable;

import java.time.LocalDate;

public record Income(
        Source source,
        AccountType paymentSource,
        double amount,
        LocalDate date

) implements DatedAccountable {
    @Override
    public LocalDate date() { return date; }

    @Override
    public AccountType account() { return paymentSource; }
}
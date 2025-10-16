package iyo.dara.domain.income;

import iyo.dara.domain.account.AccountType;
import iyo.dara.core.domain.DatedAccountable;

import java.time.LocalDate;

public record Income(
        Source source,
        AccountType account,
        double amount,
        LocalDate date

) implements DatedAccountable {
    @Override
    public LocalDate date() { return date; }

    @Override
    public AccountType account() { return account; }
}
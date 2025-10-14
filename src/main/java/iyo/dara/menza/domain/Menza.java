package iyo.dara.menza.domain;

import iyo.dara.account.domain.AccountType;
import iyo.dara.core.domain.DatedAccountable;

import java.time.LocalDate;

public record Menza(
        String store,
        double cost,
        LocalDate date,
        AccountType account
) implements DatedAccountable {
    @Override
    public LocalDate date() { return date; }

    @Override
    public AccountType account() { return account; }
}

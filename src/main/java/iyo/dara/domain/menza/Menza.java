package iyo.dara.domain.menza;

import iyo.dara.domain.account.AccountType;
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

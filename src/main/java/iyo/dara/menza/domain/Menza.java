package iyo.dara.menza.domain;

import iyo.dara.account.domain.AccountType;

import java.time.LocalDate;

public record Menza(
        /*
        MenzaCategory category,
        Store store,
         */
        double cost,
        LocalDate date,
        AccountType paymentSource
) {
}

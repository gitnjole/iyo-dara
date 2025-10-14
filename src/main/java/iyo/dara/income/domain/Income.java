package iyo.dara.income.domain;

import iyo.dara.account.domain.AccountType;

import java.time.LocalDate;

public record Income(
        Source source,
        AccountType paymentSource,
        double amount,
        LocalDate date

) {
}

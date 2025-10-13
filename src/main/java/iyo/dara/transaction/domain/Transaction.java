package iyo.dara.transaction.domain;

import iyo.dara.account.domain.AccountType;

import java.time.LocalDate;

public record Transaction(
        String detail,
        Category category,
        Subcategory subcategory,
        double cost,
        LocalDate date,
        AccountType paymentSource,
        boolean impulse
) {
}

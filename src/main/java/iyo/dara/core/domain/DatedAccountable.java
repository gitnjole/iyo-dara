package iyo.dara.core.domain;

import iyo.dara.account.domain.AccountType;

import java.time.LocalDate;

public interface DatedAccountable {
    LocalDate date();
    AccountType account();
}

package iyo.dara.core.domain;

import iyo.dara.domain.account.AccountType;

import java.time.LocalDate;

public interface DatedAccountable {
    LocalDate date();
    AccountType account();
}

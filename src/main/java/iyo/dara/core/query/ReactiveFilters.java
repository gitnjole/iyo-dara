package iyo.dara.core.query;

import iyo.dara.account.domain.AccountType;
import iyo.dara.core.domain.DatedAccountable;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Optional;

public class ReactiveFilters {
    private ReactiveFilters() {}

    public static <T extends DatedAccountable> Flux<T> byDateRange(
            Flux<T> flux, Optional<LocalDate> start, Optional<LocalDate> end) {

        return flux.filter(item -> {
            boolean afterStart = start.map(s -> !item.date().isBefore(s)).orElse(true);
            boolean beforeEnd = end.map(e -> !item.date().isAfter(e)).orElse(true);
            return afterStart && beforeEnd;
        });
    }

    public static <T extends DatedAccountable> Flux<T> byAccount(
            Flux<T> flux, Optional<AccountType> account) {

        return flux.filter(item ->
                account.map(a -> item.paymentSource().equals(a)).orElse(true));
    }
}

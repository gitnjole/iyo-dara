package iyo.dara.income.query;

import iyo.dara.account.domain.AccountType;
import iyo.dara.core.query.QueryParser;
import iyo.dara.core.query.ReactiveFilters;
import iyo.dara.income.domain.Income;
import iyo.dara.income.domain.Source;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Optional;

public record IncomeQuery (
    String range,
    Optional<LocalDate> startDate,
    Optional<LocalDate> endDate,
    Optional<Source> incomeSource,
    Optional<AccountType> account
) {
    public static IncomeQuery from(ServerRequest request) {
        String range = QueryParser.parseString(request, "range")
                .orElseThrow(() -> new IllegalArgumentException("Missing param 'range'"));

        return new IncomeQuery(
                range,
                QueryParser.parseDate(request, "startDate"),
                QueryParser.parseDate(request, "endDate"),
                QueryParser.parseEnum(request, "incomeSource", Source.class),
                QueryParser.parseEnum(request, "account", AccountType.class)
        );
    }

    public Flux<Income> applyFilters(Flux<Income> flux) {
        return ReactiveFilters
                .byDateRange(flux, startDate, endDate)
                .transform(f -> ReactiveFilters.byAccount(f, account))
                .filter(this::matchesIncomeSource);
    }

    private boolean matchesIncomeSource(Income i) {
        return incomeSource.map(c -> i.source().equals(c)).orElse(true);
    }
}

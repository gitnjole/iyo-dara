package iyo.dara.domain.menza.query;

import iyo.dara.domain.account.AccountType;
import iyo.dara.core.query.QueryParser;
import iyo.dara.core.query.ReactiveFilters;
import iyo.dara.domain.menza.Menza;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Optional;

public record MenzaQuery(
        String range,
        Optional<LocalDate> startDate,
        Optional<LocalDate> endDate,
        Optional<String> store,
        Optional<AccountType> account
) {
    public static MenzaQuery from(ServerRequest request) {
        String range = QueryParser.parseString(request, "range")
                .orElseThrow(() -> new IllegalArgumentException("Missing param 'range"));

        return new MenzaQuery(
                range,
                QueryParser.parseDate(request, "startDate"),
                QueryParser.parseDate(request, "endDate"),
                QueryParser.parseString(request, "store"),
                QueryParser.parseEnum(request, "account", AccountType.class)
        );
    }

    public Flux<Menza> applyFilters(Flux<Menza> flux) {
        return ReactiveFilters
                .byDateRange(flux, startDate, endDate)
                .transform(f -> ReactiveFilters.byAccount(f, account))
                .filter(this::matchesStore);
    }

    private boolean matchesStore(Menza m) {
        return store.map(c -> m.store().equalsIgnoreCase(c)).orElse(true);
    }
}

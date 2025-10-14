package iyo.dara.transaction.query;

import iyo.dara.account.domain.AccountType;
import iyo.dara.core.query.QueryParser;
import iyo.dara.core.query.ReactiveFilters;
import iyo.dara.transaction.domain.Category;
import iyo.dara.transaction.domain.Subcategory;
import iyo.dara.transaction.domain.Transaction;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Optional;

public record TransactionQuery(
        String source,
        Optional<LocalDate> startDate,
        Optional<LocalDate> endDate,
        Optional<Category> category,
        Optional<Subcategory> subcategory,
        Optional<AccountType> account
) {
    public static TransactionQuery from(ServerRequest request) {
        String range = QueryParser.parseString(request, "range")
                .orElseThrow(() -> new IllegalArgumentException("Missing param 'range'"));

        return new TransactionQuery(
                range,
                QueryParser.parseDate(request, "startDate"),
                QueryParser.parseDate(request, "endDate"),
                QueryParser.parseEnum(request, "category", Category.class),
                QueryParser.parseEnum(request, "subcategory", Subcategory.class),
                QueryParser.parseEnum(request, "account", AccountType.class)
        );
    }

    public Flux<Transaction> applyFilters(Flux<Transaction> flux) {
        return ReactiveFilters
                .byDateRange(flux, startDate, endDate)
                .transform(f -> ReactiveFilters.byAccount(f, account))
                .filter(this::matchesSubCategory);
    }

    private boolean matchesSubCategory(Transaction t) {
        if (t.subcategory() == null || t.subcategory() == Subcategory.None)
            return subcategory.isEmpty();

        return subcategory
                .map(sc -> t.subcategory().name().equalsIgnoreCase(sc.name()))
                .orElse(true);
    }
}

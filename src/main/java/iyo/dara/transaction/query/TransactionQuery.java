package iyo.dara.transaction.query;

import iyo.dara.account.domain.AccountType;
import iyo.dara.core.query.QueryParser;
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
        String source = QueryParser.parseString(request, "source")
                .orElseThrow(() -> new IllegalArgumentException("Missing param 'source'"));

        return new TransactionQuery(
                source,
                QueryParser.parseDate(request, "startDate"),
                QueryParser.parseDate(request, "endDate"),
                QueryParser.parseEnum(request, "category", Category.class),
                QueryParser.parseEnum(request, "subcategory", Subcategory.class),
                QueryParser.parseEnum(request, "account", AccountType.class)
        );
    }

    public Flux<Transaction> applyFilters(Flux<Transaction> flux) {
        return flux
                .filter(this::matchesDateRange)
                .filter(this::matchesCategory)
                .filter(this::matchesSubCategory)
                .filter(this::matchesAccount);
    }

    private boolean matchesDateRange(Transaction t) {
        boolean afterStart = startDate.map(start -> !t.date().isBefore(start)).orElse(true);
        boolean beforeEnd = endDate.map(end -> !t.date().isAfter(end)).orElse(true);
        return afterStart && beforeEnd;
    }

    private boolean matchesCategory(Transaction t) {
        return category.map(c -> t.category().equals(c)).orElse(true);
    }

    private boolean matchesSubCategory(Transaction t) {
        if (t.subcategory() == null || t.subcategory() == Subcategory.None)
            return subcategory.isEmpty();

        return subcategory
                .map(sc -> t.subcategory().name().equalsIgnoreCase(sc.name()))
                .orElse(true);
    }

    private boolean matchesAccount(Transaction t) {
        return account.map(a -> t.paymentSource().equals(a)).orElse(true);
    }
}

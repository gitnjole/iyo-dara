package iyo.dara.transaction.service;

import iyo.dara.transaction.domain.Transaction;
import iyo.dara.transaction.query.TransactionQuery;
import iyo.dara.core.sheets.SheetsRepository;
import iyo.dara.core.sheets.SheetsTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class TransactionService {
    private static final Logger log =  LoggerFactory.getLogger(TransactionService.class);

    private final SheetsRepository repository;
    private final SheetsTransformer<Transaction> transformer;

    public TransactionService(SheetsRepository repository,
                              SheetsTransformer<Transaction> transformer) {
        this.repository = repository;
        this.transformer = transformer;
    }

    protected Flux<List<Object>> readSheet(String range) {
        return repository.fetchRange(range);
    }

    public Flux<Transaction> stream(String range) {
        return readSheet(range)
                .map(transformer::fromSheet)
                .doOnError(e -> log.error("Transaction stream error", e));
    }

    public Flux<Transaction> query(TransactionQuery query) {
        return query.applyFilters(stream(query.range()));
    }
}

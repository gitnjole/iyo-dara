package iyo.dara.domain.transaction.service;

import iyo.dara.core.sheets.IOSheetsTransformer;
import iyo.dara.core.sheets.SheetEntityService;
import iyo.dara.domain.transaction.Transaction;
import iyo.dara.domain.transaction.query.TransactionQuery;
import iyo.dara.core.sheets.SheetsRepository;
import iyo.dara.domain.transaction.write.TransactionWrite;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TransactionService extends SheetEntityService<Transaction, TransactionWrite.TransactionDto> {
    @Value("${write.range.transactions}")
    private String WRITE_RANGE;

    public TransactionService(SheetsRepository repository,
                              @Qualifier("transactionTransformer") IOSheetsTransformer<Transaction, TransactionWrite.TransactionDto> transformer) {
        super(repository, transformer);
    }

    public Flux<Transaction> queryAndWrite(TransactionQuery query) {
        return query(query)
                .collectList()
                .flatMap(transactions ->
                        saveAll(transactions).thenReturn(transactions))
                .flatMapMany(Flux::fromIterable);
    }

    public Mono<Void> saveAll(List<Transaction> transactions) {
        return saveAll(transactions, WRITE_RANGE);
    }

    public Flux<Transaction> query(TransactionQuery query) {
        return query.applyFilters(stream(query.range()));
    }
}

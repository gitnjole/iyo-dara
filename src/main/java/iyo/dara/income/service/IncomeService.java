package iyo.dara.income.service;

import iyo.dara.core.sheets.SheetsRepository;
import iyo.dara.core.sheets.SheetsTransformer;
import iyo.dara.income.domain.Income;
import iyo.dara.income.query.IncomeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class IncomeService {
    private static final Logger log =  LoggerFactory.getLogger(IncomeService.class);

    private final SheetsRepository repository;
    private final SheetsTransformer<Income> transformer;

    public IncomeService(SheetsRepository repository,
                         SheetsTransformer<Income> transformer) {
        this.repository = repository;
        this.transformer = transformer;
    }

    protected Flux<List<Object>> readSheet(String range) { return repository.fetchRange(range); }

    public Flux<Income> stream(String range) {
        return readSheet(range)
                .map(transformer::fromSheet)
                .doOnError(e -> log.error("Income stream error", e));

    }

    public Flux<Income> query(IncomeQuery query) { return query.applyFilters(stream(query.range())); }
}

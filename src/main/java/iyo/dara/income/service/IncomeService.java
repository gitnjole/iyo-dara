package iyo.dara.income.service;

import iyo.dara.core.sheets.IOSheetsTransformer;
import iyo.dara.core.sheets.SheetEntityService;
import iyo.dara.core.sheets.SheetsRepository;
import iyo.dara.income.domain.Income;
import iyo.dara.income.query.IncomeQuery;
import iyo.dara.income.write.IncomeWrite;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class IncomeService extends SheetEntityService<Income, IncomeWrite.IncomeDto> {
    @Value("${write.range.income}")
    private String WRITE_RANGE;

    public IncomeService(SheetsRepository repository,
                         @Qualifier("incomeTransformer")IOSheetsTransformer<Income, IncomeWrite.IncomeDto> transformer) {
        super(repository, transformer);
    }

    public Flux<Income> queryAndWrite(IncomeQuery query) {
        return query(query)
                .collectList()
                .flatMap(incomes ->
                        saveAll(incomes).thenReturn(incomes))
                .flatMapMany(Flux::fromIterable);
    }

    public Mono<Void> saveAll(List<Income> incomes) {
        return saveAll(incomes, WRITE_RANGE);
    }

    public Flux<Income> query(IncomeQuery query) { return query.applyFilters(stream(query.range())); }
}

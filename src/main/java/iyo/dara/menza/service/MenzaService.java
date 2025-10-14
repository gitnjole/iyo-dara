package iyo.dara.menza.service;

import iyo.dara.core.sheets.SheetsRepository;
import iyo.dara.core.sheets.SheetsTransformer;
import iyo.dara.menza.domain.Menza;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class MenzaService {
    private final SheetsRepository repository;
    private final SheetsTransformer<Menza> transformer;

    public MenzaService(SheetsRepository repository,
                        SheetsTransformer<Menza> transformer) {
        this.repository = repository;
        this.transformer = transformer;
    }

    protected Flux<List<Object>> readSheet(String range) { return repository.fetchRange(range); }

    public Flux<Menza> stream(String range) {
        return readSheet(range)
                .map(transformer::fromSheet);
    }
}

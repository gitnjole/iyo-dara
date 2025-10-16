package iyo.dara.core.sheets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public abstract class SheetEntityService<T, D> {
    private static final Logger log =  LoggerFactory.getLogger(SheetEntityService.class);

    protected final SheetsRepository repository;
    protected final IOSheetsTransformer<T, D> transformer;

    protected SheetEntityService(SheetsRepository repository,
                                 IOSheetsTransformer<T, D> transformer) {
        this.repository = repository;
        this.transformer = transformer;
    }

    protected Flux<T> stream(String range) {
        return repository.fetchRange(range)
                .map(transformer::fromSheet)
                .doOnError(e -> log.error("Stream error", e));
    }

    public Mono<Void> saveAll(List<T> entities, String range) {
        List<List<Object>> rows = entities.stream()
                .map(transformer::toSheet)
                .toList();

        return repository.writeGrid(range, rows)
                .doOnSubscribe(s -> log.info("Recieved {} entities for writing", entities.size()))
                .doOnSuccess(v -> log.info("Write sucessful"))
                .doOnError(e -> log.error("Batch save error", e));
    }
}

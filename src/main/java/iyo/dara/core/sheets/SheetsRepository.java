package iyo.dara.core.sheets;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.BatchUpdateValuesRequest;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Repository
public class SheetsRepository {
    private final Sheets sheets;

    @Value("${spreadsheet.id}")
    private String SPREADSHEET_ID;

    public SheetsRepository(Sheets sheets) {
        this.sheets = sheets;
    }

    public Flux<List<Object>> fetchRange(String range) {
        return Mono.fromCallable(() ->
                        sheets.spreadsheets().values()
                                .get(SPREADSHEET_ID, range)
                                .execute()
                                .getValues()
                )
                .flatMapMany(values -> values == null ? Flux.empty() : Flux.fromIterable(values))
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> writeGrid(String range, List<List<Object>> grid) {
        return Mono.fromCallable(() -> {
            ValueRange body = new ValueRange()
                    .setRange(range)
                    .setMajorDimension("ROWS")
                    .setValues(grid);

            BatchUpdateValuesRequest requestBody = new BatchUpdateValuesRequest()
                    .setValueInputOption("USER_ENTERED")
                    .setData(List.of(body));

            sheets.spreadsheets().values()
                    .batchUpdate(SPREADSHEET_ID, requestBody)
                    .execute();

            return (Void) null;
        }).subscribeOn(Schedulers.boundedElastic());
    }
}

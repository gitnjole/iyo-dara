package iyo.dara.income.handler;

import iyo.dara.core.sheets.SheetEntityService;
import iyo.dara.income.domain.Income;
import iyo.dara.income.query.IncomeQuery;
import iyo.dara.income.service.IncomeService;
import iyo.dara.income.write.IncomeWrite;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class IncomeHandler {
    private final IncomeService service;

    public IncomeHandler(IncomeService service) {
        this.service = service;  }

    public Mono<ServerResponse> getIncomes(ServerRequest request) {
        IncomeQuery query = IncomeQuery.from(request);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.query(query)
                        .map(IncomeWrite.IncomeDto::from),
                        IncomeWrite.IncomeDto.class)
                .onErrorResume(this::handleError);
    }

    public Mono<ServerResponse> write(ServerRequest request) {
        IncomeQuery query = IncomeQuery.from(request);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.queryAndWrite(query)
                        .map(IncomeWrite.IncomeDto::from),
                    IncomeWrite.IncomeDto.class)
                .onErrorResume(this::handleError);
    }

    private Mono<ServerResponse> handleError(Throwable e) {
        return ServerResponse.badRequest()
                .bodyValue(new IncomeHandler.ErrorResponse(e.getMessage()));
    }

    private record ErrorResponse(String error) {}
}

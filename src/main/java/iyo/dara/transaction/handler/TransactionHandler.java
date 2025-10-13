package iyo.dara.transaction.handler;

import iyo.dara.transaction.query.TransactionQuery;
import iyo.dara.transaction.service.TransactionService;
import iyo.dara.transaction.write.TransactionWrite;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TransactionHandler {
    private final TransactionService service;

    public TransactionHandler(TransactionService service) {
        this.service = service;
    }


    public Mono<ServerResponse> getTransactions(ServerRequest request) {
        TransactionQuery query = TransactionQuery.from(request);

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.query(query)
                        .map(TransactionWrite.TransactionDto::from),
                        TransactionWrite.TransactionDto.class)
                .onErrorResume(this::handleError);
    }

    private Mono<ServerResponse> handleError(Throwable e) {
        return ServerResponse.badRequest()
                .bodyValue(new ErrorResponse(e.getMessage()));
    }

    private record ErrorResponse(String error) {}
}

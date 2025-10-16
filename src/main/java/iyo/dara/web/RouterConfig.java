package iyo.dara.web;

import iyo.dara.domain.income.handler.IncomeHandler;
import iyo.dara.domain.menza.handler.MenzaHandler;
import iyo.dara.domain.transaction.handler.TransactionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    private static final String PATH_NAME_GET_TRANSACTIONS = "/api/transactions";
    private static final String PATH_NAME_GET_INCOMES = "/api/incomes";
    private static final String PATH_NAME_GET_MENZAS = "api/menzas";

    @Bean
    public RouterFunction<ServerResponse> transactionRoutes(TransactionHandler handler) {
        return route()
                .GET(PATH_NAME_GET_TRANSACTIONS, handler::getTransactions)
                .POST(PATH_NAME_GET_TRANSACTIONS, handler::write)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> incomeRoutes(IncomeHandler handler) {
        return route()
                .GET(PATH_NAME_GET_INCOMES, handler::getIncomes)
                .POST(PATH_NAME_GET_INCOMES, handler::write)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> menzaRoutes(MenzaHandler handler) {
        return route()
                .GET(PATH_NAME_GET_MENZAS, handler::getMenzas)
                .build();
    }
}

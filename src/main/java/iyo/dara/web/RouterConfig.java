package iyo.dara.web;

import iyo.dara.income.handler.IncomeHandler;
import iyo.dara.transaction.handler.TransactionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    private static final String PATH_NAME_GET_TRANSACTIONS = "/api/transactions";
    private static final String PATH_NAME_GET_INCOME = "/api/incomes";

    @Bean
    public RouterFunction<ServerResponse> transactionRoutes(TransactionHandler handler) {
        return route()
                .GET(PATH_NAME_GET_TRANSACTIONS, handler::getTransactions)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> incomeRoutes(IncomeHandler handler) {
        return route()
                .GET(PATH_NAME_GET_INCOME, handler::getIncome)
                .build();
    }
}

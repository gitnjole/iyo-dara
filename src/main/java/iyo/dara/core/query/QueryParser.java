package iyo.dara.core.query;

import org.springframework.web.reactive.function.server.ServerRequest;

import java.time.LocalDate;
import java.util.Optional;

public class QueryParser {
    private QueryParser() {
    }

    public static <T extends Enum<T>> Optional<T> parseEnum(ServerRequest req, String param, Class<T> type) {
        return req.queryParam(param).map(value -> Enum.valueOf(type, value));
    }

    public static Optional<LocalDate> parseDate(ServerRequest req, String param) {
        return req.queryParam(param).map(LocalDate::parse);
    }

    public static Optional<String> parseString(ServerRequest req, String param) {
        return req.queryParam(param);
    }
}

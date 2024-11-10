package pl.dgorecki.gatewayserver.filters;

import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.Objects;

@Component
public class FilterUtility {
    public static final String CORRELATION_ID = "tracker-correlation-id";

    public String getCorrelationId(HttpHeaders requestHeaders) {
         return Objects.requireNonNull(requestHeaders.get(CORRELATION_ID)).stream().findFirst().orElse(null);
    }

    public ServerWebExchange setHeaderCorrelationId(ServerWebExchange exchange, String correlationId) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(CORRELATION_ID, correlationId).build()).build();
    }

}

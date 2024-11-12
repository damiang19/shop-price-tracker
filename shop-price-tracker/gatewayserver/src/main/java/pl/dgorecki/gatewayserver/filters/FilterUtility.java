package pl.dgorecki.gatewayserver.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;


@Component
public class FilterUtility {
    public static final String CORRELATION_ID = "tracker-correlation-id";

    public String getCorrelationId(HttpHeaders requestHeaders) {
         return requestHeaders.getOrEmpty(CORRELATION_ID).stream().findFirst().orElse(null);
    }

    public ServerWebExchange setHeaderCorrelationId(ServerWebExchange exchange, String correlationId) {
        return exchange.mutate().request(exchange.getRequest().mutate().header(CORRELATION_ID, correlationId).build()).build();
    }

}

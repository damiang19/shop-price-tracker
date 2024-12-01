package pl.dgorecki.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }

    @Bean
    public RouteLocator scrapperRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p
						.path("/dgorecki/scrapper/**")
                        .filters(filter -> filter.rewritePath("/dgorecki/scrapper/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://SCRAPPER"))
				.route(p -> p
						.path("/dgorecki/price-tracker/**")
						.filters(filter -> filter.rewritePath("/dgorecki/price-tracker/(?<segment>.*)", "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
								.requestRateLimiter(config -> config.setRateLimiter(redisRateLimiter()).setKeyResolver(userKeyResolver()))
								.retry(retryConfig -> retryConfig.setRetries(3).setMethods(HttpMethod.GET)
										.setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000),2,true))
								.circuitBreaker(config -> config.setName("priceTrackerCircuitBreaker")
										.setFallbackUri("forward:/microservice-error")))
						.uri("lb://PRICETRACKER")).build();
    }

	@Bean
	public RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(1,1,1);
	}

	KeyResolver userKeyResolver() {
		return exchange -> Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("user")).defaultIfEmpty("anonymous");
	}
}

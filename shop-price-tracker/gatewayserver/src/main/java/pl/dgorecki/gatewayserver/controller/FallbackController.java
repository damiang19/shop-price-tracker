package pl.dgorecki.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/microservice-error")
    public Mono<String> microserviceErrorMessage() {
        return Mono.just("An error occurred.....");
    }
}

package com.vladcarcu.webflux.sync.webclient;

import com.vladcarcu.webflux.sync.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class StudentWebClient implements InitializingBean {

    private static final String SERVICE_NAME = "student";

    private final WebClient webClient = WebClient.create("http://localhost:8081/students");
    private final ReactiveCircuitBreakerFactory cbFactory;

    private ReactiveCircuitBreaker circuitBreaker;

    @Override
    public void afterPropertiesSet() {
        circuitBreaker = cbFactory.create(SERVICE_NAME);
    }

    public Flux<Student> getByName(String name) {
        return webClient.get()
                .uri(String.format("/name/%s", name))
                .retrieve()
                .bodyToFlux(Student.class)
                // the circuit breaker implementation is here
                .transform(studentFlux -> circuitBreaker.run(studentFlux, throwable -> Flux.empty()));
    }
}

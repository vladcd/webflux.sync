package com.vladcarcu.webflux.sync.config;

import com.vladcarcu.webflux.sync.handler.StudentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<?> routerFunction(StudentHandler studentHandler) {
        return RouterFunctions.route(GET("/students"), studentHandler::getAllStudents)
                .andRoute(GET("/students/{id}"), studentHandler::getStudent)
                .andRoute(GET("/students/name/{name}"), studentHandler::getStudentByName)
                .andRoute(POST("/students"), studentHandler::create);
    }
}

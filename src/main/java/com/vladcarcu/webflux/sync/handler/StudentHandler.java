package com.vladcarcu.webflux.sync.handler;

import com.vladcarcu.webflux.sync.entity.Student;
import com.vladcarcu.webflux.sync.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StudentHandler {

    private final StudentService studentService;

    public Mono<ServerResponse> getAllStudents(ServerRequest request) {
        var students = studentService.getAllStudents();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(students, Student.class);
    }

    public Mono<ServerResponse> getStudent(ServerRequest request) {
        var student = studentService.getStudent(Long.valueOf(request.pathVariable("id")));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(student, Student.class);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        var student = studentService.save(request.bodyToMono(Student.class));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(student, Student.class);
    }

    public Mono<ServerResponse> getStudentByName(ServerRequest request) {
        var student = studentService.getByName(request.pathVariable("name"));
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(student, Student.class);
    }

}

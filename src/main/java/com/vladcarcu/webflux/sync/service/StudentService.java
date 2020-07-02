package com.vladcarcu.webflux.sync.service;

import com.vladcarcu.webflux.sync.entity.Student;
import com.vladcarcu.webflux.sync.repository.AddressRepository;
import com.vladcarcu.webflux.sync.repository.StudentRepository;
import com.vladcarcu.webflux.sync.util.AsyncUtil;
import com.vladcarcu.webflux.sync.webclient.StudentWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final AddressRepository addressRepository;
    private final StudentWebClient studentWebClient;

    public Mono<Student> getStudent(Long id) {
        return AsyncUtil.wrapMonoOptional(() -> studentRepository.findById(id));
    }

    public Flux<Student> getAllStudents() {
        return AsyncUtil.wrapFlux(() -> studentRepository.findAll());
    }

    @Transactional
    public Mono<Student> save(Mono<Student> studentMono) {
        return studentMono.map(student -> {
            if (student.getAddress() != null) {
                var address = addressRepository.save(student.getAddress());
                student.setAddress(address);
            }
            return studentRepository.save(student);
        });
    }

    public Mono<Void> delete(Long id) {
        return AsyncUtil.wrapMonoVoid(() -> studentRepository.deleteById(id));
    }

    public Flux<Student> getByName(String name) {
        return studentWebClient.getByName(name);
    }

}

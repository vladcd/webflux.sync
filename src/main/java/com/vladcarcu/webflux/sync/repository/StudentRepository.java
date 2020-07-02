package com.vladcarcu.webflux.sync.repository;

import com.vladcarcu.webflux.sync.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByNameContainingAllIgnoreCase(String name);
}

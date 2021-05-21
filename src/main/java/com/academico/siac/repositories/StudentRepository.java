package com.academico.siac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academico.siac.models.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    public List<Student> findAllByOrderByIdAsc(); //
}

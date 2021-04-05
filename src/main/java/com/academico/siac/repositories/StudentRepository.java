package com.academico.siac.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.academico.siac.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}

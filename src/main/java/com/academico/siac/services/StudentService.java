package com.academico.siac.services;

import com.academico.siac.models.Student;
import com.academico.siac.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired StudentRepository studentRepository;

    public Student findStudentById(Long id) {
       return studentRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void saveOrUpdateStudent(Student student) {
        studentRepository.save(student);
    }

    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }
}

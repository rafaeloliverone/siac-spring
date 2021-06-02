package com.academico.siac.services;

import com.academico.siac.enums.SituacaoEnum;
import com.academico.siac.models.Student;
import com.academico.siac.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.List;

@Service
public class StudentService {

    @Autowired StudentRepository studentRepository;

    public Boolean isStudentValid(Student student) {
        return !student.getName().isEmpty() &&
                student.getBirthDate() != null &&
                student.getBirthDate().before(new Date());
    }

    public Student findStudentById(Long id) {
       return studentRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
    }

    public List<Student> getAllStudentsWithGrades() {
        List<Student> students = studentRepository.findAllByOrderByIdAsc();
        students.forEach(student -> {
            BigDecimal average;
            average = average(student);
            student.setMedia(average);

            SituacaoEnum situacao;
            situacao = calcSituacao(student);
            student.setSituacao(situacao);
        });

        return students;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAllByOrderByIdAsc();
    }

    public void saveOrUpdateStudent(Student student) {
        studentRepository.save(student);
    }

    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    private BigDecimal average(Student estudante) {
        BigDecimal nota1 = estudante.getNota1();
        BigDecimal nota2 = estudante.getNota2();
        BigDecimal nota3 = estudante.getNota3();
        BigDecimal notaFinal = estudante.getNotaFinal();
        BigDecimal media = null;

        if (nota1 != null &&
            nota2 != null &&
            nota3 != null) {

            media = (nota1.add(nota2).add(nota3)).divide(new BigDecimal(3), MathContext.DECIMAL32);

            if (notaFinal != null) {
                media = (
                    media.multiply(new BigDecimal(60)).add
                        (notaFinal.multiply(new BigDecimal(40))).divide
                        (new BigDecimal(100), MathContext.DECIMAL32)
                );
            }
        }

        return media;
    }

    private SituacaoEnum calcSituacao(Student estudante) {
        BigDecimal media = this.average(estudante);
        BigDecimal notaFinal = estudante.getNotaFinal();
        Integer faltas = estudante.getFaltas();

        if (faltas != null) {
            if (faltas >= 25) return SituacaoEnum.RF;

            if (media != null) {
                if (media.compareTo(new BigDecimal(0)) > 0) {
                    if (notaFinal != null) {

                        if (media.compareTo(new BigDecimal(50)) == 0 ||
                                media.compareTo(new BigDecimal(50)) == 1
                        ) return SituacaoEnum.AP;
                        else return SituacaoEnum.RP;

                    } else {

                        if (media.compareTo(new BigDecimal(40)) == -1) return SituacaoEnum.RP;
                        if (media.compareTo(new BigDecimal(70)) == -1) return SituacaoEnum.FN;
                        if (media.compareTo(new BigDecimal(70)) == 0 ||
                            media.compareTo(new BigDecimal(70)) == 1) return SituacaoEnum.AP;
                    }
                }
            }

        }

        return SituacaoEnum.MT;
    }
}

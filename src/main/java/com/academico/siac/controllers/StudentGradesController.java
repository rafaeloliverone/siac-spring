package com.academico.siac.controllers;

import com.academico.siac.models.Student;
import com.academico.siac.services.StudentService;
import com.academico.siac.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/grades")
public class StudentGradesController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ModelAndView listView(ModelAndView mv) {
        mv.addObject("user", userService.getUser());
        mv.addObject("students", studentService.getAllStudents());
        mv.addObject("student", new Student());
        mv.setViewName("grades/list");
        return mv;
    }

    @GetMapping("/update/{id}")
    public ModelAndView formGrade(@PathVariable Long id, ModelAndView mv) {
        mv.addObject("user", userService.getUser());
        mv.addObject("student", studentService.findStudentById(id));
        mv.setViewName("grades/form");
        return mv;
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateStudentGrade(@PathVariable Long id, Student student, BindingResult result, ModelAndView mv) {
        mv.addObject("user", userService.getUser());

        if (result.hasErrors()) {
            mv.addObject("student", student);
            mv.setViewName("grades/form");
        } else {
            student.setId(id);
            studentService.saveOrUpdateStudent(student);
            mv.setViewName("redirect:/grades/list");
        }
        return mv;
    }
}

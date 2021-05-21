package com.academico.siac.controllers;

import com.academico.siac.models.Student;
import com.academico.siac.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.academico.siac.repositories.StudentRepository;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;

	//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@GetMapping("/list")
	public ModelAndView listView(ModelAndView mv) {
		mv.addObject("students", studentService.getAllStudents());
		mv.setViewName("student/list");
		return mv;
	}

	@GetMapping("/report")
	public ModelAndView reportView(ModelAndView mv) {
		mv.addObject("students", studentService.getAllStudentsWithGrades());
		mv.setViewName("student/report");

		return mv;
	}

	@GetMapping("/create")
	public ModelAndView createView(ModelAndView mv) {
		mv.addObject("student", new Student());
		mv.setViewName("student/form");
		return mv;
	}

	@PostMapping("/save")
	public ModelAndView saveOrUpdateStudent(@Valid Student student, BindingResult result, ModelAndView mv) {
		if (result.hasErrors()) {
			mv.addObject("student", new Student());
			mv.setViewName("student/form");
		} else {
			studentService.saveOrUpdateStudent(student);
			mv.setViewName("redirect:/students/list");
		}

		return mv;
	}

	@PostMapping("/save/{id}")
	public ModelAndView updateStudent(
			@PathVariable("id") Long id,
			@Valid Student student,
			BindingResult result,
			ModelAndView mv) {

		if (result.hasErrors()) {
			mv.addObject("student", student);
			mv.setViewName("student/form");
		} else {
			student.setId(id);
			studentService.saveOrUpdateStudent(student);
			mv.setViewName("redirect:/students/list");
		}

		return mv;
	}

	@GetMapping("/update/{id}")
	public ModelAndView updateView(@PathVariable("id") Long id, ModelAndView mv) {
		Student student = studentService.findStudentById(id);
		mv.addObject("student", student);
		mv.setViewName("student/form");
		return mv;
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id, ModelAndView mv) {
		studentService.deleteStudentById(id);
		mv.setViewName("redirect:/students/list");
		return mv;
	}
}

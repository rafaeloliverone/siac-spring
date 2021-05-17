package com.academico.siac.controllers;

import com.academico.siac.models.Student;
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
	private StudentRepository studentRepository;

	//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@GetMapping("/list")
	public ModelAndView listView(ModelAndView mv) {
		mv.addObject("students", studentRepository.findAll());
		mv.setViewName("student/list");
		return mv;
	}

	@GetMapping("/create")
	public ModelAndView createView(ModelAndView mv) {
		mv.addObject("student", new Student());
		mv.setViewName("student/form");
		return mv;
	}

	@PostMapping("/save")
	public ModelAndView saveStudent(@Valid Student student, BindingResult result, ModelAndView mv) {
		if (result.hasErrors()) {
			mv.addObject("student", new Student());
			mv.setViewName("student/form");
		} else {
			studentRepository.save(student);
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
			studentRepository.save(student);
			mv.setViewName("redirect:/students/list");
		}

		return mv;
	}

	@GetMapping("/update/{id}")
	public ModelAndView updateView(@PathVariable("id") Long id, ModelAndView mv) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
		mv.addObject("student", student);
		mv.setViewName("student/form");
		return mv;
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id, ModelAndView mv) {
		studentRepository.deleteById(id);
		mv.setViewName("redirect:/students/list");
		return mv;
	}
	
}

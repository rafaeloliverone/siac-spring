package com.academico.siac.controllers;

import com.academico.siac.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.academico.siac.repositories.StudentRepository;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;

	@GetMapping("/create")
	public ModelAndView create() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("student/create");
		mv.addObject("student", new Student());
		return mv;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("student/list");
		mv.addObject("students", studentRepository.findAll());
		return mv;
	}

	@PostMapping("/create")
	public String create(@Valid Student student, Errors bindingResult, Model model ){
		ModelAndView mv = new ModelAndView();

		System.out.println(bindingResult.hasErrors());
		System.out.println(bindingResult.getErrorCount());

		if (bindingResult.hasErrors()) {
			System.out.println("tem erro");
			mv.setViewName("students/create");
			mv.addObject("student", student);
			return "students/create";
		} else {
			studentRepository.save(student);
			mv.setViewName("redirect:/students/list");
		}

		return "redirect:/students/list";
	}

	@GetMapping("/update/{id}")
	public ModelAndView alterar(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();
		Optional<Student> student = studentRepository.findById(id);
		mv.addObject("student", student);
		mv.setViewName("student/update");
		return mv;
	}

	@PostMapping("/update")
	public ModelAndView update( Student student, BindingResult bindingResult ){
		ModelAndView mv = new ModelAndView();

		mv.setViewName("redirect:/students/list");
		studentRepository.save(student);

//		if (bindingResult.hasErrors()) {
//			mv.setViewName("tarefas/alterar");
//			mv.addObject("tarefa", tarefa);
//		} else {
//
//		}
		return mv;
	}


	@GetMapping("/delete/{id}")
	public String excluir(@PathVariable("id") Long id) {
		studentRepository.deleteById(id);
		return "redirect:/students/list";
	}
	
}

package com.academico.siac.controllers;

import com.academico.siac.models.Users;
import com.academico.siac.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AccountController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @PostMapping("/login")
    public ModelAndView login(@Validated Users user, BindingResult result) {
        ModelAndView mv = new ModelAndView();

        Users usr = userService.encontrarPorEmail(user.getEmail());

        boolean checkPassword = BCrypt.checkpw(user.getPassword(), usr.getPassword());
        boolean checkSameEmail = user.getEmail().equals(usr.getEmail());

        if (checkPassword && checkSameEmail) {
            mv.setViewName("redirect:/students/list");
        } else {
            mv.addObject("user", user);
            mv.setViewName("account/login");
        }

        return mv;
    }

    @GetMapping("/registration")
    public ModelAndView registration() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("account/register");
        mv.addObject("user", new Users());
        return mv;
    }

    @PostMapping("/registration")
    public ModelAndView registration(@Validated Users user, BindingResult result) {
        ModelAndView mv = new ModelAndView();
        Users usr = userService.encontrarPorEmail(user.getEmail());

        if (usr != null) {
            result.rejectValue("email", "", "Usuário já cadastrado");
        }

        if (user.getPassword() == null) {
            result.rejectValue("password", "", "password vazio");
        }

        if (result.hasErrors()) {
            mv.setViewName("account/register");
            mv.addObject("user", user);
        } else  {
            userService.salvar(user);
            mv.setViewName("redirect:/login");

        }


        return mv;
    }

}

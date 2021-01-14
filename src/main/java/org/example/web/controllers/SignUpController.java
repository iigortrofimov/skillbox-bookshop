package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.UserDetailServiceImpl;
import org.example.web.dto.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/signup")
public class SignUpController {

    private final Logger logger = Logger.getLogger(SignUpController.class);
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    public SignUpController(UserDetailServiceImpl userDetailServiceImpl) {
        this.userDetailServiceImpl = userDetailServiceImpl;
    }

    @GetMapping
    public String signUp(Model model) {
        logger.info("GET /signup returns signup_page.html");
        model.addAttribute("SignUpForm", new SignUpForm());
        return "signup_page";
    }

    @PostMapping("/register")
    public String register(@Valid SignUpForm signUpForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("SignUpForm", signUpForm);
            return "signup_page";
        } else {
            userDetailServiceImpl.saveNewUser(signUpForm);
            return "redirect:/books/shelf";
        }
    }

}
